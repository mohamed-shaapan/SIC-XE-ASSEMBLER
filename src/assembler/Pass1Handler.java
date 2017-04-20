package assembler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IntermediateFile.LineAddressGenerator;
import parsers.LineParser;
import statement.IStatement;
import storage.FileHandler;
import storage.IntermediateFileHandler;
import storage.SrcFileHandler;
import validators.LineValidator;

public class Pass1Handler {
	
	private String operationsFileDirectory,directivesFileDirectory,srcFileDirectory,intermediateFileDirectory;
	private LineParser lineParser;
	private LineValidator lineValidator;
	private Map<String, IStatement> statementTable;
	private Map<String,String> symbolTable;
	private LineAddressGenerator lineAddressGenerator;
	
	public Pass1Handler(String operationsFileDirectory, String directivesFileDirectory, String srcFileDirectory, String intermediateFileDirectory) {
		this.operationsFileDirectory = operationsFileDirectory; 
		this.directivesFileDirectory = directivesFileDirectory;
		this.srcFileDirectory = srcFileDirectory;
		this.intermediateFileDirectory = intermediateFileDirectory;
		fillOperationTable();
		lineParser = LineParser.getInstance();
		lineValidator = new LineValidator();
		symbolTable = new HashMap<String,String>();
		lineAddressGenerator = new LineAddressGenerator();
	}
	
	private void fillOperationTable(){
		statementTable = FileHandler.readFiles(operationsFileDirectory,directivesFileDirectory);
	}
	
	public boolean ConstructSymTable(){
		Boolean error = false, firstStatement = true;
		try{
			String[] srcFile = SrcFileHandler.readSrcFile(this.srcFileDirectory);
			for(String statement : srcFile){
				if(statement.charAt(0) == '.'){
					lineAddressGenerator.appendComment(statement);
					continue;
				}
				String[] data = lineParser.parseLine(statement);
				if(data == null){
					error = true;
					lineAddressGenerator.appendError(statement, "syntax error1");
				}
				else{
					//System.out.println(data[1]);
					//System.out.println(statementTable.get(data[1]));
					if(lineValidator.validateLine(data,statementTable.get(data[1].toLowerCase()))&&symbolTable.get(data[0])==null){
						if(firstStatement){
							firstStatement = false;
							lineAddressGenerator.setInitialAddress(data[2]);
						}
						String address = lineAddressGenerator.appendStatement(statement, data, statementTable.get(data[1].toLowerCase()));
						if(data[0].replaceAll(" ", "").length()!=0){ //correct or "     "
							symbolTable.put(data[0], address);
						}
					}
					else{
						error = true;
						lineAddressGenerator.appendError(statement, "syntax error2");
					}
				}
			}
			List<String> intermediateFileContent = lineAddressGenerator.getContent();
			IntermediateFileHandler.storeFile(intermediateFileContent,symbolTable,intermediateFileDirectory);
		} catch(Exception e){
			e.printStackTrace();
		}
		return error;
	}
}