package a_1_src_code_parser;

public class LineParser {
	
	private static LineParser parserObj;
	private LineParser(){
	}
	
	public String[] parseLine(String line){
		if(validateLineFormat(line)){
			return extractParameters(line);
		}
		return null;
	}
	
	//validate line format length range only
	private boolean validateLineFormat(String line){
		boolean cond1 = (line.length() < 67);
		boolean cond2 = (line.substring(0, 9).split(" +").length <= 1);
		boolean cond3 = (line.substring(10, 16).split(" +").length == 1);
		boolean cond4 = (line.substring(18, 36).split(" +").length <= 1);
		boolean cond5 = (line.charAt(9)== ' ');
		boolean cond6 = line.substring(16, 18).equals("  ");
		return cond1&&cond2&&cond3&&cond4&&cond5&&cond6;
	}
	
	//extract line content [label,operation name,operands,comment]
	private String[] extractParameters(String line){
		return line.split(" +");
	}
	
	//signelton
	public static LineParser getInstance(){
		if(parserObj==null){
			parserObj = new LineParser();
		}
		return parserObj;
	}
}