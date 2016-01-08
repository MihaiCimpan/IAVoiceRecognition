/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.models;

import internal.interfaces.AIVocalicThresholdEstimator;

/**
 *
 * @author Costi
 */
public class AnnotatedWindow {
    
    public enum AnnotationType
    {
        Voiced,
        NonVoiced,
        Undecidable
    }
    
    private final Window window;
    private  AnnotationType annotationType;
    
    public AnnotationType geAnnotationType()
    {
        return annotationType;
    }
    
    public void setAnnotationType(AnnotationType newType)
    {
        this.annotationType = newType;
    }
    
    public Window getWindow()
    {
        return window;
    }
    
    public AnnotatedWindow(Window w, AnnotationType annotationType)
    {
        this.annotationType = annotationType;
        this.window = w;
    }
    
}
