# Multilayer Convolutional Network using TensorFlow

## parser.py

Splits audio data in 75% training data and 25% test data. _(E5a)_

Shuffles the data at every epoch of the training. _(E5b)_

Parses audio data and organizes it in a matrix structure for feeding it into the neural network.

## classifier.py

Trains a deep neural network with parsed audio data from **_parser.py_** using Google's TensorFlow framework. _(E5c)_

## results.txt

Accuracy results after testing the trained neural network. _(E5d)_
