/**
 * Created by Adyzds on 11/13/2015.
 */

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc;
        PrintWriter writer = null;
        final float eta = 0.01F;
        float out = 0;
        float eroare, eg = 0;
        List<Float> x = new ArrayList<>();
        List<Float> fereastra = new ArrayList<>();
        List<Float> w = new ArrayList<>();
        List<Float> erori = new ArrayList<>();

        try {
            writer = new PrintWriter("out.txt", "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            sc = new Scanner(new File("Valori.txt"));
            while (sc.hasNext()) {
                x.add(sc.nextFloat());
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
                System.out.print(fereastra);
                out = 0;
                for (int j = 0; j < 5; j++) {

                    out += fereastra.get(j) * w.get(j);

                }
//                if (count == 8)
//                    writer.println(out);
                System.out.println(x.get(i+5));
                eroare = out - x.get(i+5) ;
                erori.add(eroare);
//                System.out.println(" -> " + eroare);

                for (int j = 0; j < 5; j++) {
                    w.set(j, w.get(j) - eta * fereastra.get(j) * eroare);
                }

            }
            System.out.println("\n");

            System.out.println(w);

            eg = 0;
            for (int i = 0; i < erori.size() - 5; i += 5) {
                eg += Math.abs(erori.get(i));
            }

            System.out.println("Eroarea globala: " + eg);
            count++;
            erori = new ArrayList<>();
        }


        for (int i = 0; i < x.size() - 5; i++) {
            fereastra = x.subList(i, i + 5);
//                System.out.print(fer);
            out = 0;
            for (int j = 0; j < 5; j++) {

                out += fereastra.get(j) * w.get(j);

            }
            writer.println(out);

        }
        writer.close();
    }

    public static ArrayList<Float> initializeWeights(){
        ArrayList<Float> w = new ArrayList<>();
        final float minX = -1;
        final  float maxX = 1;
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            float finalX = rand.nextFloat() * (maxX - minX) + minX;
            w.add(finalX);

        }
        return w;
    }

}