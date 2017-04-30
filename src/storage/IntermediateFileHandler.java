package storage;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import printer.Printer;

public class IntermediateFileHandler {

	public static void storeFile(List<String> intermediateFileContent, Map<String,String> symbolTable ,String fileDirectory){
		try{
			PrintWriter printWriter = new PrintWriter(new File(fileDirectory));
			for (String line : intermediateFileContent) {
				printWriter.println(line);
			}
			printWriter.println();
			printWriter.println();
			printWriter.println();
			Iterator<Entry<String,String>> iterator = symbolTable.entrySet().iterator();
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			ArrayList<String> columnNames = new ArrayList<String>();
			columnNames.add(new String("name"));
			columnNames.add(new String("value"));
			data.add(columnNames);
			while(iterator.hasNext()){
				ArrayList<String> row = new ArrayList<String>();
				Entry<String,String>  currentRow = iterator.next();
				row.add(currentRow.getKey());
				row.add(currentRow.getValue());
				data.add(row);
			}
			Printer.printTable(data, new String("s y m b o l  t a b l e "), printWriter);
			printWriter.close();
		} catch(Exception e){
			
		}
	}
	
	
	public static void loadFile(String fileAAddress, Map<String, String> symbolTable, String loadedFile[][]){
		
	}
	
	
}
