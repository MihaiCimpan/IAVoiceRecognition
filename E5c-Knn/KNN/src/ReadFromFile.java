import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {
	public String fileName1 = "data\\res_formants.txt";
	public String fileName2 = "data\\res_mfcc.txt";
	public String fileName3 = "data\\res_pitch.txt";
	List<Point> pointsSet = new ArrayList<>();
	List<Point> trainingSet = new ArrayList<>();
	List<Point> inputSet = new ArrayList<>();

	public ReadFromFile() {
	}

	public List<Point> getTrainingSet() {
		return trainingSet;
	}

	public List<Point> getInputSet() {
		return inputSet;
	}

	public int fileSize() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName1));
		int lines = 0;
		while (br.readLine() != null)
			lines++;
		br.close();
		return lines;
	}

	public void readFile() throws FileNotFoundException, IOException {
		BufferedReader br1 = new BufferedReader(new FileReader(fileName1));
		BufferedReader br2 = new BufferedReader(new FileReader(fileName2));
		BufferedReader br3 = new BufferedReader(new FileReader(fileName3));
		String line1, line2, line3;
		String fileSplit = " ";
		int i = 0;
		int counter = fileSize() / 8;
		double countN = 0;
		double countS = 0;
		double countF = 0;
		double countJ = 0;
		double countD = 0;
		double countA = 0;
		double countB = 0;

		while (i < counter) {
			int vectornr = 0;
			int index1 = 1;
			int index2 = 1;
			int index3 = 1;
			double vector[] = new double[33];
			String emotion = "";
			char emotionLetter = 'X';

			while (index1 <= 8 && (line1 = br1.readLine()) != null) {
				index1++;
				String[] number = line1.split(fileSplit);
				if (emotionLetter == 'X') {
					emotionLetter = line1.charAt(5);
					switch (emotionLetter) {
					case 'N':
						emotion = "neutral";
						countN++;
						break;
					case 'S':
						emotion = "sadness";
						countS++;
						break;
					case 'F':
						emotion = "fury";
						countF++;
						break;
					case 'J':
						emotion = "joy";
						countJ++;
						break;
					case 'D':
						emotion = "disgust";
						countD++;
						break;
					case 'A':
						emotion = "anxiety";
						countA++;
						break;
					case 'B':
						emotion = "boredom";
						countB++;
						break;
					}
				}
				Double convNumber = Double.parseDouble(number[2]);
				vectornr++;
				vector[vectornr] = convNumber;
			}

			while (index2 <= 22 && (line2 = br2.readLine()) != null) {
				index2++;
				String[] number = line2.split(fileSplit);
				Double convNumber = Double.parseDouble(number[2]);
				vectornr++;
				vector[vectornr] = convNumber;
			}

			while (index3 <= 2 && (line3 = br3.readLine()) != null) {
				index3++;
				String[] number = line3.split(fileSplit);
				Double convNumber = Double.parseDouble(number[2]);
				vectornr++;
				vector[vectornr] = convNumber;
			}
			Point p = new Point(emotion, vector);
			pointsSet.add(p);
			i++;
		}

		br1.close();
		br2.close();
		br3.close();

		double n1 = countN / 100 * 75;
		int n2 = (int) n1 + 1;
		double s1 = countS / 100 * 75;
		int s2 = (int) s1 + 1;
		double f1 = countF / 100 * 75;
		int f2 = (int) f1 + 1;
		double j1 = countJ / 100 * 75;
		int j2 = (int) j1 + 1;
		double d1 = countD / 100 * 75;
		int d2 = (int) d1 + 1;
		double a1 = countA / 100 * 75;
		int a2 = (int) a1 + 1;
		double b1 = countB / 100 * 75;
		int b2 = (int) b1 + 1;

		int tn = 0, ts = 0, tf = 0, tj = 0, td = 0, ta = 0, tb = 0;
		for (int j = 0; j < pointsSet.size(); j++) {
			String em = pointsSet.get(j).getClassName();
			switch (em) {
			case "neutral":
				tn++;
				if (tn > n2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			case "sadness":
				ts++;
				if (ts > s2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			case "fury":
				tf++;
				if (tf > f2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			case "joy":
				tj++;
				if (tj > j2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			case "disgust":
				td++;
				if (td > d2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			case "anxiety":
				ta++;
				if (ta > a2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			case "boredom":
				tb++;
				if (tb > b2) {
					inputSet.add(pointsSet.get(j));
				} else {
					trainingSet.add(pointsSet.get(j));
				}
				break;
			}
		}
	}
}
