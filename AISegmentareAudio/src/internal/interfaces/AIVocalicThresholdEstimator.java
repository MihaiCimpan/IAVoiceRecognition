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
public interface AIVocalicThresholdEstimator 
{   
    //this is the vocalic boundary. Anything greater is considered
    // to be a part of the consonant zone.
    public double estimateBestVocalicThresholdFromList(List<Double> errorList);
}
