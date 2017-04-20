package validators;

import operation.Operation;

public class OperationValidator implements IValidator {

	public OperationValidator() {
	}
	
	@Override
	public Boolean validate(String content, Operation operation) {
		return (operation!=null);
	}

}
