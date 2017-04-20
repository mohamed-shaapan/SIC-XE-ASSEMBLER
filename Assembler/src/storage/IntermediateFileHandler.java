package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class IntermediateFileHandler {

	public static void storeFile(String intermediateFile[], String fileDirectory){
		try{
			PrintWriter fileWriter = new PrintWriter(new File(fileDirectory));
			for (String line : intermediateFile) {
				fileWriter.println(line);
			}
			fileWriter.close();
		} catch(Exception e){
			
		}
	}
}
