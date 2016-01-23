
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.classification.tree.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

public class RandomForestAlgorithm {

	public static void main(String[] args) {
		
		Dataset data = null;
		try {
			
			//KNN
			Classifier knn;
			CrossValidation cv;
			Map<Object, PerformanceMeasure> p;
			double sum, finalSum;
			finalSum = 0.0;

			long startTime;
			long endTime;
			long totalTime;
			
			for (int i=1; i<=5; i++)
			{
				
				startTime = System.currentTimeMillis();
				
				data = FileHandler.loadDataset(new File("datasetWithoutNull.csv"), 32, ",");
				knn = new KNearestNeighbors(12+i*4);
				cv = new CrossValidation(knn);
				p = cv.crossValidation(data, 10);
				
				endTime = System.currentTimeMillis();

				sum = 0.0;
				System.out.println("KNN");
				for(Object o:p.keySet())
				    {
				    	if (!Double.isNaN(p.get(o).getAccuracy())) {
					    	sum += p.get(o).getAccuracy();
				    		System.out.println(o+": "+p.get(o).getAccuracy());
				    	}
				    	else
				    		System.out.println(o+": 0.0");
	
				    }
				finalSum += (sum / 7.0);
				System.out.println("Media: " + (double)Math.round(sum / 7.0 * 1000d) / 1000d);
				totalTime = endTime - startTime;
				System.out.println("Timp: " + (double)Math.round(totalTime / 1000.0 * 100d) / 100d + "s");
				System.out.println();
			}

			System.out.println("[KNN]Media dupa 5 iteratii: " + (double)Math.round(finalSum / 5.0 * 1000d) / 1000d);
			System.out.println();
			
			
			//Random Forest
			finalSum = 0.0;
		 
			for (int i=1; i<=5; i++){

				startTime = System.currentTimeMillis();
				
				data = FileHandler.loadDataset(new File("datasetWithoutNull.csv"), 32, ",");
				Classifier randomForest = new RandomForest(100);
				cv = new CrossValidation(randomForest);	
				p = cv.crossValidation(data, 5, new Random(i));

				endTime = System.currentTimeMillis();

				sum = 0.0;
				System.out.println("Random Forest");
				for(Object o:p.keySet())
				    {
				    	if (!Double.isNaN(p.get(o).getAccuracy())) {
					    	sum += p.get(o).getAccuracy();
				    		System.out.println(o+": "+p.get(o).getAccuracy());
				    	}
				    	else
				    		System.out.println(o+": 0.0");

				    }
				finalSum += (sum / 7.0);
				System.out.println("Media: " + (double)Math.round(sum / 7.0 * 1000d) / 1000d);
				totalTime = endTime - startTime;
				System.out.println("Timp: " + (double)Math.round(totalTime / 1000.0 * 100d) / 100d + "s");
				System.out.println();
			}
			
			System.out.println("[RF]Media dupa 5 iteratii: " + (double)Math.round(finalSum / 5.0 * 1000d) / 1000d);
			System.out.println();
		
			
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
