
public class Point {

	public String className;
	public double[] points;
	public double distance; // B507fSd formants B507fSd
	
	public Point(String className, double[] points) {
		this.className = className;
		this.points = points;
		distance = 0; 
	}
        
        public void displayPoint(){
//            System.out.println("emotion: " + this.className);
//            for(int i=1; i<points.length; i++) {
//                System.out.println(i + ": " + points[i]);
//            }
        }


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public double[] getPoints() {
		return points;
	}

	

	public void setPoints(double[] points) {
		this.points = points;
	}
}

