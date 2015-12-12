/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.*;
/**
 *
 * @author Costi
 */
public interface AIVocalicThresholdEstimator 
{   
    public double estimateBestVocalicThresholdFromList(List<Double> errorList);
}
