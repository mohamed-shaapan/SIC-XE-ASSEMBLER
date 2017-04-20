package validators;

import operation.Operation;

public class CommentValidator implements IValidator{

	public CommentValidator() {

	}
	
	@Override
	public Boolean validate(String content, Operation operation) {
		return (content.charAt(0) == '.');
	}

}
