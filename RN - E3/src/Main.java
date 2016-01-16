import java.util.List;

/**
 * Created by Adyzds on 12/18/2015.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        NeuronalNetwork rn = new NeuronalNetwork();
        List<Double> globalErrors = rn.getGlobalErrors("Valori.txt");
//        for(Double d: globalErrors)
//            System.out.println(d);
    }
}
