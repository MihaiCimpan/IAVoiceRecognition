package converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import data.*;

public class Converter {
	
	public static List<TextGridFile> convert(List<Path> files) {
		return files.stream()
					.map(e -> buildTextGridFile(e))
					.collect(Collectors.toList());
	}
	
	private static TextGridFile buildTextGridFile(Path path) {
		List<Interval> intervals = buildIntervals(path);
		return new TextGridFile(path.getFileName().toString(), intervals);
	}
	
	private static List<Interval> buildIntervals(Path path) {
		try {
			int size = buildSize(Files.lines(path));
			return buildIntervalsWithSize(Files.lines(path), size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	private static int buildSize(Stream<String> lines) {
		return lines.filter(e -> e.contains("intervals:"))
					.map(e -> e.trim())
				    .map(e -> e.substring(e.lastIndexOf(" ") + 1, e.length()))
				    .map(e -> Integer.parseInt(e))
				    .findFirst()
				    .get();
	}
		
	private static List<Interval> buildIntervalsWithSize(Stream<String> lines, int size) {
		List<String> attributes = buildIntervalsList(lines, size);
		return IntStream.range(0, attributes.size() / 3)
				 .mapToObj(e -> {
					 return new Interval(Double.parseDouble(attributes.get(e * 3)),
							 			 Double.parseDouble(attributes.get(e * 3 + 1)),
							 			 attributes.get(e * 3 + 2));
				 })
				 .collect(Collectors.toList());
	}
	
	private static List<String> buildIntervalsList(Stream<String> lines, int size) {
		return lines.filter(e -> containsData(e))
					.limit(size * 3)
					.map(e -> e.trim())
					.map(e -> e.substring(e.lastIndexOf(" ") + 1, e.length()))
					.collect(Collectors.toList());
	}
	
	private static boolean containsData(String data) {
		return data.contains("            xmin") ||
			   data.contains("            xmax") ||
			   data.contains("            text");
	}

}
	
	

