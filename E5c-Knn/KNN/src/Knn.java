
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knn {
	public int k;
	public List<Point> trainingSet = new ArrayList<>();
	List<Distance> distancesEuclidian = new ArrayList<>();
	List<Distance> distancesManhattan = new ArrayList<>();
	List<Distance> distancesMinkowski = new ArrayList<>();
	ReadFromFile r = new ReadFromFile();

	public Knn(int k) {
		this.k = k;
	}

	public List<Point> getTrainingSet() {
		return trainingSet;
	}

	public void setTrainingSet(List<Point> trainingSet) {
		this.trainingSet = trainingSet;
	}

	public String returnEmotion(List<Distance> list) {
		int emotions[] = new int[7];
		double emotionDistances[] = new double[7];

		for (int i = 0; i < k; i++) {
			switch (list.get(i).getClassName()) {
			case "neutral":
				emotions[0]++;
				emotionDistances[0] += list.get(i).getValue();
				break;
			case "joy":
				emotions[1]++;
				emotionDistances[1] += list.get(i).getValue();
				break;
			case "sadness":
				emotions[2]++; // 2
				emotionDistances[2] += list.get(i).getValue();
				break;
			case "fury":
				emotions[3]++;
				emotionDistances[3] += list.get(i).getValue();
				break;
			case "anxiety":
				emotions[4]++; // 2
				emotionDistances[4] += list.get(i).getValue();
				break;
			case "boredom":
				emotions[5]++;
				emotionDistances[5] += list.get(i).getValue();
				break;
			case "disgust":
				emotions[6]++;
				emotionDistances[6] += list.get(i).getValue();
				break;
			}
		}

		if (emotions[0] == emotions[1] && emotions[1] == emotions[2] && emotions[2] == emotions[3]
				&& emotions[3] == emotions[4] && emotions[4] == emotions[5] && emotions[5] == emotions[6])
			return list.get(0).getClassName();

		int max = 0;
		int poz = 0;
		double minDistance = 9999999999.0;
		for (int i = 0; i < emotions.length; i++) {
			if (emotions[i] != 0)
				if ((int) emotions[i] > max && emotionDistances[i] / emotions[i] < minDistance) {
					max = (int) emotions[i];
					poz = i;
					minDistance = emotionDistances[i] / emotions[i];
				}
		}

		return list.get(poz).getClassName();

	}

	public String findClass(Point instance, List<Point> trainingSet) throws IOException {

		for (int j = 0; j < trainingSet.size(); j++) {
			double distanceEuclidian = 0;
			double distanceManhattan = 0;
			double distanceMinkowski = 0;

			double[] pointX = instance.getPoints();
			double[] pointY = trainingSet.get(j).getPoints();
			for (int i = 0; i < 33; i++) {
				// distanceEuclidian += Math.pow((pointX[i] - pointY[i]), 2);
				// distanceManhattan += Math.abs(pointX[i] - pointY[i]);
				distanceMinkowski += Math.pow((Math.abs(pointX[i] - pointY[i])), 1.5);

			}
			// distanceEuclidian = Math.sqrt(distanceEuclidian);
			distanceMinkowski = Math.pow(distanceMinkowski, 1 / 1.5);
			// Distance dEuclidian = new Distance(distanceEuclidian,
			// trainingSet.get(j).getClassName());
			// Distance dManhattan = new Distance(distanceManhattan,
			// trainingSet.get(j).getClassName());
			Distance dMinkowski = new Distance(distanceMinkowski, trainingSet.get(j).getClassName());

			// distancesEuclidian.add(dEuclidian);
			// distancesManhattan.add(dManhattan);
			distancesMinkowski.add(dMinkowski);
		}
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		// Collections.sort(distancesManhattan, new DistanceComparator());

		// System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		// for(int i = 0; i< k; i++)
		// System.out.println(distancesEuclidian.get(i).getValue() + " " +
		// (distancesEuclidian.get(i).getClassName()));
		// System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		Collections.sort(distancesMinkowski, new DistanceComparator());
		// Collections.sort(distancesEuclidian, new DistanceComparator());

		// System.out.println("Euclidian");
		// return returnEmotion(distancesEuclidian);
		// System.out.println("\nManhattan");
		// return returnEmotion(distancesManhattan);
		// System.out.println("\nMikowski");
		return returnEmotion(distancesMinkowski);
	}
}
