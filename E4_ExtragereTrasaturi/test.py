import os
from subprocess import Popen

praat_loc = r'.\Praat.exe'
script_loc = r'.\max_pitch.praat'
sound_loc = r'.\sound_files\\'

textview_loc = sound_loc
output_loc = os.path.join(sound_loc, "res.out")

Popen(r"{} --run {} {} .wav {} .TextGrid D:\tmp\res.out sentence 0.01 75 300".format(
    praat_loc, script_loc, sound_loc, textview_loc, output_loc)
).wait()