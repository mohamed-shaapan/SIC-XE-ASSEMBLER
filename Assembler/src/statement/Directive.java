package statement;

public class Directive implements IStatement{
	private String dirName;
	private int numberOfOperands;
	
	public Directive(String opName,int numberOfoperands){
		this.dirName=opName;
		this.numberOfOperands=numberOfoperands;
	}
	
	@Override
	public String getOpName() {
		return null;
	}

	@Override
	public int getNumberOfOperands() {
		return 0;
	}
	

	@Override
	public String getOpCode() {
		return null;
	}
	
	@Override
	public int getFormatType() {
		return 0;
	}

	@Override
	public int hasLabel() {
		// TODO Auto-generated method stub
		return 0;
	}
}
