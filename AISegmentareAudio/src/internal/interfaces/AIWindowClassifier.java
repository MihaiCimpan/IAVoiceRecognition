/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.interfaces;

import java.util.*;
import internal.models.*;

/**
 *
 * @author Costi
 */
public interface AIWindowClassifier {
    
    public List<AnnotatedWindow> annotateWindowListGivenSignal(List<Window> windowList,
            double[] signal);
    
}
