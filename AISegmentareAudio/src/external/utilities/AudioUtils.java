/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external.utilities;
import com.ritolaaudio.simplewavio.*;
import internal.models.IndexRange;
import java.io.File;
import java.util.*;

/**
 *
 * @author Costi
 */
public class AudioUtils 
{
    public static class MillisecondsRange
    {
        final private float startMs;
        final private float stopMs;
        
        public MillisecondsRange(float start, float stop)
        {
            this.startMs = start;
            this.stopMs = stop;
        }
        
        public float getStartMs()
        {
            return this.startMs;
        }
        
        public float getStopMs()
        {
            return this.stopMs;
        }

        @Override
        public String toString() 
        {
            return "[" + this.startMs + "ms, " + this.stopMs +"ms]";
        }
        
        
    }
    
    public static double[] readWaveFromPath(String filePath) throws Exception
    {
        
        float[][] audioFloats = Utils.WAVToFloats(new File(filePath));
        
        double[] result = new double[audioFloats.length];
        
        //We assume it is a mono recorded audio
        for(int i=0; i<audioFloats.length;i++)
        {
            result[i] = audioFloats[i][0];
        }
        
        return result;
    }
    
    
    public static List<MillisecondsRange> getRangesInMsFromIndexRanges
        (List<IndexRange> indexRangesList, int recordedFrequency)
        {
            List<MillisecondsRange> list = new ArrayList<>();
            
            indexRangesList.stream().map((idr) -> {
                float start = (float)idr.getStartIndex() / (float)recordedFrequency;
                float stop = (float)idr.getStopIndex() / (float)recordedFrequency;
                MillisecondsRange mr = new AudioUtils.MillisecondsRange(start, stop);
            return mr;
            }).forEach((mr) -> {
                list.add(mr);
        });
            
            return list;
        }
    
        
    public static void printDoubles(double[] doubles)
    {
        System.out.println("///////////////////////////////////");
        System.out.println("[ ");
        for(int i=0; i<doubles.length; i++)
        {
            System.out.print(doubles[i] +" ");
        }
        System.out.print(" ]");
        System.out.println("");
    }
}
