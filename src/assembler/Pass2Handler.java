package assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Data;
import statement.Directive;
import statement.IStatement;
import storage.IntermediateFileHandler;
import storage.ListingFileHandler;

public class Pass2Handler {
	
	//01_ATTRIBUTES
	//*****************************************
	private Map<String, String> symbolTable;
	private List<ArrayList<String>> intermediateFileContent;
	private List<String> intermediateFile,listingFile;
	
	
	//02_CONTRSUCTOR
	//*****************************************
	public Pass2Handler(String intermediateFileAddress , String listingFileAddress){
		intermediateFile = new ArrayList<String>();
		listingFile = new ArrayList<String>();
		intermediateFileContent = new ArrayList<ArrayList<String>>();
		symbolTable = new HashMap<String,String>();
		
		//01_load file into memory
		IntermediateFileHandler.loadFile(intermediateFileAddress, symbolTable, intermediateFileContent,intermediateFile);
		//02_generate listing file
		generateListingFile();
		//03_store listing file to disk
		ListingFileHandler.storeFile(listingFile, listingFileAddress);
	}
	
	//03_METHODS
	//*****************************************
	private void generateListingFile(){
		int ind = 0;
		for(String line : this.intermediateFile){
			ind++;
			String objectCode = "";
			if(line.trim().startsWith(".")){
				this.listingFile.add(line);
				continue;
			}
			System.out.println(Arrays.toString(this.intermediateFileContent.get(ind-1).toArray()));
			if(Data.statementTable.get(intermediateFileContent.get(ind-1).get(2).toLowerCase()) instanceof Directive){
				objectCode = generateDirectiveObjectCode(intermediateFileContent.get(ind-1));
			}
			else{
				objectCode= generateInstructionObjectCode(this.intermediateFileContent.get(ind-1));
			}
			this.listingFile.add(line + "    " + objectCode);
			if(intermediateFileContent.size()==ind)break;
		}
		while(ind<intermediateFile.size()){
			this.listingFile.add(intermediateFile.get(ind++));
		}
	}
	
	private String generateInstructionObjectCode(ArrayList<String> instructionContent){
		IStatement statement = Data.statementTable.get(instructionContent.get(2).toLowerCase());
		if(statement instanceof Directive)return "";
		String opCode = Integer.toHexString(Integer.parseInt(statement.getOpCode()));
		if(opCode.length()<2)opCode = "0" + opCode;
		String operandAddress;
		int indOfColon = instructionContent.get(3).indexOf(',');
		boolean indexing = true;
		if(indOfColon==-1){
			indOfColon = instructionContent.get(3).length();
			indexing = false;
		}
		operandAddress = this.symbolTable.get(instructionContent.get(3).substring(0, indOfColon).trim());
		if(operandAddress==null && statement.getNumberOfOperands()!=0){
			operandAddress = instructionContent.get(3).trim();
			while(operandAddress.length()<6)operandAddress = "0" + operandAddress;
		}
		if(statement.getNumberOfOperands()==0){
			operandAddress = "000000";
		}
		return opCode+concatenate(indexing?1:0,operandAddress.charAt(2))+operandAddress.substring(3);
	}
	
	private String generateDirectiveObjectCode(ArrayList<String> directiveContent){
		String result = "";
		if(directiveContent.get(2).equalsIgnoreCase("BYTE")){
			String data = directiveContent.get(3).trim();
			if (data.charAt(0) == 'X' || data.charAt(0) == 'x') {
                if (data.charAt(1) == '\'' && data.charAt(data.length() - 1) == '\''){
				//System.out.println("here");
				result = data.substring(2,directiveContent.get(3).length()-1);
				//while(result.length()<6)result = "0" + result;
                }
			}
			else{
				result = getHexaFromChars(data.substring(2,data.length()-1));
			}
		}
		else if(directiveContent.get(2).equalsIgnoreCase("WORD")){
			result = getHexaFromDecimal(directiveContent.get(3));
		}
		
		else;
		return result;
	}
	
	private char concatenate(int x, char hexaDigit){
		//if(hexaDigit == 'f' && x ==1)    can not ocuur as address ae 15 bits not 16 bits  [x 3bit hex3 hex2 hex1] 
		return (char)((int)hexaDigit+(x*8));
	}
	
	
	private String getHexaFromChars(String stringValue){
		String value = "";
		for(int i=stringValue.length()-1;i>=0;i--){
			value = Integer.toHexString(((int)stringValue.charAt(i)))+value;
		}
//		while(value.length()<6){
//			value = "0" + value.substring(0);
//		}
		return value;
	}
	
	
	//handling if one hexa wrong in phase1 ,, no limit on word
	private String getHexaFromDecimal(String decimalValue){
		String value = Integer.toHexString(Integer.parseInt(decimalValue));
		while(value.length()<6){
			value = "0" + value.substring(0);
		}
		return value;
	}
	
}
