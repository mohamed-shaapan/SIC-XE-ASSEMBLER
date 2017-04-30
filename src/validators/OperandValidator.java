package validators;

import exception.StatementException;
import statement.IStatement;
import statement.Operation;

//instruction 12344 hexadexcimal address
// instruction #12344 decimal value
// instruction label label address
// instruction #label  address
// instruction @label  address
// instruction label,x address
//**********************************************
//word -(4 decimal digits) or 4 decimal digits
//byte C'max 15 char'
//byte X'max 14 hexa digits'
// REWS RESW  operand max 4 decimal digits
// directive 123 decimal value  -->   word,resw,resb
// directive X'123' hexadecimal value --> byte
// directive C'123' string value --> byte

public class OperandValidator implements IValidator {

    public OperandValidator() {

    }

    @Override
    public Boolean validate(String content, IStatement operation) throws StatementException {
        String str = content.trim();
        if (operation instanceof Operation) {
        	return format3(str, operation);
        } else {
            return checkDirective(str,operation);
        }
    }

    // it will break if we try BYTE -123 or WORD C'assa'
    private boolean checkDirective(String content ,IStatement directive) throws StatementException {
        boolean flag = false;
        if(directive.getOpName().equalsIgnoreCase("BYTE")){
        	if (content.charAt(0) == 'C' || content.charAt(0) == 'c') {
                if (content.charAt(1) == '\'' && content.charAt(content.length() - 1) == '\'')
                    if (content.substring(2, content.length() - 1).length() <= 15)
                        flag = true;
            }
            if (content.charAt(0) == 'X' || content.charAt(0) == 'x') {
                if (content.charAt(1) == '\'' && content.charAt(content.length() - 1) == '\'')
                    flag = checkHexaNumber(content.substring(2, content.length() - 1))
                            && content.substring(1, content.length() - 1).length() <= 14;
            }
    	}
        if(directive.getOpName().equalsIgnoreCase("WORD")){
        	if (content.length() <= 5 && !flag) {
                if (content.charAt(0) == '-') {
                    flag =  checkDecimalNumber(content.substring(1));
                } else {
                    flag =  checkDecimalNumber(content);
                }
            }

        }
        if(flag)return true;
        throw new StatementException("Invalid Directive Operands");
    }

    private boolean checkChar(String content) {
        for (int i = 0; i < content.length(); i++) {
            if (inBetween(content, i, 'a', 'z') || inBetween(content, i, 'A', 'Z'))
                continue;
            return false;
        }
        return true;
    }

//    private boolean format1(String content) {
//        return content.isEmpty();
//    }
//
//    private boolean format2(String content) {
//        if (content.length() == 1) {
//            return isRegister(content);
//        }
//        if (content.length() == 3) {
//            return isRegister(content.substring(0, 1)) && isRegister(content.substring(2));
//        }
//        return false;
//    }

    private boolean format3(String content, IStatement operation) throws StatementException {
    	if (content.substring(0, 2).equalsIgnoreCase("0x")) {
           if(checkHexaNumber(content.substring(3)))return true;
           throw new StatementException("Invalid Hexadecimal Address");
        }
        if(generalChecker(content, operation))return true;
        throw new StatementException("Invalid Operation Operand");
    }

    private boolean generalChecker(String content, IStatement operation) {
        // it will break if #label,x 
    	if (content.charAt(0) == '#' || content.charAt(0) == '@') {
            return checkName(content.substring(1)) || checkDecimalNumber(content.substring(1));
        }
        return checkName(content);

    }

//    private boolean format4(String content, IStatement operation) {
//        if (content.substring(0, 2).equalsIgnoreCase("0x")) {
//            return checkHexaNumber(content.substring(3));
//        }
//        return generalChecker(content, operation);
//    }

//    private boolean isRegister(String reg) {
//        String[] registers = new String[] {"A", "S", "T", "L", "B", "F"};
//        for (String c : registers) {
//            if (reg.equalsIgnoreCase(c))
//                return true;
//        }
//        return false;
//    }

    private boolean checkName(String content) {
        int i = 0;
        if (inBetween(content, 0, '0', '9'))
            return false;
        while (i < content.length()) {
            if (content.charAt(i) == ',')
                break;
            i++;
        }
        if (i + 2 == content.length()) {
            return (content.charAt(i + 1) == 'X' || content.charAt(i + 1) == 'x');
        }
        if (i == content.length())
            return true;

        return false;
    }

    private boolean checkDecimalNumber(String content) {
        for (int i = 0; i < content.length(); i++) {
            if (inBetween(content, i, '0', '9'))
                continue;
            return false;
        }
        return true;
    }

    private boolean checkHexaNumber(String content) {
        for (int i = 0; i < content.length(); i++) {
            if (inBetween(content, i, '0', '9'))
                continue;
            if (inBetween(content, i, 'A', 'F'))
                continue;
            if (inBetween(content, i, 'a', 'f'))
                continue;
            return false;
        }
        return true;
    }

    private boolean inBetween(String content, int index, char start, char end) {
        return content.charAt(index) >= start && content.charAt(index) <= end;
    }
}