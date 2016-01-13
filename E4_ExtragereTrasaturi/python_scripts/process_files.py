import subprocess
import os.path
import shutil

root_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
praat_exe = os.path.join(root_dir, 'Praat.exe')
print("Praat executable path: {}".format(praat_exe))

formant_script = os.path.join(root_dir, "praat_scripts", "collect_formant_data_from_files.praat")
pitch_script = os.path.join(root_dir, "praat_scripts", "collect_pitch_data_from_files.praat")
mfcc_script = os.path.join(root_dir, "praat_scripts", "mfcc.praat")

data_path = open(os.path.join(root_dir, "python_scripts", "data_dir"), "r").read().strip()
if not data_path.endswith("\\\\"):
    data_path += "\\"
# data_path = r'D:\Facultate\An3\IA\IAVoiceRecognition\E4_ExtragereTrasaturi\data\data\\'
print("Data path: {}".format(data_path))

# results
formant_result_file = os.path.join(root_dir, "python_scripts", "results", "formant.txt")
pitch_result_file = os.path.join(root_dir, "python_scripts", "results", "pitch.txt")
mfcc_results = os.path.join(root_dir, "python_scripts", "results", "mfcc")

os.makedirs(os.path.join(root_dir, "python_scripts", "results", "mfcc"), exist_ok=True)


def get_formant_data():
    cmd = " ".join(["\"" + x + "\"" for x in [praat_exe, formant_script, data_path, ".wav", data_path, ".TextGrid",
                                              formant_result_file, "segments", "0.01", "5",
                                              "5500_(=adult female)", "0.025", "50"]])
    print(cmd)
    proc = subprocess.Popen(cmd, shell=True)
    proc.wait()


def get_pitch_data():
    cmd = " ".join(["\"" + x + "\"" for x in [praat_exe, pitch_script, data_path, ".wav", data_path, ".TextGrid",
                                              pitch_result_file, "segments", "0.01", "75", "300"]])
    print(cmd)
    proc = subprocess.Popen(cmd, shell=True)
    proc.wait()


def get_mfcc_data():
    cmd = " ".join(["\"" + x + "\"" for x in [praat_exe, mfcc_script, data_path]])
    print(cmd)
    proc = subprocess.Popen(cmd, shell=True)
    proc.wait()

    print("Moving .txt results")
    for file in os.listdir(data_path):
        if file.endswith(".txt"):
            if os.path.exists(os.path.join(mfcc_results, file)):
                os.remove(os.path.join(mfcc_results, file))
            shutil.move(os.path.join(data_path, file), os.path.join(mfcc_results, file))

if __name__ == '__main__':
    import argparse, sys
    parser = argparse.ArgumentParser()
    parser.add_argument("target", nargs="+", choices=["formant", "mfcc", "pitch"])
    args = parser.parse_args(sys.argv[1:]).target

    if "formant" in args:
        print("Getting formant data")
        get_formant_data()
    if "pitch" in args:
        print("Getting pitch data")
        get_pitch_data()
    if "mfcc" in args:
        print("Getting MFCC data")
        get_mfcc_data()
