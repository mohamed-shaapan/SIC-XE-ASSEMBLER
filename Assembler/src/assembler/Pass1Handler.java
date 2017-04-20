package assembler;

import java.util.Map;

import operation.Operation;
import parsers.LineParser;
import storage.FileHandler;
import storage.SrcFileHandler;
import validators.LineValidator;

public class Pass1Handler {
	
	private String fileDirectory,srcFileDirectory;
	private LineParser lineParser;
	private static Pass1Handler instanceObj;
	private LineValidator lineValidator;
	private Map<String, Operation> operationTable;
	private Map<String,String> symbolTable;
	
	private Pass1Handler(String fileDirectory , String srcFileDirectory) {
		this.fileDirectory = fileDirectory;
		this.srcFileDirectory = srcFileDirectory;
		lineParser = LineParser.getInstance();
		lineValidator = new LineValidator();
		fillOperationTable();
	}
	
	private void fillOperationTable(){
		operationTable = FileHandler.readFile(fileDirectory);
	}
	
	private boolean ConstructSymTable(){
		try{
			Boolean error = false;
			String[] srcFile = SrcFileHandler.readSrcFile(this.srcFileDirectory);
			for(String statement : srcFile){
				String[] data = lineParser.parseLine(statement);
				if(data == null){
					error = true;
					//write error messege in file
				}
				else{
					if(lineValidator.validateLine(data, operationTable.get(data[1]))){
						//update location counter and symbol table
						//lineAddressGenerator.generateLineAdress(i-1, currentLineContents);
					}
					else{
						error = true;
					}
				}
			}
			//05_store intermediate file
			//IntermediateFileHandler.storeSymTable();
			//IntermediateFileHandler.storeFile(intermediateFile, intermediateFileDirectory);
		} catch(Exception e){
		}
		return true;
	}
	public static Pass1Handler getInstance(){
	    if(instanceObj == null){
	        //instanceObj = new Pass1Handler(fileDirectory, srcFileDirectory)
	    }
	    return instanceObj;
	}
}
