package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ListingFileHandler {

	public static void storeFile(List<String> listingFile, String fileAddress){
		try {
			PrintWriter printWriter = new PrintWriter(new File(fileAddress));
			for(String line : listingFile){
				printWriter.println(line);
			}
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
