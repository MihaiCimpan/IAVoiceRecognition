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
public class GaussianParameters {
    
    private final double _center;
    private final double _amplitutde;
    private final double _dispersion;
    
    public double getCenter()
    {
        return _center;
    }
    
    public double getAmplitude()
    {
        return _amplitutde;
    }
    
    public double getDispersion()
    {
        return _dispersion;
    }
    
    public GaussianParameters(double center, double amplitude, double dispersion)
    {
        _amplitutde = amplitude;
        _dispersion = dispersion;
        _center = center;
    }
}
