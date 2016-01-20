import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.*;
import java.util.HashMap;

/**
 * Created by HB on 20.01.2016.
 */
public class DecisionTreeClassifier {

    public static HashMap<String, Instance> data = new HashMap<>();

    public static void generateARFF() {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.arff"), "utf-8"));
            writer.write(
                    "@RELATION data\n\n" +
                            "@ATTRIBUTE f1_mean NUMERIC\n" +
                            "@ATTRIBUTE f2_mean NUMERIC\n" +
                            "@ATTRIBUTE f3_mean NUMERIC\n" +
                            "@ATTRIBUTE f4_mean NUMERIC\n" +
                            "@ATTRIBUTE f1_stdev NUMERIC\n" +
                            "@ATTRIBUTE f2_stdev NUMERIC\n" +
                            "@ATTRIBUTE f3_stdev NUMERIC\n" +
                            "@ATTRIBUTE f4_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_1_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_1_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_2_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_2_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_3_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_3_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_4_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_4_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_5_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_5_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_6_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_6_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_7_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_7_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_8_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_8_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_9_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_9_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_10_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_10_stdev NUMERIC\n" +
                            "@ATTRIBUTE mfcc_11_mean NUMERIC\n" +
                            "@ATTRIBUTE mfcc_11_stdev NUMERIC\n" +
                            "@ATTRIBUTE pitch_mean NUMERIC\n" +
                            "@ATTRIBUTE pitch_stdev NUMERIC\n" +
                            "@ATTRIBUTE emotion {N,J,S,F,A,B,D}\n\n" +
                            "@DATA\n");
            BufferedReader br1 = new BufferedReader(new FileReader("res_formants.txt"));
            readFormants(br1);
            BufferedReader br2 = new BufferedReader(new FileReader("res_mfcc.txt"));
            readFormants(br2);
            BufferedReader br3 = new BufferedReader(new FileReader("res_pitch.txt"));
            readFormants(br3);
            for (String key : data.keySet()) {
                Instance instance = data.get(key);
                try {
                    writer.write(instance.getValues().get("f1_mean").toString() + "," +
                            instance.getValues().get("f2_mean").toString() + "," +
                            instance.getValues().get("f3_mean").toString() + "," +
                            instance.getValues().get("f4_mean").toString() + "," +
                            instance.getValues().get("f1_stdev").toString() + "," +
                            instance.getValues().get("f2_stdev").toString() + "," +
                            instance.getValues().get("f3_stdev").toString() + "," +
                            instance.getValues().get("f4_stdev").toString() + "," +
                            instance.getValues().get("mfcc_1_mean").toString() + "," +
                            instance.getValues().get("mfcc_1_stdev").toString() + "," +
                            instance.getValues().get("mfcc_2_mean").toString() + "," +
                            instance.getValues().get("mfcc_2_stdev").toString() + "," +
                            instance.getValues().get("mfcc_3_mean").toString() + "," +
                            instance.getValues().get("mfcc_3_stdev").toString() + "," +
                            instance.getValues().get("mfcc_4_mean").toString() + "," +
                            instance.getValues().get("mfcc_4_stdev").toString() + "," +
                            instance.getValues().get("mfcc_5_mean").toString() + "," +
                            instance.getValues().get("mfcc_5_stdev").toString() + "," +
                            instance.getValues().get("mfcc_6_mean").toString() + "," +
                            instance.getValues().get("mfcc_6_stdev").toString() + "," +
                            instance.getValues().get("mfcc_7_mean").toString() + "," +
                            instance.getValues().get("mfcc_7_stdev").toString() + "," +
                            instance.getValues().get("mfcc_8_mean").toString() + "," +
                            instance.getValues().get("mfcc_8_stdev").toString() + "," +
                            instance.getValues().get("mfcc_9_mean").toString() + "," +
                            instance.getValues().get("mfcc_9_stdev").toString() + "," +
                            instance.getValues().get("mfcc_10_mean").toString() + "," +
                            instance.getValues().get("mfcc_10_stdev").toString() + "," +
                            instance.getValues().get("mfcc_11_mean").toString() + "," +
                            instance.getValues().get("mfcc_11_stdev").toString() + "," +
                            instance.getValues().get("pitch_mean").toString() + "," +
                            instance.getValues().get("pitch_stdev").toString() + "," +
                            instance.getEmotion().toUpperCase() + "\n");
                } catch (NullPointerException e) {
                    System.out.print(key);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }
    }

    public static void readFormants(BufferedReader br) throws IOException {
        try {
            String fileName;
            String attribute;
            Double value;

            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split(" ");
                fileName = parts[0];
                attribute = parts[1];
                value = Double.parseDouble(parts[2]);
                if (data.containsKey(fileName)) {
                    Instance instance = data.get(fileName);
                    instance.getValues().put(attribute, value);
                    data.put(fileName, instance);
                } else {
                    Instance instance = new Instance();
                    instance.setEmotion("" + fileName.charAt(5));
                    instance.getValues().put(attribute, value);
                    data.put(fileName, instance);
                }

                line = br.readLine();
            }

        } finally {
            br.close();
        }
    }

    public static void main(String[] args) {
        generateARFF();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("data.arff"));
            Instances data = new Instances(reader);
            reader.close();
            data.setClassIndex(data.numAttributes() - 1);

            J48 tree = new J48();
            tree.buildClassifier(data);
            System.out.println(tree);

            Classifier cls = new J48();
            cls.buildClassifier(data);
            Evaluation eval = new Evaluation(data);
            eval.evaluateModel(cls, data);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
