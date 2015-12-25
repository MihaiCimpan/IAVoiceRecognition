import java.util.LinkedList;

/**
 * Created by nicolae.berendea on 12/25/2015.
 */
public class Annotation {
    private LinkedList<Interval> intervals;

    public Annotation() {
        intervals = new LinkedList<>();
    }

    public void addInterval(Interval interval) {
        intervals.add(interval);
    }
}
