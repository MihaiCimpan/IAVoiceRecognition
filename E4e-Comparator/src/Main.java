import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args)
    {
        // Task t[] = new Task[5];
        //NeuronalNetwork[] Network = new NeuronalNetwork[5];
        Comparator c = new Comparator();
        NeuronalNetwork neuronalNetwork = new NeuronalNetwork();
        FCM alg;
        List<Double> globalErrors,compList;
        ArrayList<Interval> list;
        File[] wavFiles = getFiles(".wav");
        Map<String, ArrayList<Interval>> intervals = Parse(getFile());
        for (File wavFile:wavFiles) {
            globalErrors = neuronalNetwork.getGlobalErrors(readFromWav(wavFile));
            alg = new FCM(globalErrors);
            list = alg.convert();
            try{
                //List<Interval> l = intervals.get(getFilename(wavFile));
                //for(Interval i:l) System.out.println(i.getBegin() + " " + i.getEnd() + " " + i.isVocala());
                compList = c.Compare(list, intervals.get(getFilename(wavFile)));
                System.out.println(wavFile.getName());
                for (Double d : compList) {
                    System.out.println(d);
                }
            }
            catch (Exception ex){
                System.out.println("Eroare la comparare" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public static File getFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select the file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal != JFileChooser.APPROVE_OPTION) return null;

        return new File(chooser.getSelectedFile().getAbsolutePath());
    }

    public static File[] getFiles(String extension){
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal != JFileChooser.APPROVE_OPTION) return null;

        File directory = new File(chooser.getSelectedFile().getAbsolutePath());
        if(directory == null) return null;

        return directory.listFiles(path -> { return path.getName().endsWith(extension); });
    }

    public static String getFilename(File file){
        String fileName = file.getName();
        if (fileName.indexOf(".") > 0)
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        return fileName;
    }

    public static ArrayList<Double> readFromWav(File file) {
        try{
            WavFile wavFile = WavFile.openWavFile(file);
            ArrayList<Double> values = new ArrayList<>();
            int numChannels = wavFile.getNumChannels();
            double[] buffer = new double[100 * numChannels];

            int framesRead;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            do
            {
                framesRead = wavFile.readFrames(buffer, 100);
                for (int s=0 ; s<framesRead * numChannels ; s++)
                {
                    if (buffer[s] > max) max = buffer[s];
                    if (buffer[s] < min) min = buffer[s];
                    values.add(buffer[s]);
                }
            }
            while (framesRead != 0);

            wavFile.close();

            return values;
        }
        catch(Exception ex)
        {
            System.out.print(ex.getMessage());
            return null;
        }
    }

    public static Map<String,ArrayList<Interval>> Parse(File file){
        Map<String,ArrayList<Interval>> list = new HashMap<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            String[] split;
            String lastFile = "";
            boolean cons = true;
            double beginv = 0.1;
            double lastv = 0;
            double beginc = 0.1;
            double lastc = 0;
            ArrayList<Interval> intervals = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                split = line.split("\\s+");
                if(split[0].startsWith("B")){
                    if(!lastFile.contentEquals(split[0]))
                    {
                        if(lastc != 0 || lastv != 0)
                            if(cons){
                                //intervals.add(new Interval(beginc,lastc,!cons));
                            }
                            else{
                                intervals.add(new Interval(beginv,lastv,!cons));
                            }
                        list.put(lastFile,intervals);
                        intervals = new ArrayList<>();
                        beginv = 0.01;
                        lastv = 0.01;
                        beginc = 0.01;
                        lastc = 0.01;
                        lastFile = split[0];
                    }
                    if(split[2].contentEquals("--undefined--"))
                    {
                        if(!cons) {
                            intervals.add(new Interval(beginv,lastv,!cons));
                            beginc = Double.parseDouble(split[1]);
                        }
                        lastc = Double.parseDouble(split[1]);
                        cons = true;
                        continue;
                    }
                    if(cons){
                        //intervals.add(new Interval(beginc,lastc,!cons));
                        beginv = Double.parseDouble(split[1]);
                    }
                    lastv = Double.parseDouble(split[1]);
                    cons = false;
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
