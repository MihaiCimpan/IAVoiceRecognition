import java.util.*;

public class Comparator {

	private double PerfectMatch;
	private double SegmentError;
	private double PraatError;

	public Comparator(){
		this.PerfectMatch=0.0; // Praatul si Segmentatorul identifica corect zona vocalica
		this.SegmentError=0.0; // Praatul gaseste zona vocalica segmentatorul nu
		this.PraatError=0.0;   // Praatul nu gaseste zona vocalica dar segmentatorul da
	}

	public double getPerfectMatch() {
		return PerfectMatch;
	}

	public double getSegmentError() {
		return SegmentError;
	}

	public double getPraatError() {
		return PraatError;
	}

	private void AddTimeMatched(Interval praat, Interval seg){
		if(praat.isVocala()){
			if(!seg.isVocala()){
				this.SegmentError+=seg.getEnd()-seg.getBegin();
			}
			else{
				this.PerfectMatch+=seg.getEnd()-seg.getBegin();
			}
		}
		else
		if(seg.isVocala()){
			this.PraatError+=seg.getEnd()-seg.getBegin();
		}


	}

	public ArrayList<Double> Compare(ArrayList<Interval> praat,ArrayList<Interval> segmentator){
		ArrayList<Double> procents = new ArrayList<Double>();
		Double endFirstTime;
		Interval first,second;
		int i=0,j=0;
		boolean out = false;
		first=praat.get(i);
		second=segmentator.get(j);

		if(praat.get(praat.size()-1).getEnd()<=segmentator.get(segmentator.size()-1).getEnd())
			endFirstTime= praat.get(praat.size()-1).getEnd();
		else
			endFirstTime= segmentator.get(segmentator.size()-1).getEnd();

		if(first.getBegin() > second.getBegin()){
			AddTimeMatched(new Interval(second.getBegin(), first.getBegin(),false), new Interval(second.getBegin(), first.getBegin(),second.isVocala()));
			second.setBegin(first.getBegin());
		}
		else{
			if(first.getBegin() < second.getBegin()){
				AddTimeMatched(new Interval(first.getBegin(), second.getBegin(),false), new Interval(first.getBegin(), second.getBegin(),first.isVocala()));
				first.setBegin(second.getBegin());
			}
		}

		while(((first.getEnd()!=endFirstTime)||(second.getEnd()!=endFirstTime))&& !out){

			if(first.getEnd() == second.getEnd()){
				AddTimeMatched(first,second);
				i++;j++;
				first=praat.get(i);
				second=segmentator.get(j);
			}
			else if(first.getEnd() < second.getEnd()){

				AddTimeMatched(first,new Interval(second.getBegin(),first.getEnd(),second.isVocala()));
				i++;
				second.setBegin(first.getEnd());
				if(i<praat.size()-1)
				{
					first=praat.get(i);
				}
				else
					out=true;

			}
			else if(first.getEnd() > second.getEnd()){
				AddTimeMatched(new Interval(first.getBegin(),second.getEnd(),first.isVocala()),second);
				j++;
				first.setBegin(second.getEnd());
				if(j<segmentator.size()-1){

					second=segmentator.get(j);
				}
				else
					out=true;
			}
		}


		while(j<segmentator.size()-1){
			j++;
			second=segmentator.get(j);
			AddTimeMatched(new Interval(second.getBegin(),second.getEnd(),false),second);
		}

		while(i<praat.size()-1){
			i++;
			first=praat.get(i);
			AddTimeMatched(first,new Interval(first.getBegin(),first.getEnd(),false));
		}

		Double sum= this.PerfectMatch+this.PraatError+this.SegmentError;
		procents.add(this.PerfectMatch/sum);
		procents.add(this.SegmentError/sum);
		procents.add(this.PraatError/sum);

		return procents;

	}
}