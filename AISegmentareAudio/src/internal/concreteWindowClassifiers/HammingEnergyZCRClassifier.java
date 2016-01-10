/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.concreteWindowClassifiers;

import internal.utilities.*;
import internal.models.*;
import java.util.*;

/**
 *
 * @author Costi
 */
public class HammingEnergyZCRClassifier extends AbstractEnergyAndZCRClassifier
{

    @Override
    protected List<FeaturedWindow> computeFeaturedWindowsFromList(List<Window> windowList) 
    {
        List<FeaturedWindow> list = new ArrayList<>();
        
        for(Window w : windowList)
        {
            double zcr = AudioSegmentationUtils.zeroCrossingRateUsingHammingWindow(w.getValues());
            double en  = AudioSegmentationUtils.energyCalculatedWithHammingWindow(w.getValues());
            FeaturedWindow fw = new FeaturedWindow(w, en, zcr);
            list.add(fw);
        }
        
        return list;
    }
    
}
