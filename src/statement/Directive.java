package statement;

public class Directive implements IStatement {
	private String dirName;
	private int numberOfOperands, label;

	public Directive(String opName, int numberOfoperands, int label) {
		this.dirName = opName;
		this.numberOfOperands = numberOfoperands;
		this.label = label;
	}

	@Override
	public String getOpName() {
		return dirName;
	}

	@Override
	public int getNumberOfOperands() {
		return numberOfOperands;
	}

	@Override
	public String getOpCode() {
		return null;
	}

	@Override
	public int hasLabel() {
		return label;
	}
}
