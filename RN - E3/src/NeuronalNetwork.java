import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Adyzds on 12/15/2015.
 */
public class NeuronalNetwork {
    private int precision = 80;
    public NeuronalNetwork() {

    }
    public void setPrecision(int precision){
        this.precision = precision;
    }
    public List<Double> getGlobalErrors(String fileName){

        Scanner sc;
        PrintWriter writer = null;
        final double eta = 0.01d;
        double out = 0;
        double eroare, eg = 0;
        final int dimFereseastra = 800;
        List<Double> x = new ArrayList<>();
        List<Double> fereastra = new ArrayList<>();
        List<Double> w = new ArrayList<>();
        List<Double> erori = new ArrayList<>();
        List<Double> eroriGlobale = new ArrayList<>();
        Integer results[ ] = {new Integer(3), new Integer(5), new Integer(8)};


        try {
            writer = new PrintWriter("out.txt", "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            sc = new Scanner(new File("Valori.txt"));
            while (sc.hasNext()) {
                x.add(sc.nextDouble());
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("Eroare la citirea labirintului din fisier!");
        }


        w = initializeWeights();

        int count = 0;
        while (count < 50) {
            for (int i = 0; i < x.size() - 5; i++) {
                fereastra = x.subList(i, i + 5);
                out = 0;
                for (int j = 0; j < 5; j++) {
//                    System.out.println(w.get(i) + " " + fereastra.get(j) * w.get(j) );
                    out += fereastra.get(j) * w.get(j);

                }
                eroare = out - x.get(i+5) ;
                if(count == 49){
                    writer.println(out);
                    erori.add(eroare);
                }

                for (int j = 0; j < 5; j++) {
                    w.set(j, w.get(j) - eta * fereastra.get(j) * eroare);
                }

            }

            count++;

        }

        double eroareGlobala = 0;
        for (int i = 0; i < erori.size() - precision; i++) {
            if(i%precision == 0 && i != 0){
                eroriGlobale.add(eroareGlobala);
                eroareGlobala = 0;
            }
            else
                eroareGlobala += Math.abs(erori.get(i));


        }



        writer.close();
        PrintWriter writer2 = null;
        try {
            writer2 = new PrintWriter("out2.txt", "UTF-8");
            for (int i = 0; i < erori.size(); i++) {
                writer2.println(erori.get(i));
//                System.out.println(erori.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        finally {
            writer2.close();
        }

        return eroriGlobale;
    }

    public static ArrayList<Double> initializeWeights(){
        ArrayList<Double> w = new ArrayList<>();
        final float minX = -1;
        final  float maxX = 1;
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            double finalX = rand.nextDouble() * (maxX - minX) + minX;
            w.add(finalX);

        }
        return w;
    }
}
