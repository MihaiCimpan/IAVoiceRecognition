/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external.interfaces;

/**
 *
 * @author Costi
 */
import internal.models.Interval;
import java.util.*;

public interface AIVocalicZonesEstimator 
{
    public List<Interval> estimateVocalicZonesFromAudioSignal(double[] audioSignal);
}
