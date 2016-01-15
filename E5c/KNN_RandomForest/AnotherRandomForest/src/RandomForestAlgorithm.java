import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import libsvm.LibSVM;
import libsvm.svm_parameter;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.classification.tree.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.DatasetTools;
import net.sf.javaml.tools.data.FileHandler;


public class RandomForestAlgorithm {

	public static void main(String[] args) {
		
		Dataset data = null;
		try {
			
			//KNN
			data = FileHandler.loadDataset(new File("datasetAI.data"), 34, ",");
			Classifier knn = new KNearestNeighbors(50);
			CrossValidation cv = new CrossValidation(knn);
			Map<Object, PerformanceMeasure> p;
			double sum;
			double finalSum = 0.0;
			for (int i=1; i<=5; i++)
			{
				p = cv.crossValidation(data, 5, new Random(i));
				sum = 0.0;
				System.out.println("KNN");
				for(Object o:p.keySet())
				    {
				    	if (!Double.isNaN(p.get(o).getPrecision())) {
					    	sum += p.get(o).getPrecision();
				    		System.out.println(o+": "+p.get(o).getPrecision());
				    	}
				    	else
				    		System.out.println(o+": 0.0");

				    }
				System.out.println("Media: " + sum / 7.0);
				finalSum += (sum / 7.0);
				System.out.println();
			}
			System.out.println("Media dupa 5 iteratii: " + finalSum / 5.0);
			System.out.println();

			//Random Forest
			data = FileHandler.loadDataset(new File("datasetAI.data"), 34, ",");
			Classifier randomForest = new RandomForest(100);
			cv = new CrossValidation(randomForest);
			
			finalSum = 0.0;
			for (int i=1; i<=5; i++){
				p = cv.crossValidation(data, 5, new Random(i));
				sum = 0.0;
				System.out.println("Random Forest");
				for(Object o:p.keySet())
				    {
				    	if (!Double.isNaN(p.get(o).getPrecision())) {
					    	sum += p.get(o).getPrecision();
				    		System.out.println(o+": "+p.get(o).getPrecision());
				    	}
				    	else
				    		System.out.println(o+": 0.0");

				    }
				finalSum += (sum / 7.0);
				System.out.println("Media: " + sum / 7.0);
				System.out.println();
			}
			
			System.out.println("Media dupa 5 iteratii: " + finalSum / 5.0);
			System.out.println();
			
			
			//SVM
			/*data = FileHandler.loadDataset(new File("datasetAI.data"), 34, ",");

			Dataset training = new DefaultDataset();
			Dataset test = new DefaultDataset();
			
			for (Instance instance : data){
				if (Math.random() < 0.75) {
					training.add(instance);
				}
				else
					test.add(instance);
			}
			
			LibSVM svm = new LibSVM();
			
			svm_parameter param = new svm_parameter();
			param.kernel_type = libsvm.svm_parameter.POLY;

			svm.setParameters(param);
			svm.buildClassifier(training);
			
			int correct = 0, wrong = 0;
			for (Instance inst : test) {
			    Object predictedClassValue = svm.classify(inst);
			    Object realClassValue = inst.classValue();
			    if (predictedClassValue.equals(realClassValue))
			        correct++;
			    else
			        wrong++;
			}
			
			System.out.println("Result: " + correct + " and " + (correct * 100.0) / (correct+wrong) + "%.");
			*/
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
