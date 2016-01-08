/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external.interfaces;
import java.util.*;
import internal.models.IndexRange;

/**
 *
 * @author Costi
 */
public interface AIAudioSegmentator {
    
    public List<IndexRange> getIndexRangesOfVocalicZonesFromSignal(double[] signal);
}
