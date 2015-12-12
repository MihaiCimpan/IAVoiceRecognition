/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.interfaces;

import internal.models.GaussianParametersPair;
import java.util.*;

/**
 *
 * @author Costi
 */
public interface AIGaussianParametersEstimator {
    
    public GaussianParametersPair estimateBestGaussianPairForValues(List<Double> valueList);
    
}
