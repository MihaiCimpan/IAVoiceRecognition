/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.utilities;

/**
 *
 * @author Costi
 */
public class AudioSegmentationUtils {
    
    public static double[] extractWindow(double[] source, int windowSize, int windowStep,
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
        
        return result;
    }
}
