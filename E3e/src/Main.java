import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nicolae.berendea on 12/25/2015.
 */


public class Main {
    private static final String textGridPatternKeys[] = {"item", "name", "interval"};
    private static final String textGridPatterns[] = {"item \\[(\\d+)\\]:", "name = \"(.+)\"", "intervals \\[(\\d+)\\]:"};

    public static Annotation loadFromTextGrid(String filePath, String itemName) throws FileNotFoundException {
        BufferedReader reader = null;
        Annotation result = null;

        try {
            HashMap<String, Pattern> patterns = new HashMap<>();
            Matcher matcher;
            boolean inItem = false;

            reader = new BufferedReader(new FileReader(new File(filePath)));
            for (int i=0; i<textGridPatternKeys.length; ++i) {
                patterns.put(textGridPatternKeys[i], Pattern.compile(textGridPatterns[i]));
            }
            result = new Annotation();

            for (String line; ((line=reader.readLine()) != null);) {
                line = line.trim();

                matcher = patterns.get("item").matcher(line);
                if (matcher.matches()) {
                    inItem = true;
                    continue;
                }

                if (!inItem)
                    continue;

                matcher = patterns.get("name").matcher(line);
                if (matcher.matches()) {
                    if (!matcher.group(1).equals(itemName))
                        inItem = false;
                    continue;
                }

                matcher = patterns.get("interval").matcher(line);
                if (matcher.matches()) {
                    double xmin, xmax;
                    String type;

                    line = reader.readLine().trim();
                    xmin = new Double(line.substring(7));

                    line = reader.readLine().trim();
                    xmax = new Double(line.substring(7));

                    line = reader.readLine().trim();
                    type = line.substring(8, line.lastIndexOf("\""));
                    result.addInterval(new Interval(xmax, type));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        result.addInterval(new Interval(1000, "c'"));

        return result;
    }

    public static Annotation loadFromTextFile(String filePath) throws FileNotFoundException {
        BufferedReader reader = null;
        Annotation result = null;

        try {
            Pattern pattern = Pattern.compile("[A-Za-z0-9\\.]+\\s*([0-9\\.]+)\\s*([A-Za-z0-9\\.\\-]+)");
            Matcher matcher;
            boolean vocalic = false;

            reader = new BufferedReader(new FileReader(new File(filePath)));
            result = new Annotation();

            for (String line=null; ((line=reader.readLine()) != null);) {
                line = line.trim();

                if (line.equals("Filename\tTimestamp\tPitch (Hz)")) {
                    continue;
                }
                matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    if (matcher.group(2).equals("--undefined--")) {
                        if (vocalic) {
                            double xmax = new Double(matcher.group(1));
                            result.addInterval(new Interval(xmax, "v"));

                        }
                        vocalic = false;
                    }
                    else {
                        if (!vocalic) {
                            double xmax = new Double(matcher.group(1));
                            result.addInterval(new Interval(xmax, ""));
                        }
                        vocalic = true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        result.addInterval(new Interval(1000, "c'"));

        return result;
    }

    public static void main(String args[]) {
        try {
            Annotation manual = loadFromTextGrid("B527eFc.TextGrid", "cv");
            Annotation auto = loadFromTextFile("B527eFc.praat");

            System.out.println(manual.compareTo1(auto));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
