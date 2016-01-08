/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.concreteWindowClassifiers;

import internal.models.FeaturedWindow;
import internal.models.Window;
import internal.utilities.AudioSegmentationUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Costi
 */
public class SimpleEnergyAndZCRClassifier extends AbstractEnergyAndZCRClassifier
{

    @Override
    protected List<FeaturedWindow> computeFeaturedWindowsFromList(List<Window> windowList) 
    {
        List<FeaturedWindow> list = new ArrayList<>();
        
        for(Window w : windowList)
        {
            double zcr = AudioSegmentationUtils.zeroCrossingRateForSignal(w.getValues());
            double en  = AudioSegmentationUtils.energyCalculatedWithRectangularWindow(w.getValues());
            FeaturedWindow fw = new FeaturedWindow(w, en, zcr);
            list.add(fw);
        }
        
        return list;
    }
    
}
