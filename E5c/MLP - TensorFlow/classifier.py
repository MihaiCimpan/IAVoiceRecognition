import os
import tensorflow as tf
from parser import Parser
from ConfigParser import ConfigParser
from first import first
from math import sqrt
from os.path import join


class Configuration(object):
    """Neural network configuration"""

    def __init__(self, config_file):
        super(Configuration, self).__init__()
        self.config_file = config_file
        config = self.parse_config()
        self.formants = config.getboolean('DATASET', 'Formants')
        self.mfcc = config.getboolean('DATASET', 'MFCC')
        self.pitch = config.getboolean('DATASET', 'Pitch')

        self.trainingPercent = config.getint('PARAMS', 'TrainingPercent')
        self.iterations = config.getint('PARAMS', 'Iterations')
        self.classes = config.getint('PARAMS', 'Classes')
        self.multiplier = config.getint('PARAMS', 'Multiplier')
        self.batchSize = config.getint('PARAMS', 'BatchSize')

        self.logfile = config.get('FILES', 'Log')
        self.modelfile = config.get('FILES', 'Model')

    def parse_config(self):
        config = ConfigParser()
        with open(self.config_file) as f:
            config.readfp(f)
        return config


class Classifier(Configuration):
    """Emotion classifier for audio data"""

    def __init__(self, config_file):
        super(Classifier, self).__init__(config_file)

        self.logger = open(self.logfile, 'w')

        self.dimen = 8 * self.formants + 22 * self.mfcc + 2 * self.pitch
        self.div = first(xrange(int(round(sqrt(self.dimen))), 1, -1), key=lambda x: self.dimen % x == 0, default=1)
        self.shape = [self.dimen // self.div, self.div]
        self.wshape = min(self.shape)
        self.z = ((self.shape[0] // 4) or 1) * ((self.shape[1] // 4) or 1) * 64 * self.multiplier

        self.x = tf.placeholder(tf.float32, [None, self.dimen])
        self.x_audio = tf.reshape(self.x, [-1, self.shape[0], self.shape[1], 1])

        self.W_conv1 = self.weight_variable([self.wshape, self.wshape, 1, 32])
        self.b_conv1 = self.bias_variable([32])

        self.h_conv1 = tf.nn.relu(self.conv2d(self.x_audio, self.W_conv1) + self.b_conv1)
        self.h_pool1 = self.max_pool(self.h_conv1)

        self.W_conv2 = self.weight_variable([self.wshape // 2, self.wshape // 2, 32, 64])
        self.b_conv2 = self.bias_variable([64])

        self.h_conv2 = tf.nn.relu(self.conv2d(self.h_pool1, self.W_conv2) + self.b_conv2)
        self.h_pool2 = self.max_pool(self.h_conv2, dimen=min(2, self.wshape // 2))

        self.W_fc1 = self.weight_variable([self.z, 1024])
        self.b_fc1 = self.bias_variable([1024])

        self.h_pool2_flat = tf.reshape(self.h_pool2, [-1, self.z])
        self.h_fc1 = tf.nn.relu(tf.matmul(self.h_pool2_flat, self.W_fc1) + self.b_fc1)

        self.keep_prob = tf.placeholder("float")
        self.h_fc1_drop = tf.nn.dropout(self.h_fc1, self.keep_prob)

        self.W_fc2 = self.weight_variable([1024, self.classes])
        self.b_fc2 = self.bias_variable([self.classes])

        self.y_conv = tf.nn.softmax(tf.matmul(self.h_fc1_drop, self.W_fc2) + self.b_fc2)
        self.y_ = tf.placeholder(tf.float32, [None, self.classes])

        self.cross_entropy = -tf.reduce_sum(self.y_ * tf.log(self.y_conv))
        self.train_step = tf.train.AdamOptimizer(1e-4).minimize(self.cross_entropy)
        self.correct_prediction = tf.equal(tf.argmax(self.y_conv, 1), tf.argmax(self.y_, 1))
        self.accuracy = tf.reduce_mean(tf.cast(self.correct_prediction, "float"))

        self.sess = tf.InteractiveSession()
        self.sess.run(tf.initialize_all_variables())

        self.parser = Parser('voice_data', trainingPercent=self.trainingPercent, formants=self.formants, mfcc=self.mfcc, pitch=self.pitch)

    def log(self, msg):
        print msg
        self.logger.write(msg + '\n')

    def train(self):
        self.log('Training using %s' % ', '.join(filter(None, ['F0' * self.pitch, 'F1-F4' * self.formants, 'MFCC' * self.mfcc])))
        for i in range(self.iterations + 1):
            batch = self.parser.get_batch(self.batchSize)
            if i % 100 == 0:
                self.train_accuracy = self.accuracy.eval(feed_dict={
                    self.x: batch[0], self.y_: batch[1], self.keep_prob: 1.0})
                self.log("\tstep %d, training accuracy %.1f%%" % (i, self.train_accuracy * 100))
            self.train_step.run(feed_dict={self.x: batch[0], self.y_: batch[1], self.keep_prob: 0.5})

    def test(self):
        self.log("test accuracy %.3f%%" % (self.accuracy.eval(feed_dict={
            self.x: self.parser.datasets.test.values,
            self.y_: self.parser.datasets.test.emotions,
            self.keep_prob: 1.0}) * 100))

    def save(self):
        saver = tf.train.Saver()
        saver.save(self.sess, self.modelfile)

    def restore(self):
        saver = tf.train.Saver()
        saver.restore(self.sess, self.modelfile)

    def close(self):
        self.sess.close()

    def weight_variable(self, shape):
        initial = tf.truncated_normal(shape, stddev=0.1)
        return tf.Variable(initial)

    def bias_variable(self, shape):
        initial = tf.constant(0.1, shape=shape)
        return tf.Variable(initial)

    def conv2d(self, x, W):
        return tf.nn.conv2d(x, W, strides=[1, 1, 1, 1], padding='SAME')

    def max_pool(self, x, dimen=2):
        return tf.nn.max_pool(x, ksize=[1, dimen, dimen, 1],
                              strides=[1, dimen, dimen, 1], padding='SAME')

if __name__ == '__main__':
    for conf in os.listdir('configs'):
        classifier = Classifier(join('configs', conf))
        classifier.train()
        classifier.test()
        classifier.save()
        classifier.close()
