/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.utilities;
import edu.emory.mathcs.jtransforms.fft.*;
import internal.models.AnnotatedWindow;
import internal.models.IndexRange;
import java.util.*;
import internal.models.Window;
import java.math.*;
/**
 *
 * @author Costi
 */
public class AudioSegmentationUtils {
    
    public static Window extractWindow(double[] source, int windowSize, int windowStep,
                                   int iteration)
    {
        
        double[] result = new double[windowSize];
        
        int startIndex = iteration * windowStep;
        
        int count = 0;
        int index = startIndex;
        
        if (startIndex >= source.length) 
        {
            return null;
        }
        
        while(count < windowSize && index <source.length)
        {
            result[count] = source[index];
            count++;
            index++;
        }
        
        while(count < windowSize)
        {
            result[count] = 0.0f;
            count++;
        }
        
        Window win = new Window(result, startIndex, index-1);
        
        return win;
    }
    
    
    public static List<Window> getWindowListFromSignalWithSizeAndStep(double[] signal, 
                                                                       int windowSize,
                                                                       int windowStep)
    {
        List<Window> list = new ArrayList<>();
        
        for(int i=0; i<signal.length - windowSize; i++)
        {
            Window w = extractWindow(signal, windowSize, windowStep, i);
            if(w!= null)
            {
                list.add(w);
            }
        }
        
        return list;
    }
    
    public static double[] computeUnnormalizedAutocorrelation(double[] signal)
    {
        double[] corr = new double[signal.length];
        
        bruteForceAutoCorrelation(signal, corr);
        
        return corr;
    }
    
    public static void bruteForceAutoCorrelation(double [] x, double [] ac) {
        Arrays.fill(ac, 0);
        int n = x.length;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                ac[j] += x[i] * x[(n + i - j) % n];
            }
        }
    }

    public static double sqr(double x) {
        return x * x;
    }
    
    public static double[] fastAutoCorrelation(double[] x)
    {
        double[] corr = new double[x.length];
        fftAutoCorrelation(x, corr);
        
        return corr;
    }

    public static void fftAutoCorrelation(double [] x, double [] ac) {
        int n = x.length;
        // Assumes n is even.
        DoubleFFT_1D fft = new DoubleFFT_1D(n);
        fft.realForward(x);
        ac[0] = sqr(x[0]);
        // ac[0] = 0;  // For statistical convention, zero out the mean 
        ac[1] = sqr(x[1]);
        for (int i = 2; i < n; i += 2) {
            ac[i] = sqr(x[i]) + sqr(x[i+1]);
            ac[i+1] = 0;
        }
        DoubleFFT_1D ifft = new DoubleFFT_1D(n); 
        ifft.realInverse(ac, true);
        // For statistical convention, normalize by dividing through with variance
        //for (int i = 1; i < n; i++)
        //    ac[i] /= ac[0];
        //ac[0] = 1;
    }
    
    
    public static double computedBSPLOfSignal(double[] signal)
    {
        double sum = 0;
        
        for(int i=0; i<signal.length; i++)
        {
            sum += signal[i];
        }
        
        sum = Math.pow(sum, 0.5);
        sum = sum / signal.length;
        
        sum = 20 * Math.log10(sum);
        
        return sum;
    }
    
    public static double maxSignalValue(double[] signal)
    {
        double max = signal[0];
        
        for(int i=1;i<signal.length; i++)
        {
            if (signal[i] > max) 
            {
                max = signal[i];
            }
        }
        
        return max;
    }
    
    
    public static double[] normalizedSignal(double[] signal)
    {
        double[] resultSignal = new double[signal.length];
        
        double maxValue = maxSignalValue(signal);
        
        for(int i=0; i< signal.length; i++)
        {
            resultSignal[i] = signal[i] / maxValue;
        }
        
        return resultSignal;
    }
    
    
    
    public static double hammingWindowValueForIndex(int index, int size)
    {
        if(index < 0 || index > size)
        {
            return 0;
        }
        
        return 0.54 + 0.46*Math.cos(3.14 * (double)index / (double)size);
    }
    
    public static double energyCalculatedWithRectangularWindow(double[] signal)
    {
        double en = 0.0;
        
        for(int i=0; i<signal.length; i++)
        {
            en = en + Math.pow(signal[i], 2);
        }
        
        return en;
    }
    
    public static double zeroCrossingRateForSignal(double[] signal)
    {
        double crossings = 0;
        
        for(int i=1; i<signal.length; i++)
        {
            if (signal[i] * signal[i-1] < 0) 
            {
                crossings++;
            }
        }
        
        return crossings;
    }
    
    public static double energyCalculatedWithHammingWindow(double[] signal)
    {
        double en = 0.0;
        
        for(int i=0; i<signal.length; i++)
        {
            double windowed = hammingWindowValueForIndex(i, signal.length);
            en += signal[i] * signal[i] * windowed * windowed;
        }
        
        return en;
    }
    
   
    
    public static double zeroCrossingRateUsingHammingWindow(double[] signal)
    {
        double zcr = 0;
        
        for(int i=1;i<signal.length;i++)
        {
            zcr += hammingWindowValueForIndex(i, signal.length)*Math.abs(
                    Math.signum(signal[i]) - Math.signum(signal[i-1])
            );
        }
        
        return zcr * 0.5;
    }
    
    
    public static List<IndexRange> getIndexRangesOfVoicedSpeechFromWindows(List<AnnotatedWindow> windowList)
    {
        List<IndexRange> list = new ArrayList<>();
        
        int index = 0;
        while(index < windowList.size())
        {
            int firstIndexOfVoicedSequenceOfWindows = findIndexOfFirstVoicedWindow(windowList, index);
            if (firstIndexOfVoicedSequenceOfWindows >= 0) 
            {
                AnnotatedWindow firstAnnotatedWindow = windowList.get(firstIndexOfVoicedSequenceOfWindows);
                AnnotatedWindow lastAnnotatedWindow = firstAnnotatedWindow;
                
                int lastIndexOfVoicedSequenceOfWindows = firstIndexOfVoicedSequenceOfWindows;
                AnnotatedWindow w = windowList.get(lastIndexOfVoicedSequenceOfWindows);
                
                while (w.geAnnotationType() == AnnotatedWindow.AnnotationType.Voiced && 
                        lastIndexOfVoicedSequenceOfWindows < windowList.size()-1) 
                {
                    lastIndexOfVoicedSequenceOfWindows++;
                    w = windowList.get(lastIndexOfVoicedSequenceOfWindows);
                }
                
                if (w.geAnnotationType() == AnnotatedWindow.AnnotationType.Voiced) 
                {
                    //this  would happen when the sequence is spanned from firstIndexOfVoiced to the end
                    lastAnnotatedWindow = w;
                }
                else
                {
                    //the while stopped at a window which is classified as nonvoiced
                    //therefore the previous window must have been voiced
                    
                    lastAnnotatedWindow = windowList.get(lastIndexOfVoicedSequenceOfWindows-1);
                }
                
                IndexRange range = new IndexRange(firstAnnotatedWindow.getWindow().getStartIndexInSignal(), 
                        lastAnnotatedWindow.getWindow().getStopIndexInSignal());
                
                list.add(range);
                index = lastIndexOfVoicedSequenceOfWindows+1;
            }
            else
            {
                break;
            }
        }
        
        return list;
    }
    
    
    public static int findIndexOfFirstVoicedWindow(List<AnnotatedWindow> windowList, int startingAtIndex)
    {
        int i = startingAtIndex;
        AnnotatedWindow w = windowList.get(i);
        i++;
        
        while (w.geAnnotationType() != AnnotatedWindow.AnnotationType.Voiced && i<windowList.size()) 
        {            
            w = windowList.get(i);
            i++;
        }
        
        if (w.geAnnotationType() == AnnotatedWindow.AnnotationType.Voiced) 
        {
            return i-1;
        }
        
        return -1;
    }
}
