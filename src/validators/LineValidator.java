package validators;

import java.util.Map;

import statement.IStatement;
import statement.Operation;

public class LineValidator {
	private IValidator labelValidator,operationValidator,operandValidator,commentValidator;
	
	public LineValidator() {
		labelValidator = new LabelValidator();
		operationValidator = new OperationValidator();
		operandValidator = new OperandValidator();
		commentValidator = new CommentValidator();
	}
	
	public boolean validateLine(String[] line , IStatement statement){
		boolean cond1 = labelValidator.validate(line[0], statement);
		boolean cond2 = operationValidator.validate(line[1], statement);
		boolean cond3 = operandValidator.validate(line[2], statement);
		boolean cond4 = true; //= commentValidator.validate(line[3], statement);
		return cond1&&cond2&&cond3&&cond4;
	}
}