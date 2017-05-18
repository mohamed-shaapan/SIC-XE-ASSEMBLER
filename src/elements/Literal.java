package elements;

public class Literal {

	//01_ATTRIBUTES
	//**************************************
	private String name;
	private String value;
	private String address;
	private int length;
	
	
	//02_CONSTRUCTOR
	//**************************************
	public Literal(String name, String value, String address, int length){
		this.name=name;
		this.value=value;
		this.address=address;
		this.length=length;
	}
	
	public Literal(String literal, String address){
		this.address=address;
		this.name=literal;
	}


	//03_SETTERS & GETTERS
	//**************************************
	public String getName() {
		return name;
	}


	public String getValue() {
		return value;
	}


	public String getAddress() {
		return address;
	}

	public int getLength() {
		return length;
	}
	
	public String generateStatement(){
		String statement=name;
		while(statement.length()!=)
		return statement;
	}

	
	
	
	
}
