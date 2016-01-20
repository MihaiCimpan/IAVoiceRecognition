import java.util.HashMap;

/**
 * Created by HB on 20.01.2016.
 */
public class Instance {

    private HashMap<String, Double> values = new HashMap<>();

    public HashMap<String, Double> getValues() {
        return values;
    }

    public void setValues(HashMap<String, Double> values) {
        this.values = values;
    }

    private String emotion;

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
