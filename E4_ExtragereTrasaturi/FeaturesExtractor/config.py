""" Module-wide variables and settings. """
import os

# --- global settings ---
praat_location = os.path.join(os.path.abspath(os.path.dirname(__file__)), "bin",
                              "praat.exe")

# --- f0/pitch extraction settings ---

f0_parameters = {
    'script_path': os.path.join(os.path.abspath(os.path.dirname(__file__)),
                                "praat_scripts",
                                "collect_pitch_data_from_files.praat"),
    'sound_directory': 'C:/tmp/',  # audio+textview files
    'Sound_file_extension': '.wav',
    'textGrid_directory': 'C:/tmp/',
    'TextGrid_file_extension': ".TextGrid",
    'resultfile': 'C:/tmp/results.txt',
    'tier': 'propozitii',  # cananul de adnotarea folosit
    'Time_step': 0.01,  # dimensiunea intervalelor
    'Minimum_pitch': 75,  # amplitudinea minima, in Hz
    'Maximum_pitch': 300,  # amplitudinea maxima, in Hz
}