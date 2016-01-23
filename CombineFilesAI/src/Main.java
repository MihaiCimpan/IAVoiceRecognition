
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {
		List<String> keySet = new ArrayList<>();
		List<String> files = new ArrayList<>();
		HashMap<String, HashMap<String, Double> > results = new HashMap<String, HashMap<String, Double> > ();
		
		BufferedReader brPitch = new BufferedReader(new FileReader("res_pitch.txt"));
		String line;
		while( (line = brPitch.readLine()) != null ) {
			String[] split = line.split(" ");
			files.add(split[0]);
			if(keySet.contains(split[1])==false)
				keySet.add(split[1]);
			HashMap<String, Double> value = results.getOrDefault(split[0], new HashMap<String, Double>());
			value.put(split[1], Double.parseDouble(split[2]));
			results.put(split[0], value);
		}
		brPitch.close();
		
		BufferedReader brFormants = new BufferedReader(new FileReader("res_formants.txt"));
		while( (line = brFormants.readLine()) != null ) {
			String[] split = line.split(" ");
			if(keySet.contains(split[1])==false)
				keySet.add(split[1]);
			HashMap<String, Double> value = results.getOrDefault(split[0], new HashMap<String, Double>());
			value.put(split[1], Double.parseDouble(split[2]));
			results.put(split[0], value);
		}
		brFormants.close();
		
		BufferedReader brMFCC = new BufferedReader(new FileReader("res_mfcc.txt"));
		while( (line = brMFCC.readLine()) != null ) {
			String[] split = line.split(" ");
			if(keySet.contains(split[1])==false)
				keySet.add(split[1]);
			HashMap<String, Double> value = results.getOrDefault(split[0], new HashMap<String, Double>());
			value.put(split[1], Double.parseDouble(split[2]));
			results.put(split[0], value);
		}
		brMFCC.close();
		
		PrintWriter writer = new PrintWriter("datasetFinal.csv", "UTF-8");
		writer.print("emotion");
		for(String name: keySet) {
			writer.print("," + name);
		}
		writer.println("");
		
		System.out.println(results.size());
		for(Map.Entry<String, HashMap<String, Double> > entry: results.entrySet()) {
			writer.print(entry.getKey().charAt(entry.getKey().length() - 2));
			for(String name: keySet) {
				writer.print("," + entry.getValue().get(name));
			}
			writer.println("");
		}
		writer.flush();
		writer.close();
		
		
	}

}
