package validators;

import exception.StatementException;
import statement.IStatement;

public interface IValidator {
	Boolean validate(String content, IStatement operation) throws StatementException;
}
