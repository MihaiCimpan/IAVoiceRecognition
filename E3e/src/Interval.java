/**
 * Created by nicolae.berendea on 12/25/2015.
 */
public class Interval {
    private double intervalEnd;
    private String type;

    public Interval(double intervalEnd, String type) {
        this.intervalEnd = intervalEnd;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double getIntervalEnd() {
        return intervalEnd;
    }
}
