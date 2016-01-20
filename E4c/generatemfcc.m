fil = fullfile('wavs','*.wav');
wavsDir = dir(fil);
len = length(wavsDir);

% Define variables
    Tw = 25;                % analysis frame duration (ms)
    Ts = 10;                % analysis frame shift (ms)
    alpha = 0.97;           % preemphasis coefficient
    M = 20;                 % number of filterbank channels 
    C = 12;                 % number of cepstral coefficients
    L = 22;                 % cepstral sine lifter parameter
    LF = 300;               % lower frequency limit (Hz)
    HF = 3700;              % upper frequency limit (Hz)

for i = 1:len
    
    wav_file = wavsDir(i);
    wav_file = fullfile('wavs', wav_file.name);

    % Read speech samples, sampling rate and precision from file
    [ speech, fs ] = audioread( wav_file );


    % Feature extraction (feature vectors as columns)
    [ MFCCs, FBEs, frames ] = ...
                    mfcc( speech, fs, Tw, Ts, alpha, @hamming, [LF HF], M, C+1, L );    
    disp(strrep(['MFCCs: (' sprintf(' %d,', MFCCs) ')'], ',)', ')'));
    [file_name, remain] = strtok(wav_file, '.');
    fid = fopen(strcat(file_name, '.txt'),'wt');  % Note the 'wt' for writing in text mode
    fprintf(fid,'%f\n',MFCCs);  % The format string is applied to each element of a
    fclose(fid);
end

