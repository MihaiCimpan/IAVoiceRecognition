from os.path import *
from collections import defaultdict
from numpy import array, float32, uint8, multiply, arange, random, zeros
import os
import re


class DataSet(object):
    """DataSet structure"""

    def __init__(self, emotions, values):
        super(DataSet, self).__init__()
        self.emotions = emotions
        self.values = values


class DataSets(object):

    def __init__(self, train, test):
        super(DataSets, self).__init__()
        self.train = train
        self.test = test

classes = {
    'N': 0,
    'J': 1,
    'S': 2,
    'F': 3,
    'A': 4,
    'B': 5,
    'D': 6
}


class Parser(object):
    """Formant data parser"""

    datafile_pattern = re.compile('.*(formants|mfcc|pitch).*')

    def __init__(self, datadir, trainingPercent=75, **kwargs):
        super(Parser, self).__init__()
        self.epoch = 0
        self.epoch_index = 0
        self.useddata = kwargs
        self.trainingPercent = trainingPercent
        self.datadir = datadir
        self.datasets = self.get_datasets()
        self.num_samples = self.datasets.train.emotions.shape[0]

    def dense_to_one_hot(self, dense, num_classes=7):
        num_labels = dense.shape[0]
        index_offset = arange(num_labels) * num_classes
        one_hot = zeros((num_labels, num_classes))
        one_hot.flat[index_offset + dense.ravel()] = 1
        return one_hot

    def used(self, datafile):
        datatype = Parser.datafile_pattern.match(datafile).group(1)
        return self.useddata.get(datatype, False)

    def get_datasets(self):
        values = defaultdict(list)
        datafiles = filter(self.used, os.listdir(self.datadir))
        for _file in datafiles:
            with open(join(self.datadir, _file)) as f:
                content = f.readlines()
            data = map(lambda x: x.split(), content)

            for rec, _, val in data:
                values[rec].append(float(val.strip()))
        emotions = array(map(lambda x: classes[x[-2].upper()], values.keys()), dtype=uint8)
        emotions = self.dense_to_one_hot(emotions)
        data = array(values.values(), dtype=float32)
        data = multiply(data, 1.0 / 20000.0)
        divider = (emotions.shape[0] * self.trainingPercent) / 100
        return DataSets(DataSet(emotions[:divider], data[:divider]), DataSet(emotions[divider:], data[divider:]))

    def get_batch(self, size):
        d = self.datasets.train
        start = self.epoch_index
        self.epoch_index += size

        if self.epoch_index > self.num_samples:
            self.epoch += 1

            perm = arange(self.num_samples)
            random.shuffle(perm)
            d.emotions = d.emotions[perm]
            d.values = d.values[perm]

            start = 0
            self.epoch_index = size
        end = self.epoch_index

        return d.values[start:end], d.emotions[start:end]


if __name__ == '__main__':
    Parser('voice_data')
