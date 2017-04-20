package validators;

import statement.IStatement;

public class OperationValidator implements IValidator {

	public OperationValidator() {
	}
	
	@Override
	public Boolean validate(String content, IStatement operation) {
		return (operation!=null);
	}
}
