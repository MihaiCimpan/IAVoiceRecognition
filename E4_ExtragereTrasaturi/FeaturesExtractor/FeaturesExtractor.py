""" The main module, used to process audio files and create output based
on the input arguments. """

import subprocess

from . import config


class FeaturesExtractor(object):
    def __init__(self, praat_path, wav_path, textview_path, output_path,
                 script_path):
        self.praat_path = praat_path
        self.wav_path = wav_path
        self.textview_path = textview_path
        self.output_path = output_path
        self.script_path = script_path

    def run(self):
        pass


class F0Extractor(GeneralFeaturesExtractor):
    def __init__(self, *args):
        GeneralFeaturesExtractor.__init__(self, *args)

    def run(self):
        pass

if __name__ == "__main__":
    F0Extractor(config.praat_path, config.wav_path,
                config.textview_path, config.output_path,
                config.f0_script_path).run()
