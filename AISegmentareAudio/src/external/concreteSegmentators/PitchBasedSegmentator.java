/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external.concreteSegmentators;
import external.interfaces.*;
import internal.concreteWindowClassifiers.PitchWindowClassifier;
import internal.models.IndexRange;
import java.util.List;
import internal.utilities.AudioSegmentationUtils;
import internal.models.*;
import internal.ConcreteAnnotationSmoothers.NaiveMNeighborsSmoother;
/**
 *
 * @author Costi
 */
public class PitchBasedSegmentator implements AIAudioSegmentator
{
    private PitchWindowClassifier pitchWindowClassifier = new PitchWindowClassifier((float)0.6);
    
    @Override
    public List<IndexRange> getIndexRangesOfVocalicZonesFromSignal(double[] signal) 
    {
        List<Window> windowList = AudioSegmentationUtils.getWindowListFromSignalWithSizeAndStep(signal, 
                   500, 250);
        
        List<AnnotatedWindow> annotatedWindows = pitchWindowClassifier.annotateWindowListGivenSignal(windowList, signal);
        NaiveMNeighborsSmoother smoother = new NaiveMNeighborsSmoother(2);
        smoother.smoothenAnnotedWindowList(annotatedWindows);
        
        return AudioSegmentationUtils.getIndexRangesOfVoicedSpeechFromWindows(annotatedWindows);
    }
    
}
