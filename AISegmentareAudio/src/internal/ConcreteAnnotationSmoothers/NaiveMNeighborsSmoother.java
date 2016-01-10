/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.ConcreteAnnotationSmoothers;
import internal.interfaces.*;
import internal.models.AnnotatedWindow;
import java.util.List;

/**
 *
 * @author Costi
 */
public class NaiveMNeighborsSmoother implements AnnotationsSmoother {

    private int leftRightNeighborsCount;
    
    public NaiveMNeighborsSmoother(int leftRightNeighboursCount)
    {
        this.leftRightNeighborsCount = leftRightNeighboursCount;
    }
    
    @Override
    public void smoothenAnnotedWindowList(List<AnnotatedWindow> windowList) 
    {
        for(int i=leftRightNeighborsCount; i < windowList.size() - leftRightNeighborsCount; i++)
        {
            AnnotatedWindow aw = windowList.get(i);
            if (aw.geAnnotationType() == AnnotatedWindow.AnnotationType.Undecidable ||
                aw.geAnnotationType() == AnnotatedWindow.AnnotationType.NonVoiced) 
            {
                boolean allNeighboursVoiced = true;
                int times = 0;
                int jLeft = i;
                int jRight = i;
                
                while(times < this.leftRightNeighborsCount)
                {
                    jLeft--;
                    jRight++;
                    
                    AnnotatedWindow wl = windowList.get(jLeft);
                    AnnotatedWindow wr = windowList.get(jRight);
                    
                    if(wl.geAnnotationType() != AnnotatedWindow.AnnotationType.Voiced || 
                       wr.geAnnotationType() != AnnotatedWindow.AnnotationType.Voiced)
                    {
                        allNeighboursVoiced = false;
                        break;
                    }
                    
                }
                
                if (allNeighboursVoiced) {
                    aw.setAnnotationType(AnnotatedWindow.AnnotationType.Voiced);
                }
            }
        }
        
    }
    
}
