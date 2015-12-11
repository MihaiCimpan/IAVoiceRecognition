package converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Loader {

	public static List<Path> loadFiles(String path) throws IOException {
		return Files.walk(Paths.get(path))
					.filter(Files::isRegularFile)
					.filter(e -> e.getFileName().toString().endsWith(".TextGrid"))
					.collect(Collectors.toList());
	}
	
}
