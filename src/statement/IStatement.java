package statement;

public interface IStatement {
	public String getOpName();
	public String getOpCode();
	public int getNumberOfOperands();
	public int getFormatType();
	public int hasLabel();
}
