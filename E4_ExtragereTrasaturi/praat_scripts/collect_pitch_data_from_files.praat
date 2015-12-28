# Source: http://www.helsinki.fi/~lennes/praat-scripts/public/collect_pitch_data_from_files.praat

# Modificare: sunt ignorate complet adnotarile, se extrag trasaturi pe intreg fisierul audio

# This script goes through sound and TextGrid files in a directory,
# opens each pair of Sound and TextGrid, calculates the pitch maximum
# of each labeled interval, and saves results to a text file.
# To make some other or additional analyses, you can modify the script
# yourself... it should be reasonably well commented! ;)
#
# This script is distributed under the GNU General Public License.
# Copyright 4.7.2003 Mietta Lennes

form Analyze pitch maxima from labeled segments in files
	text sound_directory C:\_facultate\IA\audio_input\
	sentence Sound_file_extension .wav
	text textGrid_directory C:\_facultate\IA\audio_input\
	sentence TextGrid_file_extension .TextGrid
	text resultfile C:\_facultate\IA\audio_input\pitchresults.txt
	sentence Tier segments
	positive Time_step 0.01
	positive Minimum_pitch_(Hz) 75
	positive Maximum_pitch_(Hz) 300
endform

# Here, you make a listing of all the sound files in a directory.
# The example gets file names ending with ".wav" from D:\tmp\

Create Strings as file list... list 'sound_directory$'*'sound_file_extension$'
numberOfFiles = Get number of strings

# Check if the result file exists:
if fileReadable (resultfile$)
	pause The result file 'resultfile$' already exists! Do you want to overwrite it?
	filedelete 'resultfile$'
endif

# Write a row with column titles to the result file:
# (remember to edit this if you add or change the analyses!)

titleline$ = "Filename	Timestamp	Pitch (Hz)'newline$'"
fileappend "'resultfile$'" 'titleline$'

# Go through all the sound files, one by one:

for ifile to numberOfFiles
	filename$ = Get string... ifile
	# A sound file is opened from the listing:
	Read from file... 'sound_directory$''filename$'
	# Starting from here, you can add everything that should be
	# repeated for every sound file that was opened:
	soundname$ = selected$ ("Sound", 1)
	To Pitch... time_step minimum_pitch maximum_pitch
	# Open a TextGrid by the same name:
	# gridfile$ = "'textGrid_directory$''soundname$''textGrid_file_extension$'"
	# if fileReadable (gridfile$)
		# Read from file... 'gridfile$'
		# Find the tier number that has the label given in the form:
		# call GetTier 'tier$' tier
		# numberOfIntervals = Get number of intervals... tier
		# Pass through all intervals in the selected tier:
        
    tmin = Get start time
    tmax = Get end time
    for i to (tmax-tmin)/0.01
        timestamp = tmin + i * 0.01
        select Pitch 'soundname$'
        pitch = Get value at time: timestamp, "Hertz", "Linear"
        # Save result to text file:
        resultline$ = "'soundname$'	'timestamp'	'pitch''newline$'"
        fileappend "'resultfile$'" 'resultline$'
        #select TextGrid 'soundname$'
    endfor
	# Remove the TextGrid object from the object list
	#	select TextGrid 'soundname$'
	#	Remove
	#endif
	# Remove the temporary objects from the object list
	select Sound 'soundname$'
	plus Pitch 'soundname$'
	Remove
	select Strings list
	# and go on with the next sound file!
endfor

Remove


#-------------
# This procedure finds the number of a tier that has a given label.

procedure GetTier name$ variable$
        numberOfTiers = Get number of tiers
        itier = 1
        repeat
                tier$ = Get tier name... itier
                itier = itier + 1
        until tier$ = name$ or itier > numberOfTiers
        if tier$ <> name$
                'variable$' = 0
        else
                'variable$' = itier - 1
        endif

	if 'variable$' = 0
		exit The tier called 'name$' is missing from the file 'soundname$'!
	endif

endproc