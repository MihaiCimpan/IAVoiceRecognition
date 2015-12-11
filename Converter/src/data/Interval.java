package data;

public class Interval {
	
	private double min;
	private double max;
	private String type;
	
	public Interval(double min, double max, String type) {
		this.min = min;
		this.max = max;
		this.type = type;
	}
	
	public double getMin() {
		return this.min;
	}
	
	public double getMax() {
		return this.max;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return "[" + this.min + " " + this.max + " " + this.type + "]";
	}
	
}
