/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.interfaces;

import java.util.*;
/**
 *
 * @author Costi
 */
public interface AIPredictionAlgorithm 
{
    
    public List<Double> calculateErrorsBasedOnSignalAndWindowSizeAndStep(double[] signal,
                                                                         int windowSize,
                                                                         int windowStep);
    
}