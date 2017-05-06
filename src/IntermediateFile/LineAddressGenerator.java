package IntermediateFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import statement.IStatement;
import statement.Operation;

public class LineAddressGenerator {

	// 01_ATTRIBUTES
	// *******************************************************
	private List<String> intermediateContent;
	private int currentLineAddress, nextLineAddress;
	public static boolean error;

	// 02_CONSTRUCTOR
	// *******************************************************
	// we call it on first statement (start)
	public LineAddressGenerator() {
		intermediateContent = new ArrayList<String>();
		currentLineAddress = convertFromHexaToDeca(new String("000000"));
		nextLineAddress = currentLineAddress;
	}

	public void setInitialAddress(String startOperand) {		
		currentLineAddress = convertFromHexaToDeca(startOperand);
		nextLineAddress = currentLineAddress;
	}

	// 03_METHODS
	// *******************************************************
	public String appendStatement(String originalStatement, String statementContent[], IStatement statement) {
		if (statement instanceof Operation) {
			nextLineAddress = calculateNextLineAddress();
		} else {
			nextLineAddress = calculateNextLineAddress(statementContent);
		}
		// 02_generate current line for intermediate file
		String address = convertToHexa(currentLineAddress);
		String line = address + "    " + originalStatement;
		intermediateContent.add(line);
		// System.out.println(line);
		currentLineAddress = nextLineAddress;
		return address;
	}

	public void appendError(String originalStatement, String errorMessege) {
		error = true;
		intermediateContent.add(new String(". ****" + errorMessege));
		intermediateContent.add(convertToHexa(currentLineAddress) + "    " + originalStatement);
	}

	public void appendComment(String originalStatement) {
		intermediateContent.add(new String("          " + originalStatement));
	}

	public List<String> getContent() {
		return this.intermediateContent;
	}

	private int calculateNextLineAddress(String currentStatement[]) {
		if (currentStatement[1].equalsIgnoreCase("RESW")) {
			nextLineAddress += 3 * Integer.parseInt(currentStatement[2]);
		} else if (currentStatement[1].equalsIgnoreCase("RESB")) {
			nextLineAddress += Integer.parseInt(currentStatement[2]);
		} else if (currentStatement[1].equalsIgnoreCase("WORD")) {
			nextLineAddress += 3;
		} else if (currentStatement[1].equalsIgnoreCase("BYTE")) {
			// nextLineAddress += // calculate total size of operand
			String chaPattern = "(C*?)(')(.+)(')";
			Matcher queryMatcher = Pattern.compile(chaPattern).matcher(currentStatement[2]);
			if (queryMatcher.matches()) {
				String value = queryMatcher.group(3);
				nextLineAddress += value.length();
			}
			String hexPattern = "(X*?)(')(.+)(')";
			queryMatcher = Pattern.compile(hexPattern).matcher(currentStatement[2]);
			if (queryMatcher.matches()) {
				String value = queryMatcher.group(3);
				nextLineAddress += Math.ceil(value.length()*1. / 2.);
			}
		}
		// ORG EQUATE
		return nextLineAddress;
	}

	private int calculateNextLineAddress() {
		return nextLineAddress + 3;
	}

	private String convertToHexa(int value) {
		String str = Integer.toHexString(value);
		String val = "";
		while(val.length()+str.length()!=6){
			val+="0";
		}
		val+=str;
		return val;
	}
	
	private int convertFromHexaToDeca(String Hexa){
		int num = 0;
		for (int i = Hexa.length() - 1; i >= 0; i--) {
			num += (Hexa.charAt(i) - '0') * Math.pow(16, Hexa.length() - 1 - i);
		}
		return num;
	}
}