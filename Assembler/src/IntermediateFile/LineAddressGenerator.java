package IntermediateFile;

public class LineAddressGenerator {
	
	//01_ATTRIBUTES
	//*******************************************************
	private String intermediateFile[];
	private String srcCode[];
	private String nextLineAddress;
	private String currentLineAddress;
	
	
	//02_CONSTRUCTOR
	//*******************************************************
	public LineAddressGenerator(String intermediateFile[], String srcCode[]){
		currentLineAddress="0000"; //START LINE
		nextLineAddress="";
		this.intermediateFile=intermediateFile;
		this.srcCode=srcCode;
	}
	
	
	//03_METHODS
	//*******************************************************
	public void generateLineAdress(int lineIndex, String currentLine[]){
		//00_update current line address
		currentLineAddress=nextLineAddress;
		//01_calculate next line address
		nextLineAddress=calculateNextLineAddress(lineIndex, currentLine);
		//02_generate current line for intermediate file
		String line=currentLineAddress+" "+srcCode[lineIndex];
		intermediateFile[lineIndex]=line;
	}
	
	
	private String calculateNextLineAddress(int lineIndex, String currentLine[]){
		//01_SPECIAL CASES
		if(currentLine[1].equalsIgnoreCase("START")){
			nextLineAddress="0000";
		}
		if(currentLine[1].equalsIgnoreCase("END")){
			
		}
		if(currentLine[1].equalsIgnoreCase("RESW")){
			
		}
		if(currentLine[1].equalsIgnoreCase("RESB")){
			
		}
		//02_GENERAL CASE
		//currentLine[1];
		
		
		return "";
	}

}
	