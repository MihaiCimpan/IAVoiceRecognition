import os
import random


def shuffleFiles(directory):
    filesList = os.listdir(directory)
    random.shuffle(filesList)
    return filesList
