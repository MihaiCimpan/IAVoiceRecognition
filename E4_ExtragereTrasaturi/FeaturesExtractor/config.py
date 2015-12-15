""" Module-wide variables and settings. """
import os

praat_path = "./praat.exe"
wav_path = "./audio/"
textview_path = "./audio_textview/"
output_path = "./audio_output/"

f0_script_path = os.path.join(os.path.abspath(__file__),
                              "praat_scripts",
                              "collect_formant_data_from_files.praat")
