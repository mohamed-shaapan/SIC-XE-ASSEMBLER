package validators;

import statement.IStatement;
import statement.Operation;

public class LabelValidator implements IValidator{

	public LabelValidator() {
	}
	
	@Override
	public Boolean validate(String content, IStatement operation) {
		if(operation.hasLabel()== 0 && content.replaceAll(" ", "").length()!=0)return false;
		if(operation.hasLabel()== 1 && content.replaceAll(" ", "").length()==0)return false;
		boolean cond1 = content.matches("[a-zA-Z0-9]*");
		boolean cond2 = content.substring(0, 1).matches("[a-zA-Z]");
		return cond1&&cond2;
	}
}
