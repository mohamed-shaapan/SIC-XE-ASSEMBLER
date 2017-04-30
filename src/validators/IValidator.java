package validators;

import exception.StatementException;
import statement.IStatement;
import statement.Operation;

public interface IValidator {
	Boolean validate(String content, IStatement operation)throws StatementException;
}
