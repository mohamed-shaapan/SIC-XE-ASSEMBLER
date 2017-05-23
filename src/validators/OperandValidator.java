package validators;

import elements.EquateHandler;
import elements.Label;
import elements.Literal;
import elements.Operand;
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
        type = new Label();
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
        type = new Label();
        // limits for X'max 14 hexa digits' || C'max 15 char'
        if (directive.getOpName().equalsIgnoreCase("BYTE")) {
            if (Checker.checkStringData(content) || Checker.checkHexaData(content)) {
                flag = true;
            } else {
                error = "illegel expression with BYTE expecting C'' or X''";
            }
        }
        // limits for word - or not (4 decimal digits)
        if (directive.getOpName().equalsIgnoreCase("WORD")) {
            flag = Checker.checkDecimalData(content);
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

        // neither operand nor label with ltorg
        if (directive.getOpName().equalsIgnoreCase("LTORG")) {
            flag = content.trim().isEmpty();
        }
        // EQUate
        if (directive.getOpName().equalsIgnoreCase("EQU")) {
        	flag = EquateHandler.validateEquateOperands(content);
        }
        if (flag)
            return true;
        if (error.isEmpty())
            throw new StatementException("Invalid Directive Operands");
        else
            throw new StatementException(error);
    }

    private boolean format3(String content, IStatement operation) throws StatementException {
        if (generalChecker(content, operation)) {
            type = new Label();
            return true;
        }
        if (Checker.checkLiteral(content)) {
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

    public Operand getOperandType() {
        return type;
    }

}