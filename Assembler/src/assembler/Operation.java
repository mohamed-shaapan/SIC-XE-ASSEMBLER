package assembler;

public class Operation {
	private String opName;
	private String opCode;
	private int numberOfOperands;
	private int formatType;
	
	// 0 no operands , 1 opernd memory , 2 operand register , 3 two operand register
	public Operation(String opName, String opCode, int numberOfoperands){
		this.opName=opName;
		this.opCode=opCode;
		this.numberOfOperands=numberOfoperands;
		if(opName.charAt(0) == '+'){
			this.formatType = 4;
		}
		else if(numberOfoperands == 1){
			this.formatType = 3;
		}
		else{
			this.formatType = Math.min(numberOfoperands, 1)+1;
		}
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
	
	public int getFormatType() {
		return formatType;
	}
	
	public void print(){
		System.out.println(opName+"\t"+opCode+"\t"+numberOfOperands);
		System.out.println("-------------------");
	}
}
