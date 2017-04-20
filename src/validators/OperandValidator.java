package validators;

import statement.Directive;
import statement.IStatement;

public class OperandValidator implements IValidator {

    public OperandValidator() {

    }

    @Override
    public Boolean validate(String content, IStatement operation) {
        String str = content.trim();
        switch (operation.getFormatType()) {
            case 1:
                return format1(str);
            case 2:
                return format2(str);
            case 3:
                return format3(str, operation);
            case 4:
                return format4(str, operation);
        }
        return false;
    }

    private boolean format1(String content) {
        return content.isEmpty();
    }

    private boolean format2(String content) {
        if (content.length() == 1) {
            return isRegister(content);
        }
        if (content.length() == 3) {
            return isRegister(content.substring(0, 1)) && isRegister(content.substring(2));
        }
        return false;
    }

    private boolean format3(String content, IStatement operation) {
        if (operation instanceof Directive) {
            return checkDecimalNumber(content);
        } else {
            if (content.charAt(0) == '#' || content.charAt(0) == '@') {
                return checkName(content.substring(1)) || checkDecimalNumber(content.substring(1));
            }
            if (content.substring(0, 2).equalsIgnoreCase("0x")) {
                return checkHexaNumber(content.substring(3));
            }
            return checkName(content);
        }

    }

    private boolean format4(String content, IStatement operation) {
        if (operation instanceof Directive) {
            return false;
        }
        return format3(content, operation);
    }

    private boolean isRegister(String reg) {
        String[] registers = new String[] {"A", "S", "T", "L", "B", "F"};
        for (String c : registers) {
            if (reg.equalsIgnoreCase(c))
                return true;
        }
        return false;
    }

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
