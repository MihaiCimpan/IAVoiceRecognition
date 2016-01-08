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
public class Window {
    
    private final double[] values;
    
    private final int startIndexFromSignal;
    private final int stopIndexFromSignal;
    
    public double[] getValues()
    {
        return this.values;
    }
    
    public int getStartIndexInSignal()
    {
        return startIndexFromSignal;
    }
    
    public int getStopIndexInSignal()
    {
        return stopIndexFromSignal;
    }
    
    public Window(double[] values, int startInSignal, int stopInSignal)
    {
        this.values = values;
        this.startIndexFromSignal = startInSignal;
        this.stopIndexFromSignal = stopInSignal;
    }
}
