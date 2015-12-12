/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aisegmentareaudio;

import interfaces.*;
import concretePredictionAlgorithms.*;
import java.util.*;


/**
 *
 * @author Costi
 */
public class AISegmentareAudio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        AIPredictionAlgorithm algorithm = new EncogNNPrediction();
        double[] signal = AISegmentareAudio.generateRandomSignal(10000);
        List<Double> errorList = algorithm.calculateErrorsBasedOnSignalAndWindowSizeAndStep(signal, 500, 250);
        System.out.println(errorList);
    }
    
    
    public static double[] generateRandomSignal(int count)
    {
        double[] signal = new double[count];
        
        Random r = new Random();
        
        for(int i=0; i<count; i++)
        {
            double item = r.nextDouble();
            if(item < 0) {
                item = -item;
            }
            
            signal[i] = item;
        }
        
        return signal;
    }
    
}
