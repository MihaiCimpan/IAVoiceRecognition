/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.concreteWindowClassifiers;

import internal.concretePredictionAlgorithms.EncogNNPrediction;
import internal.interfaces.*;
import internal.models.AnnotatedWindow;
import internal.models.Window;
import java.util.*;

/**
 *
 * @author Costi
 */
public class PredictiveNNWindowClassifier implements AIWindowClassifier
{
    private AIPredictionAlgorithm predAlg = new EncogNNPrediction();
    
    final private int windowSize;
    final private int windowStep;
    
    public PredictiveNNWindowClassifier(int windowSize, int windowStep)
    {
        this.windowSize = windowSize;
        this.windowStep = windowStep;
    }
    
    @Override
    public List<AnnotatedWindow> annotateWindowListGivenSignal(List<Window> windowList, double[] signal) 
    {
        List<AnnotatedWindow> annotedList = new ArrayList<>();
        List<Double> errorList = this.predAlg.calculateErrorsBasedOnSignalAndWindowSizeAndStep
        (signal, windowSize, windowStep);
        
        double globalErrorMean = this.meanOfDoubles(errorList);
        
        if (errorList.size() != windowList.size()) 
        {
            System.out.println("WARNING: THE NUMBER OF ERRORS DOES NOT MATCH THE NR OF WINDOWS");
            System.out.println("WILL CONTINUE ANYWAY ... ");
            System.out.println("Error count " + errorList.size());
            System.out.println("Window count " + windowList.size());
        }
        
        for(int i=0; i<windowList.size() && i<errorList.size(); i++)
        {
            Window w = windowList.get(i);
            AnnotatedWindow.AnnotationType type = AnnotatedWindow.AnnotationType.Undecidable;
            double currentError = errorList.get(i);
            
            if(currentError < globalErrorMean)
            {
                type = AnnotatedWindow.AnnotationType.Voiced;
            }
            
            AnnotatedWindow aw = new AnnotatedWindow(w, type);
            annotedList.add(aw);
        }
        
        return annotedList;
    }
    
    
    private double meanOfDoubles(List<Double> list)
    {
        double sum = 0.0;
        
        for(Double d : list)
        {
            sum += d;
        }
        
        return sum / list.size();
    }
}
