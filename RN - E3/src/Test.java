import java.util.List;

/**
 * Created by Adyzds on 12/15/2015.
 */
public class Test {
    public static void main(String[] args) {
        NeuronalNetwork rn = new NeuronalNetwork();

        rn.setPrecision(8);

        List<Double> globalErrors = rn.getGlobalErrors("Valori.txt");

        for (int i = 0; i < globalErrors.size(); i++) {
            System.out.println(globalErrors.get(i));
        }

    }
}
