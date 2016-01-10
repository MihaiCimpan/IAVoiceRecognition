/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aisegmentareaudio;

import internal.concretePredictionAlgorithms.EncogNNPrediction;
import internal.interfaces.AIPredictionAlgorithm;
import java.util.*;
import internal.utilities.*;
import internal.models.*;
import external.utilities.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import external.concreteSegmentators.*;

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
        
        
        try {
            double[] audioSignal = AudioUtils.readWaveFromPath("WavFiles/m04ah.wav");
            //AudioUtils.printDoubles(audioSignal);
            
            System.out.println("Pitch based segmentator gives: ");
            PitchBasedSegmentator pbs = new PitchBasedSegmentator();
            List<IndexRange> vowelRanges = pbs.getIndexRangesOfVocalicZonesFromSignal(audioSignal);
            List<AudioUtils.MillisecondsRange> millisecondsRanges = AudioUtils.getRangesInMsFromIndexRanges(vowelRanges, 16000);
            System.out.println(millisecondsRanges);
            
            
            System.out.println("Hamming Windowed Energy and ZCR segmentator gives: ");
            HammingEnergyAndZCRSegmentator hmEZCR = new HammingEnergyAndZCRSegmentator();
            vowelRanges = hmEZCR.getIndexRangesOfVocalicZonesFromSignal(audioSignal);
            millisecondsRanges = AudioUtils.getRangesInMsFromIndexRanges(vowelRanges, 16000);
            System.out.println(millisecondsRanges);
            
            System.out.println("Simple Windowed Energy and ZCR segmentator gives: ");
            SimpleEnergyAndZCRSegmentator simEZCR = new SimpleEnergyAndZCRSegmentator();
            vowelRanges = simEZCR.getIndexRangesOfVocalicZonesFromSignal(audioSignal);
            millisecondsRanges = AudioUtils.getRangesInMsFromIndexRanges(vowelRanges, 16000);
            System.out.println(millisecondsRanges);
            
            System.out.println("Predictive NN  segmentator gives: ");
            PredictiveNNSegmentator pnnsgmt = new PredictiveNNSegmentator();
            vowelRanges = pnnsgmt.getIndexRangesOfVocalicZonesFromSignal(audioSignal);
            millisecondsRanges = AudioUtils.getRangesInMsFromIndexRanges(vowelRanges, 16000);
            System.out.println(millisecondsRanges);
            
            /*Window w1 = new Window(null, 0, 2);
            Window w2 = new Window(null, 1, 3);
            Window w3 = new Window(null, 2, 5);
            
            AnnotatedWindow aw1 = new AnnotatedWindow(w1, AnnotatedWindow.AnnotationType.NonVoiced);
            AnnotatedWindow aw2 = new AnnotatedWindow(w2, AnnotatedWindow.AnnotationType.NonVoiced);
            AnnotatedWindow aw3 = new AnnotatedWindow(w3, AnnotatedWindow.AnnotationType.NonVoiced);
            
            List<AnnotatedWindow> list = new ArrayList<>();
            list.add(aw1); list.add(aw2); list.add(aw3);
            
            
            List<IndexRange> ranges = AudioSegmentationUtils.getIndexRangesOfVoicedSpeechFromWindows(list);
            
            System.out.println(ranges);
            */
            /*double[] xcorr = new double[]{1, -81, 2, -15, 8, 2, -9, 0};
            double[] rescorr = AudioSegmentationUtils.fastAutoCorrelation(xcorr);
            
            for(int i=0; i<rescorr.length; i++)
            {
            System.out.print(rescorr[i] + " ");
            }*/
            
            /*AIPredictionAlgorithm algorithm = new EncogNNPrediction();
            double[] signal = AISegmentareAudio.generateRandomSignal(10000);
            List<Double> errorList = algorithm.calculateErrorsBasedOnSignalAndWindowSizeAndStep(signal, 500, 250);
            System.out.println(errorList);
            */
        } catch (Exception ex) {
            Logger.getLogger(AISegmentareAudio.class.getName()).log(Level.SEVERE, null, ex);
        }
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
