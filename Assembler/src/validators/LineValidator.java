package validators;

import java.util.Map;

import operation.Operation;

public class LineValidator {
	private IValidator labelValidator,operationValidator,operandValidator,commentValidator;
	
	public LineValidator() {
		labelValidator = new LabelValidator();
		operationValidator = new OperationValidator();
		operandValidator = new OperandValidator();
		commentValidator = new CommentValidator();
	}
	
	public boolean validateLine(String[] line , Operation operation){
		boolean cond1 = labelValidator.validate(line[0], operation);
		boolean cond2 = operationValidator.validate(line[1], operation);
		boolean cond3 = operandValidator.validate(line[2], operation);
		boolean cond4 = commentValidator.validate(line[3], operation);
		return cond1&&cond2&&cond3&&cond4;
	}
}
