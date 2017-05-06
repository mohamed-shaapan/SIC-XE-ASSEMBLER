package validators;

import exception.StatementException;
import statement.IStatement;

public class LabelValidator implements IValidator{

	public LabelValidator() {
	}
	
	@Override
	public Boolean validate(String content, IStatement operation) throws StatementException {
		while(content.length()<8)content+=" ";
		if(operation.hasLabel()==1){
			return checkSyntax(content);
		}
		if(operation.hashCode()==0){
			return checkEmpty(content);
		}
		else{
			try{
				return checkEmpty(content);
			}catch(StatementException e){
				return checkSyntax(content);
			}
		}
	}
	
	private boolean checkSyntax(String content) throws StatementException{
		int ind = Math.max(content.indexOf(" "),0);
		boolean cond1 = content.substring(0,ind).matches("[a-zA-Z0-9]*");
		boolean cond2 = content.substring(0, 1).matches("[a-zA-Z]");
		if(cond1&&cond2){
			return true;
		}
		throw new StatementException("Invalid Label Name");
	}
	
	private boolean checkEmpty(String content) throws StatementException{
		boolean cond1 = content.replaceAll("\\s+", "").isEmpty();
		if(cond1){
			return true;
		}
		throw new StatementException("Invalid Label must be blank");
	}
}
