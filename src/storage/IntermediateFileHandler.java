package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IntermediateFileHandler {

	public static void storeFile(List<String> intermediateFileContent, Map<String,String> symbolTable ,String fileDirectory){
		try{
			PrintWriter fileWriter = new PrintWriter(new File(fileDirectory));
			for (String line : intermediateFileContent) {
				fileWriter.println(line);
			}
			fileWriter.println();
			fileWriter.println();
			fileWriter.println();
			Iterator<Entry<String,String>> iterator = symbolTable.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String,String>  currentRow = iterator.next();
				fileWriter.println(currentRow.getKey() + "   " + currentRow.getValue());
			}
			fileWriter.close();
		} catch(Exception e){
			
		}
	}
}
