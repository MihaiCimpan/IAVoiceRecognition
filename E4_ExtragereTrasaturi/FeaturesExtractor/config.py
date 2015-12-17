""" Module-wide variables and settings. """
import os

# --- f0/pitch extraction settings ---

f0_parameters = {
    'script_path': os.path.join(os.path.abspath(__file__), "praat_scripts",
                                "collect_formant_data_from_files.praat"),
    'sound_directory': './audio_input/',
    'Sound_file_extension': 'wav',
    'textGrid_directory': './audio_input/',  # audio + textview in acelasi dir
    'resultfile': './audio_input/results.txt',
    'tier': 'propozitie',  # cananul de adnotarea folosit
    'Time_step': 0.01,  # dimensiunea intervalelor
    'Minimum_pitch_(Hz)': 75,  # amplitudinea minima, in Hz
    'Maximum_pitch_(Hz)': 300,  # amplitudinea maxima, in Hz
}