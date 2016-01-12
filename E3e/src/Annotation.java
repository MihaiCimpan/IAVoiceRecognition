import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;

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

    public double compareTo1(Annotation other) {
        double correct=0, total=0;

        for (int p1=0, p2=0; p1<intervals.size() && p2<other.intervals.size(); ) {
            Interval i1 = intervals.get(p1);
            Interval i2 = other.intervals.get(p2);

            if (i1.getType().equals("v")) {
                double l1, l2;
                double r1, r2;

                r1 = intervals.get(p1).getIntervalEnd();
                if (p1==0) l1 = 0;
                else l1 = intervals.get(p1-1).getIntervalEnd();

                r2 = other.intervals.get(p2).getIntervalEnd();
                if (p2==0) l2 = 0;
                else l2 = other.intervals.get(p2-1).getIntervalEnd();

                if (i2.getType().equals("v")) {
                    correct += Math.min(r1, r2) - Math.max(l1, l2);
                }

            }

            if (i1.getIntervalEnd() < i2.getIntervalEnd()) {
                if (i1.getType().equals("v")) {
                    double l1;

                    if (p1==0) l1 = 0;
                    else l1 = intervals.get(p1-1).getIntervalEnd();

                    total += i1.getIntervalEnd() - l1;
                }
                ++p1;
            }
            else {
                ++p2;
            }
        }

        return correct / total;
    }


    public double compareTo2(Annotation other) {
        double incorrect=0, total = 0;

        for (int p1=0, p2=0; p1<intervals.size() && p2<other.intervals.size(); ) {
            Interval i1 = intervals.get(p1);
            Interval i2 = other.intervals.get(p2);

            if (i1.getType().equals("v") != i2.getType().equals("v")) {
                double l, l1, l2;
                double r, r1, r2;

                r1 = intervals.get(p1).getIntervalEnd();
                if (p1==0) l1 = 0;
                else l1 = intervals.get(p1-1).getIntervalEnd();

                r2 = other.intervals.get(p2).getIntervalEnd();
                if (p2==0) l2 = 0;
                else l2 = other.intervals.get(p2-1).getIntervalEnd();

                l = Math.max(Math.min(r1, l2), Math.min(l1, r2));
                r = Math.min(Math.max(r1, l2), Math.max(l1, r2));

                incorrect += r-l;
            }

            if (i1.getIntervalEnd() < i2.getIntervalEnd()) {
                if (i1.getType().equals("v")) {
                    double l1;

                    if (p1==0) l1 = 0;
                    else l1 = intervals.get(p1-1).getIntervalEnd();

                    total += i1.getIntervalEnd() - l1;
                }
                ++p1;
            }
            else {
                ++p2;
            }
        }

        return incorrect / total;
    }
}
