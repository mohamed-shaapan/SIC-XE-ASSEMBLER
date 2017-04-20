package IntermediateFile;

import java.util.ArrayList;
import java.util.List;

import statement.IStatement;
import statement.Operation;

public class LineAddressGenerator {
	
	//01_ATTRIBUTES
	//*******************************************************
	private List<String> intermediateContent;
	private int currentLineAddress,nextLineAddress;
	
	
	//02_CONSTRUCTOR
	//*******************************************************
	// we call it on first statement (start)
	public LineAddressGenerator(){
		intermediateContent = new ArrayList<String>(); 
	}
	
	public void setInitialAddress(String startOperand){
		currentLineAddress = Integer.parseInt(startOperand);
		nextLineAddress = currentLineAddress;
	}
	
	//03_METHODS
	//*******************************************************
	public String appendStatement(String originalStatement ,String statementContent[] , IStatement statement){
		currentLineAddress = nextLineAddress;
		if(statement instanceof Operation){
			nextLineAddress += calculateNextLineAddress(statement.getFormatType());
		} else{
			nextLineAddress += calculateNextLineAddress(statementContent);
		}
		//02_generate current line for intermediate file
		String address = convertToHexa(currentLineAddress);
		String line = address + "    " + originalStatement;
		intermediateContent.add(line);
		//System.out.println(line);
		return address;
	}
	
	public void appendError(String originalStatement, String errorMessege){
		intermediateContent.add(new String("." + errorMessege));
		intermediateContent.add(originalStatement);
	}
	
	public void appendComment(String originalStatement){
		intermediateContent.add(new String("          " + originalStatement));
	}
	
	public List<String> getContent(){
		return this.intermediateContent;
	}
	
	private int calculateNextLineAddress(String currentStatement[]){
		if(currentStatement[1].equalsIgnoreCase("START")){
			nextLineAddress = Integer.parseInt(currentStatement[2]);
		}
		else if(currentStatement[1].equalsIgnoreCase("RESW")){
			nextLineAddress += 3*Integer.parseInt(currentStatement[2]);
		}
		else if(currentStatement[1].equalsIgnoreCase("RESB")){
			nextLineAddress += Integer.parseInt(currentStatement[2]);
		}
		else if(currentStatement[1].equalsIgnoreCase("WORD")){
			nextLineAddress += 3;
		}
		else if(currentStatement[1].equalsIgnoreCase("BYTE")){
			// nextLineAddress += // calculate total size of operand
		}
		//ORG  EQUATE
		return nextLineAddress;
	}
	
	private int calculateNextLineAddress(int formatType){
		return nextLineAddress + Math.min(4,formatType);
	}
			
	private String convertToHexa(int value){
		String str = "" , val = "";
		while(str.length()<6){
			int curr = value%16;
			value/=16;
			str+=curr;
		}
		for(int i=str.length();i>=0;i--){
			val+=str.charAt(i);
		}
		return val;
	}
}
	