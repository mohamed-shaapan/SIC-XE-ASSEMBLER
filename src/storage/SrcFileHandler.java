package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class SrcFileHandler {
	public static String[] readSrcFile(String fileDirectory) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileDirectory)));
			while (bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				result.add(line);
			}
			bufferedReader.close();
		} catch (Exception e) {

		}
		return result.toArray(new String[result.size()]);
	}
}
