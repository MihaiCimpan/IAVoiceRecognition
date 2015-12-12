class Interval {

    private String text;
    private double min, max;

    public Interval() {

    }

    public Interval(String text, double min, double max) {
        this.text = text;
        this.min = min;
        this.max = max;
    }

    public String getText() {
        return text;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public String toString() {
        String string = "text: " + text + " min: " + min + " max: " + max;
        return string;
    }
}
