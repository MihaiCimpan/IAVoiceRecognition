public class Interval {
    private double begin;
    private double end;
    private boolean isVocala;

    public Interval() {
    }

    public Interval(double begin, double end, boolean isVocala) {
        this.begin = begin;
        this.end = end;
        this.isVocala = isVocala;
    }

    public double getBegin() {
        return this.begin;
    }

    public void setBegin(double begin) {
        this.begin = begin;
    }

    public double getEnd() {
        return this.end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public boolean isVocala() {
        return this.isVocala;
    }
    
    public void setVocala(boolean isVocala) {
        this.isVocala = isVocala;
    }
}
