package validators;

import exception.StatementException;
import statement.IStatement;

public class CommentValidator implements IValidator{

	public CommentValidator() {

	}
	
	@Override
	public Boolean validate(String content, IStatement operation) throws StatementException {
		return true;
	}
}
