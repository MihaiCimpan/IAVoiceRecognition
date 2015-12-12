/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal.concretePredictionAlgorithms;
import internal.interfaces.AIPredictionAlgorithm;
import java.util.*;

import org.encog.neural.networks.*;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.training.*;
import org.encog.neural.networks.training.propagation.resilient.*;
import org.encog.neural.networks.layers.BasicLayer; 
import org.encog.engine.network.activation.*;
import internal.utilities.AudioSegmentationUtils;

/**
 *
 * @author Costi
 */
public class EncogNNPrediction implements AIPredictionAlgorithm 
{
    
    private class NeuralNetworkTrainingSample
    {
        public double[][] inputData;
        public double[][] shouldBePredictedData;
    }

    @Override
    public List<Double> calculateErrorsBasedOnSignalAndWindowSizeAndStep(double[] signal, int windowSize, 
                                                                                          int windowStep)
    {
        
        List<Double> list = new ArrayList<>();
        
        BasicNetwork basicNetwork = this.createBasicNetwork();
        
        int currentIteration = 0;
        boolean shouldStop = false;
        while (!shouldStop) 
        {            
            NeuralNetworkTrainingSample ts = this.createNeuralInputAtIterationFrom
                                            (signal, windowSize, windowStep, currentIteration);
            
            if (ts != null) 
            {
                int howManyIterations = currentIteration == 0 ? 500 : 100;
                double error = this.trainNetworkWithSample(basicNetwork, ts, howManyIterations);
                list.add(error);
                
                System.out.println(list.size());
            }
            else
                shouldStop = true;
            
            currentIteration++;
        }
        
        return  list;
    }
    
    private NeuralNetworkTrainingSample createNeuralInputAtIterationFrom(double[] source, int windowSize,
                                                        int windowStep, int iteration)
    {
        
        double[] inputWindow = AudioSegmentationUtils.extractWindow(source, windowSize, windowStep, iteration);
        double[] shouldBePredictedWindow = AudioSegmentationUtils.extractWindow(source, windowSize, windowStep, iteration+1);
        
        if(inputWindow != null && shouldBePredictedWindow != null)
        {
            double[][] inputData = new double[inputWindow.length][1];
            double[][] outputData = new double[inputWindow.length][1];
            for(int i=0; i<inputData.length; i++)
            {
                inputData[i] = new double[1];
                inputData[i][0] = inputWindow[i];
                
                outputData[i] = new double[1];
                outputData[i][0] = shouldBePredictedWindow[i];
            }
            
            NeuralNetworkTrainingSample ts = new NeuralNetworkTrainingSample();
            ts.inputData = inputData;
            ts.shouldBePredictedData = outputData;
            
            return ts;
        }
        
        return null;
    }
    
    
    private double trainNetworkWithSample(BasicNetwork network, 
            NeuralNetworkTrainingSample sample,
            int iterations)
    {
        
        BasicNeuralDataSet set = new BasicNeuralDataSet(sample.inputData, 
                                                sample.shouldBePredictedData);
        
        
        Train train = new ResilientPropagation(network, set);
        train.iteration(iterations);
        return train.getError();
    }
    
    
    private BasicNetwork createBasicNetwork()
    {
        BasicNetwork network = new BasicNetwork();
        
        network.addLayer(new BasicLayer(null,true,1));
	network.addLayer(new BasicLayer(new ActivationSigmoid(),true,5));
	network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
        network.getStructure().finalizeStructure();
        network.reset();
        
        return network;
    }
    
}
