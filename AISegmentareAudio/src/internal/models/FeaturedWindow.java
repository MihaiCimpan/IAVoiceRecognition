/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.models;

/**
 *
 * @author Costi
 */
public class FeaturedWindow {
    
    private final Window window;
    private final double energy;
    private final double zeroCrossingRate;
    
    public FeaturedWindow(Window window, double energy, double zeroCrossingRate)
    {
        this.window = window;
        this.energy = energy;
        this.zeroCrossingRate = zeroCrossingRate;
    }
    
    public Window getWindow()
    {
        return window;
    }
    
    public double getEnergy()
    {
        return energy;
    }
    
    public double getZeroCrossingRate()
    {
        return zeroCrossingRate;
    }
}
