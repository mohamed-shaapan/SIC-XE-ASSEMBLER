package statement;

public class Operation implements IStatement{
	private String opName;
	private String opCode;
	private int numberOfOperands;
	
	// 0 no operands , 1 opernd memory , 2 operand register , 3 two operand register
	public Operation(String opName, String opCode, int numberOfoperands){
		this.opName=opName;
		this.opCode=opCode;
		this.numberOfOperands=numberOfoperands;
	}

	public String getOpName() {
		return opName;
	}

	public String getOpCode() {
		return opCode;
	}

	public int getNumberOfOperands() {
		return numberOfOperands;
	}
	
	public void print(){
		System.out.println(opName+"\t"+opCode+"\t"+numberOfOperands);
		System.out.println("-------------------");
	}

	@Override
	public int hasLabel() {
		return 2;
	}
}
