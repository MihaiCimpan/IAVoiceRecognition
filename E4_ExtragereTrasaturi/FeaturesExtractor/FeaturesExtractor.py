""" The main module, used to process audio files and create output based
on the input arguments (config file). """

from subprocess import Popen, PIPE
from config import praat_location, f0_parameters

if __name__ == "__main__":
    print "Praat location:", praat_location

    print "[F0] Input parameters:", str(f0_parameters)

    cmd = "{} --run {} {} {} {} {} {} {} {} {} {}".\
        format(praat_location, f0_parameters['script_path'],
               f0_parameters['sound_directory'],
               f0_parameters['Sound_file_extension'],
               f0_parameters['textGrid_directory'],
               f0_parameters['TextGrid_file_extension'],
               f0_parameters['resultfile'],
               f0_parameters['tier'],
               f0_parameters['Time_step'],
               f0_parameters['Minimum_pitch'],
               f0_parameters['Maximum_pitch'])
    print "[F0] Command line:", cmd

    process = Popen(cmd, shell=True, stdout=PIPE, stderr=PIPE,
                    universal_newlines=True)
    ret = process.communicate()
    print "[F0] Return code:", process.returncode
    print "[F0] Output:", ret
