import os
import sys
import shutil
import subprocess
root_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
praat_exe = os.path.join(root_dir, 'Praat.exe')
print("Praat executable path: {}".format(praat_exe))

formant_script = os.path.join(root_dir, "praat_scripts", "collect_formant_data_from_files.praat")
pitch_script = os.path.join(root_dir, "praat_scripts", "collect_pitch_data_from_files.praat")
mfcc_script = os.path.join(root_dir, "praat_scripts", "mfcc.praat")

data_path_temp = ""


formant_result_file = os.path.join(root_dir, "python_scripts", "results", "formant.txt")
pitch_result_file = os.path.join(root_dir, "python_scripts", "results", "pitch.txt")
mfcc_results = os.path.join(root_dir, "python_scripts", "results", "mfcc")

def prerequisites(temp_dir,wav_name):
    try:
        os.stat(temp_dir)
    except:
        os.mkdir(temp_dir)
    print(">>> Temporary directory was created")

    data_path = open(os.path.join(root_dir, "python_scripts", "data_dir"), "r").read().strip()
    if not data_path.endswith("\\\\"):
        data_path += "\\"
    # data_path = r'D:\Facultate\An3\IA\IAVoiceRecognition\E4_ExtragereTrasaturi\data\data\\'
    print(data_path)
    #print(os.path.join(os.getcwd(), temp_dir))
    
    print(os.path.join(data_path,wav_name))
    data_path_temp = os.path.join(os.getcwd(), temp_dir)
    print(os.path.join(os.getcwd(), temp_dir, wav_name))
    try:
        shutil.copy(os.path.join(data_path,wav_name), os.path.join(os.getcwd(), temp_dir, wav_name))
    except:
        pass
    print(">>> File recieved as argument was copied")



if __name__ == '__main__':

    wav_name = ""
    if len(sys.argv) > 1:
        wav_name = sys.argv[1]
        print(">>> WAV name: " + wav_name)
        prerequisites("director_temporar", wav_name)

        #os.system("process_files.py formant")
        os.system("process_files.py mfcc")
        #os.system("process_files.py pitch")

    else:
        print(">>> Give a wav file as an argument!")




