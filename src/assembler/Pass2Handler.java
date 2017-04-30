package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Data;
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
			if(line.startsWith(".")){
				this.listingFile.add(line);
				continue;
			}
			String objectCode = generateInstructionObjectCode(this.intermediateFileContent.get(ind-1));
			this.listingFile.add(line + "    " + objectCode);
			if(intermediateFileContent.size()==ind)break;
		}
		while(ind<intermediateFile.size()){
			this.listingFile.add(intermediateFile.get(ind++));
		}
	}
	
	private String generateInstructionObjectCode(ArrayList<String> instructionContent){
		String opCode = Data.statementTable.get(instructionContent.get(2)).getOpCode();
		String operandAddress;
		int indOfColon = instructionContent.get(3).indexOf(',');
		boolean indexing = false;
		if(indOfColon==-1){
			indOfColon = instructionContent.get(3).length();
			indexing = true;
		}
		operandAddress = this.symbolTable.get(instructionContent.get(3).substring(0, indOfColon).trim());
		if(operandAddress==null){
			operandAddress = instructionContent.get(3).trim();
		}
		return opCode+concatenate(indexing?1:0,operandAddress.charAt(2))+operandAddress.substring(3);
	}

	private char concatenate(int x, char hexaDigit){
		return (char)((int)hexaDigit+(x));
	}
}
