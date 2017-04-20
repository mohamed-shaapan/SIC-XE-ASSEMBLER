package validators;

import statement.IStatement;

public class OperandValidator implements IValidator{

	public OperandValidator() {

	}
	
	@Override
	public Boolean validate(String content, IStatement operation) {
		return true;
	}
}
