package validators;

import exception.StatementException;
import statement.IStatement;
import statement.Operation;
import tools.Checker;

// instruction 12344 hexadexcimal address
// instruction label
// instruction label,x address
//**********************************************
//word -(4 decimal digits) or 4 decimal digits
//byte C'no limit'
//byte X'no limit must be even'
// RESSB RESW  operand max 4 decimal digits
// directive 123 decimal value  -->   word,resw,resb
// directive X'123' hexadecimal value --> byte
// directive C'123' string value --> byte

public class OperandValidator implements IValidator {

	
	
	private Operand type;
	
	
	public OperandValidator() {

	}

	@Override
	public Boolean validate(String content, IStatement operation) throws StatementException {
		String str = content.trim();
		if (operation instanceof Operation) {
			return format3(str, operation);
		} else {
			return checkDirective(str, operation);
		}
	}

	// it will break if we try BYTE -123 or WORD C'assa'
	private boolean checkDirective(String content, IStatement directive) throws StatementException {
		String error = "";
		boolean flag = false;
		// limits for X'max 14 hexa digits' || C'max 15 char'
		if (directive.getOpName().equalsIgnoreCase("BYTE")) {
			if (content.charAt(0) == 'C' || content.charAt(0) == 'c') {
				if (content.charAt(1) == '\'' && content.charAt(content.length() - 1) == '\'')
					if (content.substring(2, content.length() - 1).length() <= 15)
						flag = true;
			}
			if (content.charAt(0) == 'X' || content.charAt(0) == 'x') {
				if (content.charAt(1) == '\'' && content.charAt(content.length() - 1) == '\'')
					flag = (content.substring(2, content.length() - 1).length() % 2 == 0)
							&& Checker.checkHexaNumber(content.substring(2, content.length() - 1))
							&& content.substring(1, content.length() - 1).length() <= 14;
			} else {
				error = "illegel expression with BYTE expecting C'' or X''";
			}
		}
		// limits for word - or not (4 decimal digits)
		if (directive.getOpName().equalsIgnoreCase("WORD")) {
			if (content.length() <= 5 && !flag) {
				if (content.charAt(0) == '-') {
					flag = Checker.checkDecimalNumber(content.substring(1));
				} else {
					flag = (content.length() < 5);
					if (!flag) {
						error = "exceeds limit with WORD";
					}
					flag = flag && Checker.checkDecimalNumber(content);
				}
			}

		}
		// limits not more than 4 decimal digits
		if (directive.getOpName().equalsIgnoreCase("RESW") || directive.getOpName().equalsIgnoreCase("RESB")) {
			flag = (content.trim().length() <= 4) && (Checker.checkDecimalNumber(content));
			if (!flag) {
				error = "illegel operand with RES";
			}
		}

		// must be hexadecimal value no label
		if (directive.getOpName().equalsIgnoreCase("START")) {
			flag = Checker.checkHexaAddress(content);
		}

		// no numeric address after END or empty
		if (directive.getOpName().equalsIgnoreCase("END")) {
			flag = Checker.checkName(content) || (content.trim().isEmpty());
		}
		if (flag)
			return true;
		if (error.isEmpty())
			throw new StatementException("Invalid Directive Operands");
		else
			throw new StatementException(error);
	}

	private boolean checkChar(String content) {
		for (int i = 0; i < content.length(); i++) {
			if (Checker.inBetween(content, i, 'a', 'z') || Checker.inBetween(content, i, 'A', 'Z'))
				continue;
			return false;
		}
		return true;
	}

	private boolean format3(String content, IStatement operation) throws StatementException {
		if (generalChecker(content, operation)){
			type = new Label();
			return true;
		}
		if(Checker.checkLiteral(content)==true){
			type = new Literal();
			return true;
		}
		throw new StatementException("Invalid Operation Operand");
	}

	private boolean generalChecker(String content, IStatement operation) {
		if (operation.getNumberOfOperands() == 0)
			return (content.trim().isEmpty());
		return (Checker.checkName(content) || Checker.checkHexaAddress(content) || Checker.checkStar(content));
	}
	
	public Operand getOperandType(){
		return type;
	}
	

}