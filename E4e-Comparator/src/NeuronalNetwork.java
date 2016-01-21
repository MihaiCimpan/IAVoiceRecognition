import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class NeuronalNetwork {
    private int swinSize;
    private int wStep;
    private int nInputs;
    final double eta = 0.1d;
    double out = 0;
    double eroare, eg = 0;
    List<Double> values;
    List<Double> fereastra = new ArrayList<>();
    List<Double> swin = new ArrayList<>();
    List<Double> inputs = new ArrayList<>();
    List<Double> outputs = new ArrayList<>();
    List<Double> outputs2 = new ArrayList<>();
    List<Double> weigths = new ArrayList<>();
    List<Double> erori = new ArrayList<>();
    List<Double> eroriGlobale = new ArrayList<>();
    PrintWriter writer = null;
    private int precision = 9;

    public NeuronalNetwork() {
        swinSize = 256;
        wStep = swinSize / 8;
        nInputs = 5;

        weigths = initializeWeights();

        try {
            writer = new PrintWriter("out.txt", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Double> getGlobalErrors(String fileName) {

        Scanner sc;
        int iterationsNumber;
        int valuesLength;
        int j = 1;
        int y2;
        int traningTurns;
        double globalError;

        values = new ArrayList<>();
        try
        {
            sc = new Scanner(new File(fileName));
            while (sc.hasNext())
            {
                values.add(Double.parseDouble(sc.next()));
            }
            sc.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        valuesLength = values.size();
        // se sar primele esantioane daca acestea sunt aproximativ egale cu zero

        if (valuesLength < swinSize) {
            System.out.println("Semnalul este prea mic pentru a fi analizat");
            return null;
        }

        iterationsNumber = (valuesLength - swinSize + 1) / wStep;

        weigths = initializeWeights();

        for (int i = 0; i < iterationsNumber * wStep - 1; i += wStep) {
            swin = values.subList(i, i + swinSize);
            if(j <= 3) traningTurns = 200;
            else traningTurns = 1;

            for (int k = 0; k < traningTurns; k++) {
                for (int y = 0; y < swinSize - 5; y++) {
                    y2 = y;
                    inputs = swin.subList(y2, y2 + 5);

                    out = 0;
                    for (int z = 0; z < 5; z++) {
                        out += inputs.get(z) * weigths.get(z);
                    }
                    eroare = out - swin.get(y2 + 5);

                    for (int z = 0; z < 5; z++) {
                        weigths.set(z, weigths.get(z) - eta * inputs.get(z) * eroare);
                    }
                }

                if ((k == 0 && j > 3) || (k == 199)) {
                    outputs = new ArrayList<>();
                    for (int y = 5; y < swinSize - 5; y++) {
                        inputs = swin.subList(y, y + 5);

                        out = 0;
                        for (int z = 0; z < 5; z++) {
                            out += inputs.get(z) * weigths.get(z);
                        }

                        outputs.add(out);

                    }
                    double sum = 0, sum2 = 0;
                    for (int y = 5; y < swinSize - 5; y++) {
                        sum2 += swin.get(y)* swin.get(y);
                        sum += (outputs.get(y-5) - swin.get(y)) * (outputs.get(y-5) - swin.get(y)) ;
                    }
                    eroriGlobale.add(Math.sqrt(sum)/sum2);
                }
            }
            j++;
        }

        //median filter
        for (int i = 0; i < eroriGlobale.size() - precision; i++) {

//            inputs  = eroriGlobale.subList(i, i + precision);
            //System.out.println(eroriGlobale.subList(i, i + precision));
            inputs = sortArray(eroriGlobale.subList(i, i + precision));

            eroriGlobale.set(precision / 2 + i, inputs.get(precision / 2 ));
        }
//         average filter
        for (int i = 0; i < eroriGlobale.size() - precision; i++) {
            inputs = eroriGlobale.subList(i, i + precision);
//                     System.out.println(eroriGlobale.subList(i, i + precision));

            double sum = 0;
            for (double err :inputs)
                sum += err;
            eroriGlobale.set(precision / 2  + i, sum/precision);
        }



        writer.close();
        PrintWriter writer2 = null;
        try {
            writer2 = new PrintWriter("out2.txt", "UTF-8");
            for (int i = 0; i < eroriGlobale.size(); i++) {
                writer2.println(eroriGlobale.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        finally {
            writer2.close();
        }
//
        return eroriGlobale;
//        return null;
    }
    
    
    //functie adaugata pentru citirea direct din fisier .wav E4e
    public List<Double> getGlobalErrors(List<Double> inputValues) {

        Scanner sc;
        int iterationsNumber;
        int valuesLength;
        int j = 1;
        int y2;
        int traningTurns;
        double globalError;
        values = inputValues;

        valuesLength = values.size();
        // se sar primele esantioane daca acestea sunt aproximativ egale cu zero

        if (valuesLength < swinSize) {
            System.out.println("Semnalul este prea mic pentru a fi analizat");
            return null;
        }

        iterationsNumber = (valuesLength - swinSize + 1) / wStep;

        weigths = initializeWeights();

        for (int i = 0; i < iterationsNumber * wStep - 1; i += wStep) {
            swin = values.subList(i, i + swinSize);
            if(j <= 3) traningTurns = 200;
            else traningTurns = 1;

            for (int k = 0; k < traningTurns; k++) {
                for (int y = 0; y < swinSize - 5; y++) {
                    y2 = y;
                    inputs = swin.subList(y2, y2 + 5);

                    out = 0;
                    for (int z = 0; z < 5; z++) {
                        out += inputs.get(z) * weigths.get(z);
                    }
                    eroare = out - swin.get(y2 + 5);

                    for (int z = 0; z < 5; z++) {
                        weigths.set(z, weigths.get(z) - eta * inputs.get(z) * eroare);
                    }
                }

                if ((k == 0 && j > 3) || (k == 199)) {
                    outputs = new ArrayList<>();
                    for (int y = 5; y < swinSize - 5; y++) {
                        inputs = swin.subList(y, y + 5);

                        out = 0;
                        for (int z = 0; z < 5; z++) {
                            out += inputs.get(z) * weigths.get(z);
                        }

                        outputs.add(out);

                    }
                    double sum = 0, sum2 = 0;
                    for (int y = 5; y < swinSize - 5; y++) {
                        sum2 += swin.get(y)* swin.get(y);
                        sum += (outputs.get(y-5) - swin.get(y)) * (outputs.get(y-5) - swin.get(y)) ;
                    }
                    eroriGlobale.add(Math.sqrt(sum)/sum2);
                }
            }
            j++;
        }

        //median filter
        for (int i = 0; i < eroriGlobale.size() - precision; i++) {

//            inputs  = eroriGlobale.subList(i, i + precision);
            //System.out.println(eroriGlobale.subList(i, i + precision));
            inputs = sortArray(eroriGlobale.subList(i, i + precision));

            eroriGlobale.set(precision / 2 + i, inputs.get(precision / 2 ));
        }
//         average filter
        for (int i = 0; i < eroriGlobale.size() - precision; i++) {
            inputs = eroriGlobale.subList(i, i + precision);
//                     System.out.println(eroriGlobale.subList(i, i + precision));

            double sum = 0;
            for (double err :inputs)
                sum += err;
            eroriGlobale.set(precision / 2  + i, sum/precision);
        }



        writer.close();
        PrintWriter writer2 = null;
        try {
            writer2 = new PrintWriter("out2.txt", "UTF-8");
            for (int i = 0; i < eroriGlobale.size(); i++) {
                writer2.println(eroriGlobale.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        finally {
            writer2.close();
        }
//
        return eroriGlobale;
//        return null;
    }

    private List<Double> sortArray(List<Double> inputs) {
        List<Double> array = arrayCopy(inputs);
        Collections.sort(array);
        return array;
    }

    private List<Double> arrayCopy(List<Double> doubles) {
        ArrayList<Double>copy = new ArrayList<>();
        for (double d: doubles)
            copy.add(d);
        return copy;
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


