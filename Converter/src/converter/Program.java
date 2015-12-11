package converter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import data.TextGridFile;

public class Program {

	public static void main(String[] args) {
		try {
			List<Path> files = Loader.loadFiles("C:\\Users\\Andrei\\Desktop\\partial_B4_CotrutaAndreea");
			List<TextGridFile> textGridFiles = Converter.convert(files);
			textGridFiles.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
