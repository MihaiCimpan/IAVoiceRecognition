# Source: http://phon.chass.ncsu.edu/manual/wav2mfcc.praat

###################################################################
# wav2mfcc.praat        revised March 13, 2013
# Jeff Mielke
# read all the wav files in a directory, make mfcc objects from them, and save as matrices
###################################################################

form Extract MFCC from files in folder
	comment Directory of sound files
	text directory C:\_facultate\IA\audio_input\
endform

# FIND ALL WAV FILES IN THE DIRECTORY
Create Strings as file list... list 'directory$'/*.wav
numberOfFiles = Get number of strings

# LOOP THROUGH ALL THE WAV FILES
for ifile to numberOfFiles

    #OPEN THEM
    select Strings list
    filename$ = Get string... ifile
    baseFile$ = filename$ - ".wav"
    Read from file... 'directory$'/'baseFile$'.wav
    
    #CONVERT THEM AND SAVE THEM
    select Sound 'baseFile$'
    # To MelFilter... 0.015 0.005 100 100 0
    To MFCC... 12 0.015 0.005 100.0 100.0 0.0
    To Matrix
    Write to matrix text file... 'directory$'/'baseFile$'.txt

    #CLEAN UP
    # select MelFilter 'baseFile$'
    select MFCC 'baseFile$'
    plus Matrix 'baseFile$'
    plus Sound 'baseFile$'
    Remove

endfor

select Strings list
Remove