/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external.concreteSegmentators;

import external.interfaces.AIAudioSegmentator;
import internal.ConcreteAnnotationSmoothers.NaiveMNeighborsSmoother;
import internal.concreteWindowClassifiers.*;
import internal.models.AnnotatedWindow;
import internal.models.IndexRange;
import internal.models.Window;
import internal.utilities.AudioSegmentationUtils;
import java.util.List;

/**
 *
 * @author Costi
 */
public class PredictiveNNSegmentator implements AIAudioSegmentator
{    
    @Override
    public List<IndexRange> getIndexRangesOfVocalicZonesFromSignal(double[] signal) 
    {
        List<Window> windowList = AudioSegmentationUtils.getWindowListFromSignalWithSizeAndStep(signal, 
                   500, 250);
        
        PredictiveNNWindowClassifier classifier = new PredictiveNNWindowClassifier(500, 250);
        
        List<AnnotatedWindow> annotatedWindows = classifier.annotateWindowListGivenSignal(windowList, signal);
        NaiveMNeighborsSmoother smoother = new NaiveMNeighborsSmoother(2);
        smoother.smoothenAnnotedWindowList(annotatedWindows);
        
        return AudioSegmentationUtils.getIndexRangesOfVoicedSpeechFromWindows(annotatedWindows);
    }
}
