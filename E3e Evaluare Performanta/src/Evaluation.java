import data.Interval;
import data.TextGridFile;
import java.util.List;

/**
 * Created by ioana.chircu on 15.12.2015.
 */
public class Evaluation {

    public static boolean evaluateFile(TextGridFile manual, TextGridFile automatic){

        if(manual.getIntervals().size() != automatic.getIntervals().size())
            return false;

        for(int i = 0; i < manual.getIntervals().size(); i++) {

            Interval intervalManual = manual.getIntervals().get(i);
            Interval intervalAutomatic = manual.getIntervals().get(i);
            String typeManual = manual.getIntervals().get(i).getType();
            String typeAutomatic = automatic.getIntervals().get(i).getType();

            double diffLower = intervalManual.getMin() - intervalAutomatic.getMin();
            double diffUpper = intervalManual.getMax() - intervalAutomatic.getMax();

            if(!typeAutomatic.equals(typeManual))
                return false;
            if(Math.abs(diffLower) > 0.02 )
                return false;
            if(Math.abs(diffUpper) > 0.02 )
                return false;
        }
        return true;
    }

    public int evaluateIntervals(TextGridFile manual, TextGridFile automatic){

        int noCorrectIntervals = 0;
        int size;
        if(manual.getIntervals().size() < automatic.getIntervals().size())
            size = manual.getIntervals().size();
        else
            size = automatic.getIntervals().size();

        for(int i = 0; i < size; i++) {

            Interval intervalManual = manual.getIntervals().get(i);
            Interval intervalAutomatic = manual.getIntervals().get(i);
            String typeManual = manual.getIntervals().get(i).getType();
            String typeAutomatic = automatic.getIntervals().get(i).getType();

            double diffLower = intervalManual.getMin() - intervalAutomatic.getMin();
            double diffUpper = intervalManual.getMax() - intervalAutomatic.getMax();

            if(typeAutomatic.equals(typeManual) && Math.abs(diffLower) < 0.02 && Math.abs(diffUpper) < 0.02)
                noCorrectIntervals ++;
        }
        System.out.println("correct = " + noCorrectIntervals + " from " + automatic.getIntervals().size());
        return noCorrectIntervals;
    }

    public double filesAdnotatedCorrectly(List<TextGridFile> manual, List<TextGridFile> automatic) {

        double correctAdnotated = 0;
        for(int i = 0; i < automatic.size(); i++) {

            if(evaluateFile(manual.get(i), automatic.get(i)))
                correctAdnotated ++;
        }

        return correctAdnotated/manual.size()*100;
    }

    public double intervalsAdnotatedCorrectly(List<TextGridFile> manual, List<TextGridFile> automatic) {

        int noCorrectIntervals = 0;
        int noTotalIntervals = 0;
        for(int i = 0; i < automatic.size(); i++) {

            noCorrectIntervals += evaluateIntervals(manual.get(i) ,automatic.get(i));
            if(manual.get(i).getIntervals().size() > automatic.get(i).getIntervals().size())
                noTotalIntervals += manual.get(i).getIntervals().size();
            else
                noTotalIntervals += automatic.get(i).getIntervals().size();
        }

        return (double)noCorrectIntervals/noTotalIntervals*100;
    }
}
