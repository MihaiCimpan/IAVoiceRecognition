/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.concreteWindowClassifiers;
import internal.interfaces.*;
import internal.models.*;
import java.util.*;
import internal.utilities.*;

/**
 *
 * @author Costi
 */
public abstract class AbstractEnergyAndZCRClassifier implements AIWindowClassifier
{
    
        public List<AnnotatedWindow> annotateWindowListGivenSignal(List<Window> windowList,
            double[] signal)
        {
            List<AnnotatedWindow> list = new ArrayList<>();
            List<FeaturedWindow> featuredWindows = this.computeFeaturedWindowsFromList(windowList);
            FeaturedWindow thresholdWindow = this.findThresholdWindowInList(featuredWindows);
            
            for(FeaturedWindow fw : featuredWindows)
            {
                AnnotatedWindow.AnnotationType type = AnnotatedWindow.AnnotationType.Undecidable;
                if(fw.getEnergy() > thresholdWindow.getEnergy() && 
                   fw.getZeroCrossingRate() < thresholdWindow.getZeroCrossingRate())
                {
                    type = AnnotatedWindow.AnnotationType.Voiced;
                }
                
                if(fw.getEnergy() < thresholdWindow.getEnergy() && 
                   fw.getZeroCrossingRate() > thresholdWindow.getZeroCrossingRate())
                {
                    type = AnnotatedWindow.AnnotationType.NonVoiced;
                }
                
                AnnotatedWindow aw = new AnnotatedWindow(fw.getWindow(), type);
            }
            
            return list;
        }
    
    
    protected FeaturedWindow findThresholdWindowInList(List<FeaturedWindow> featuredWindows)
    {
        FeaturedWindow window = featuredWindows.get(0);
        double minDifference = Math.abs(window.getEnergy() - window.getZeroCrossingRate());
        
        for(FeaturedWindow w : featuredWindows)
        {
            double difference = Math.abs(w.getEnergy() - w.getZeroCrossingRate());
            if(difference < minDifference)
            {
                minDifference = difference;
                window = w;
            }
        }
        
        return window;
    }
    
    
    
    protected abstract List<FeaturedWindow> computeFeaturedWindowsFromList(List<Window> windowList);
    
}
