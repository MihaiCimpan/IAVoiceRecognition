import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
       
	public static void main (String args[]) throws IOException {
                List<Point> inputSet = new ArrayList<>();
                List<Point> trainingSet = new ArrayList<Point>();
                ReadFromFile r = new ReadFromFile();  
                r.readFile();
                inputSet = r.getInputSet();
                trainingSet = r.getTrainingSet();
                Knn knn = new Knn(3);
                int result = 0;
                System.out.println(trainingSet.size());
                System.out.println(inputSet.size());
                for(int j=0; j<inputSet.size();j++) {
                    inputSet.get(j).displayPoint();
                   if( knn.findClass(inputSet.get(j), trainingSet).equals(inputSet.get(j).getClassName()))
                		   result ++;
//                    System.out.println(j + " --- > ");
                }      
                
                System.out.println(result + " done");

                
	}
}