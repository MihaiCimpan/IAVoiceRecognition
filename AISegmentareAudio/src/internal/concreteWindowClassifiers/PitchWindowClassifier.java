/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.concreteWindowClassifiers;

import internal.interfaces.AIWindowClassifier;
import internal.models.AnnotatedWindow;
import internal.models.Window;
import java.util.*;

import be.tarsos.dsp.pitch.*;
import com.sun.prism.shader.DrawEllipse_ImagePattern_AlphaTest_Loader;

/**
 *
 * @author Costi
 */
public class PitchWindowClassifier implements AIWindowClassifier {

    private float thresholdPitchProbability;
    
    public PitchWindowClassifier(float thresholdPitchProbability)
    {
        this.thresholdPitchProbability = thresholdPitchProbability;
    }
    
    @Override
    public List<AnnotatedWindow> annotateWindowListGivenSignal(List<Window> windowList, double[] signal) 
    {
        List<AnnotatedWindow> list = new ArrayList<>();
        
        for(Window window : windowList)
        {
            AnnotatedWindow.AnnotationType annotationType = AnnotatedWindow.AnnotationType.Undecidable;
            
            float[] audio = doubleToFloat(window.getValues());
            FastYin fastYin = new FastYin(16, audio.length);
            PitchDetectionResult pitchResult =  fastYin.getPitch(audio);
            
            if (pitchResult.getProbability() >= this.thresholdPitchProbability) 
            {
                annotationType = AnnotatedWindow.AnnotationType.Voiced;
            }
            
            AnnotatedWindow aw = new AnnotatedWindow(window, annotationType);
            list.add(aw);
        }
        
        return list;
    }
    
    private float[] doubleToFloat(double[] signal)
    {
        float[] fSignal = new float[signal.length];
        
        for(int i=0; i<signal.length;i++)
        {
            fSignal[i] = (float)signal[i];
        }
        
        return fSignal;
    }
}
