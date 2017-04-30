package validators;

import exception.StatementException;
import statement.IStatement;

public class CommentValidator implements IValidator{

	public CommentValidator() {

	}
	
	@Override
	public Boolean validate(String content, IStatement operation) throws StatementException {
		if(content.charAt(0) == '.' || content.isEmpty())return true;
		throw new StatementException("Invalid Comment");
	}
}
