package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.Map;

import a_1_src_code_parser.LineParser;
import storage.FileHandler;

public class Pass1Handler {
	
	private String fileDirectory;
	private LineParser lineParser;
	private Map<String, Operation> operationTable;
	private static Pass1Handler instanceObj;
	private LineValidator lineValidator;
	
	private Pass1Handler(String fileDirectory) {
		this.fileDirectory = fileDirectory;
		lineParser = LineParser.getInstance();
		lineValidator = new LineValidator();
		fillOperationTable();
	}
	
	private void fillOperationTable(){
		operationTable = FileHandler.readFile(fileDirectory);
	}
	
	private boolean ConstructSymTable(File assemlbyFile){
		try{
			Boolean error = false;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(assemlbyFile));
			while(bufferedReader.ready()){
				String statement = bufferedReader.readLine();
				String[] data = lineParser.parseLine(statement);
				if(data == null){
					error = true;
					//write error messege in file
				}
				else{
					if(lineValidator.validateLine(data, operationTable.get(data[1]))){
						//update location counter and symbol table
					}
					else{
						error = true;
					}
				}
			}
		} catch(Exception e){
		}
		return true;
	}
		
	public static Pass1Handler getInstance(String fileDirectory){
		if(instanceObj == null){
			instanceObj = new Pass1Handler(fileDirectory);
		}
		return instanceObj;
	}
}
