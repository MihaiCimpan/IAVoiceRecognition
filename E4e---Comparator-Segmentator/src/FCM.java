import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FCM {

	static List<Double> globalErrors = new ArrayList<Double>();
	static List<Double> gErrors = new ArrayList<Double>();
	static Double[] centers = new Double[2];
	static Double[][] matrix;
	static Double threshold;
	static final Double m = 2.0;
	static final int c = 2;
	static final Double epsilon = 0.01;

	FCM(List<Double> g) {
		globalErrors = g;
		for(Double err : globalErrors)
			if(err < 1.00) gErrors.add(err);
		matrix = new Double[gErrors.size()][c];
	}

	public ArrayList<Interval> convert() {
		int tmp;
		algorithm();
		threshold = getThreshold();
		ArrayList<Interval> intervalList = new ArrayList<Interval>();
		Double a;
		Double b;
		double secInit = 0.0;
		double secFin;
		int i,ok = 0;
		for(i=1;i<globalErrors.size();i++) {
			a = globalErrors.get(i-1);
			b = globalErrors.get(i);
			if((a<threshold && b>threshold) || (a>threshold && b<threshold)) {
				int x = i/500;
				int y = i%500;
				double poz = (double) y * 1600 / 500;
				secFin = (double) poz / 1600;
				secFin = secFin + x;
				Interval interval;
				if(a<threshold && b>threshold)
					interval = new Interval(secInit,secFin,true);
				else
					interval = new Interval(secInit,secFin,false);

				intervalList.add(interval);
				secInit = secFin;
				tmp = i;
			}
		}
		int x = ( i - 1 ) / 500;
		int y = ( i - 1 ) % 500;
		double poz = (double) y * 1600 / 500;
		double fin = (double) poz / 1600;
		fin = fin + x;
		if(globalErrors.get(i-1) > threshold) {
			Interval interval = new Interval(secInit,fin,false);
			intervalList.add(interval);
		}
		else {
			Interval interval = new Interval(secInit,fin,true);
			intervalList.add(interval);
		}
		return intervalList;
	}

	public void algorithm() {
		centers[0] = getMinError();
		centers[1] = getMaxError();
		int ok = 0;
		while (ok==0) {
			Double d1 = getDistance(centers[0], centers[1]);
			setMatrixElem();
			computeFuzzyCenters();
			Double d2 = getDistance(centers[0], centers[1]);
			if(d2>=d1 || Math.abs(d2-d1)<0.001) ok=1;
		}
	}

	public void setMatrixElem() {
		for(int i=0;i<gErrors.size();i++) {
			for(int j=0;j<c;j++) {
				Double sum = 0.0;
				Double d1 = getDistance(gErrors.get(i),centers[j]);
				Double d2;
				for(int k=0;k<c;k++) {
					d2 = getDistance(gErrors.get(i), centers[k]);
					sum = sum + d2;
				}
				matrix[i][j] = Math.pow((d1/sum),(2.0/(1-m)));
				if(gErrors.get(i) == centers[0]) {
					matrix[i][1-j] = 1.00;
					matrix[i][j] = 0.00;
				}
				if(gErrors.get(i) == centers[1]) {
					matrix[i][j] = 1.00;
					matrix[i][1-j] = 0.00;
				}
			}
		}
		for(int i=0;i<gErrors.size();i++) {
			Double sum = 0.0;
			for(int j=0;j<c;j++) {
				sum = sum + matrix[i][j];
			}
			for(int j=0;j<c;j++) {
				matrix[i][j] = matrix[i][j]/sum;
			}
		}
	}

	public void computeFuzzyCenters() {
		for(int j=0;j<c;j++) {
			Double sum1 = 0.0;
			Double sum2 = 0.0;
			for(int i=0;i<gErrors.size();i++) {
				sum1 = sum1 + (Math.pow(matrix[i][j], m) * gErrors.get(i));
				sum2 = sum2 + Math.pow(matrix[i][j], m);
			}
			centers[j] = sum1/sum2;
		}
	}

	public Double getThreshold() {
		List<Double> class1 = new ArrayList<Double>();
		List<Double> class2 = new ArrayList<Double>();
		for(int i=0;i<gErrors.size();i++) {
			if(matrix[i][0] > matrix[i][1] && gErrors.get(i) > 0.5) class1.add(gErrors.get(i));
			else if(matrix[i][0] < matrix[i][1]) class2.add(gErrors.get(i));
		}
		Double dev1 = getStDev(class1);
		Double dev2 = getStDev(class2);
		Double coef = getCoef(centers[0], centers[1], dev1, dev2);
		return centers[1] - dev1 * coef;
	}

	public Double getCoef(Double c1, Double c2, Double std1, Double std2){
		return (Math.abs(c1 - c2))/(std1+std2);
	}

	public Double getStDev(List<Double> arr) {
		return Math.sqrt(getVariance(arr));
	}

	public Double getVariance(List<Double> arr) {
		Double mean = getMean(arr);
		Double temp = 0.0;
		for(double a : arr)
			temp += (mean-a)*(mean-a);
		return temp/arr.size();
	}

	public Double getMean(List<Double> arr) {
		Double sum = 0.0;
		for(Double x : arr)
			sum = sum + x;
		Double mean = sum/arr.size();
		return mean;
	}

	public Double getDistance(Double x, Double y) {
		return Math.abs(x - y);
	}

	public Double getMinError() {
		Double min = gErrors.get(0);
		for(int i=0;i<gErrors.size();i++)
			if(gErrors.get(i)<min)
				min = gErrors.get(i);
		return min;
	}

	public Double getMaxError() {
		Double max = gErrors.get(0);
		for(int i=0;i<gErrors.size();i++)
			if(gErrors.get(i)>max)
				max = gErrors.get(i);
		return max;
	}

	public void reinitializeMatrix() {
		for(int i=0;i<gErrors.size();i++)
			for(int j=0;j<c;j++)
				matrix[i][j] = 0.0;
	}

	public Double truncate(Double x) {
		int aux = (int)(x*100000);
		x = aux/100000d;
		return x;
	}

	public void writeInFile() {
		PrintWriter writer2 = null;
		try {
			writer2 = new PrintWriter("out5.txt", "UTF-8");
			for (int i = 0; i < gErrors.size(); i++) {
				writer2.println(gErrors.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		writer2.close();
	}
}