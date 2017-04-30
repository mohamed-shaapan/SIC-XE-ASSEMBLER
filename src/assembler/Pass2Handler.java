package assembler;

import java.util.Map;

import storage.IntermediateFileHandler;
import storage.ListingFileHandler;

public class Pass2Handler {
	
	//01_ATTRIBUTES
	//*****************************************
	private String intermediateFile[][];
	private Map<String, String> symbolTable;
	private String listingFile[][];
	
	
	//02_CONTRSUCTOR
	//*****************************************
	public Pass2Handler(String intermediateFileAddress){
		//01_load file into memory
		IntermediateFileHandler.loadFile(intermediateFileAddress, symbolTable, intermediateFile);
		//02_generate listing file
		generateListingFile();
		//03_store listing file to disk
		String listingFileAddress="ListingFile.txt";
		ListingFileHandler.storeFile(listingFile, listingFileAddress);
		
	}
	
	//03_METHODS
	//*****************************************
	private void generateListingFile(){
		
	}
	
	private String generateInstructionObjectCode(String instruction[]){
		
		return null;
	}

}
