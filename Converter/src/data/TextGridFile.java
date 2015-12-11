package data;

import java.util.List;

public class TextGridFile {
	private String name;
	private List<Interval> intervals;
	
	public TextGridFile(String name, List<Interval> intervals) {
		this.name = name;
		this.intervals = intervals;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Interval> getIntervals() {
		return this.intervals;
	}
	
	@Override
	public String toString() {
		return this.name + this.intervals;
	}
}
