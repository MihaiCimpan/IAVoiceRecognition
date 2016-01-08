
public class Main {
    public static void main(String [] args) {  

    	int numberOfItererations = 10;
    	double accuracySum = 0;
    	double[] accuracyVector = new double[numberOfItererations];
    	
    	for (int i = 0; i < numberOfItererations; i++)
    	{
    		double currentAccuracy = getAccuracy();
    		accuracySum += currentAccuracy;
    		accuracyVector[i] = currentAccuracy;
    	}

    	for (int i = 0; i < numberOfItererations; i++)
    	{
    		System.out.println("Current accuracy: " + accuracyVector[i]);
    	}
    	
    	System.out.println("");
    	System.out.println("Final accuracy: " + Math.round((accuracySum / numberOfItererations) * 1000 + 0.5) / 1000.0);
    	
    }
    
    public static double getAccuracy() {
		SVM s = new SVM();
        
        Problem train = new Problem();
        Problem test = new Problem();

        train.loadBinaryProblem("train.txt");
        test.loadBinaryProblem("test.txt");
        
        System.out.println("Loaded.");
        System.out.println("Training...");

        s.svmTrain(train);
        System.out.println("Testing...");
        int [] pred = s.svmTest(test);
        for (int i=0; i<pred.length; i++)
                System.out.println(pred[i]);
        
        EvalMeasures e = new EvalMeasures(test, pred, 2);
        System.out.println("Done.");
        
        return e.Accuracy();
    }
    
}