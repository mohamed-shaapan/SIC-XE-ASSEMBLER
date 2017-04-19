package assembler;

public class LabelValidator implements IValidator{

	public LabelValidator() {
	}
	
	@Override
	public Boolean validate(String content, Operation operation) {
		boolean cond1 = content.matches("[a-zA-Z0-9]*");
		boolean cond2 = content.substring(0, 1).matches("[a-zA-Z]");
		return cond1&&cond2;
	}
}
