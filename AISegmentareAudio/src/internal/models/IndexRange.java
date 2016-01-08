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
public class IndexRange {
    
    private final int startIndex;
    private final int stopIndex;
    
    
    public int getStartIndex()
    {
        return startIndex;
    }
    
    public int getStopIndex()
    {
        return stopIndex;
    }
    
    public IndexRange(int start, int stop)
    {
        this.startIndex = start;
        this.stopIndex = stop;
    }

    @Override
    public String toString() {
        return "[" + startIndex + ", " + stopIndex + "]";
    }
  
}
