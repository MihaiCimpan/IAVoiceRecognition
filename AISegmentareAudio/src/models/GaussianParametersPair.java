/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Costi
 */
public class GaussianParametersPair {
    
    private final GaussianParameters _firstGaussianParameters;
    private final GaussianParameters _secondGaussianParameters;
    
    public GaussianParameters getFirstGaussianParameters()
    {
        return _firstGaussianParameters;
    }
    
    public GaussianParameters getSecondGaussianParameters()
    {
        return _secondGaussianParameters;
    }
    
    public GaussianParametersPair(GaussianParameters firstGaussianParameters, 
                                  GaussianParameters secondGaussianParameters)
    {
        _firstGaussianParameters = firstGaussianParameters;
        _secondGaussianParameters = secondGaussianParameters;
    }
}
