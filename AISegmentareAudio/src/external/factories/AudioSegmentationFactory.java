/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external.factories;

import external.interfaces.AIVocalicZonesEstimator;

/**
 *
 * @author IA_AUDIO_Team
 * 
 * This is the class with which the clients of our library will interact
 * Basically, we will instatiate one of the best estimators and return it. 
 */
public class AudioSegmentationFactory 
{
    public static AIVocalicZonesEstimator createVocalicZonesEstimator()
    {
        //
        return null;
    }
}
