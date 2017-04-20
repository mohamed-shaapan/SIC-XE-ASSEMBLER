package validators;

import operation.Operation;

public class OperandValidator implements IValidator {

    public OperandValidator() {

    }

    @Override
    public Boolean validate(String content, Operation operation) {
        // TODO Auto-generated method stub
        switch (operation.getFormatType()) {
            case 1:
                return format1(content);
            case 2:
                return format2(content);
            case 3:
                return format3(content);
            case 4:
                return format4(content);
        }
        return false;
    }

    private boolean format1(String content) {
        return content.trim().isEmpty();
    }

    private boolean format2(String content) {
        if (content.length() == 1) {
            return RegisterValidator.isRegister(content);
        } else if (content.length() == 3) {
            return RegisterValidator.isRegister(content.substring(0, 1))
                    && RegisterValidator.isRegister(content.substring(2));
        }
        return false;
    }

    private boolean format3(String content) {
        return false;
    }

    private boolean format4(String content) {
        return false;
    }

}
