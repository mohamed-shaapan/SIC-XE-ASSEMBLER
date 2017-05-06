package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	public static void loadFile(String fileAAddress, Map<String, String> symbolTable, List<ArrayList<String>> loadedFileContent, List<String> IntermediateFile){
		try {
			BufferedReader bufferedReaader= new BufferedReader(new FileReader(new File(fileAAddress)));
			int lines = 0;
			boolean flag = false;
			while(bufferedReaader.ready()){
				String line = bufferedReaader.readLine();
				ArrayList<String> currentValues = new ArrayList<String>();
				if(line.trim().startsWith(".")){
					IntermediateFile.add(line);
					currentValues.add(".");
					continue;
				}
				if(line.equals(""))continue;
				if(line.equals("s y m b o l  t a b l e ")){
					flag = true;
					line = bufferedReaader.readLine();
					//IntermediateFile.add(line);
					line = bufferedReaader.readLine();
					//IntermediateFile.add(line);
					continue;
				}
				if(flag){
					if(lines<1){
						lines++;
						continue;
					}
					lines = 0;
					symbolTable.put(line.split("\\|")[1].trim(), line.split("\\|")[2].trim());
					//System.out.println(line.split("\\|").length);
					//System.out.println(Arrays.toString(line.split("\\|")));
					//System.out.println(line.split("|")[1].trim() + "   " + line.split("|")[2].trim());
					continue;
				}
				while(line.length()<44)line+=" ";
				IntermediateFile.add(line);
				// LC  label operation  operand
				currentValues.add(line.substring(0, 6));
				currentValues.add(line.substring(10, 18).trim());
				currentValues.add(line.substring(19, 25).trim());
				currentValues.add(line.substring(27, Math.min(44, line.length())).trim());
				loadedFileContent.add((ArrayList<String>) currentValues.clone());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
