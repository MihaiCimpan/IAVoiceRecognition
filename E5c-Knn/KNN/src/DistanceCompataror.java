import java.util.Comparator;

class DistanceComparator implements Comparator<Distance> {

	public int compare(Distance d1, Distance d2) {
		if (d1.getValue() - d2.getValue() >= 0)
			return 1;
		else
			return -1;
	}

}