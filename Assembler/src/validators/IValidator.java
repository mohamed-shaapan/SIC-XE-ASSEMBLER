package validators;

import operation.Operation;

public interface IValidator {
	Boolean validate(String content, Operation operation);
}
