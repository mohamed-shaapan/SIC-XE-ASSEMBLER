package validators;

import statement.IStatement;

public class CommentValidator implements IValidator{

	public CommentValidator() {

	}
	
	@Override
	public Boolean validate(String content, IStatement operation) {
		return (content.charAt(0) == '.' || content.isEmpty());
	}
}
