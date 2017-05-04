package main;

import assembler.Pass1Handler;
import assembler.Pass2Handler;

public class Main {

	/*--org
	--equate
	--ltorg
	handling devices
	*/
	
	public static void main(String[] args) {
		String[] loc = {
						".\\extra files\\Test\\operation code.txt",
						".\\extra files\\Test\\directive operands.txt",
						".\\extra files\\Test\\input2.txt",
						".\\extra files\\Test\\intermediate.txt",
						".\\extra files\\Test\\listing.txt"
						};
		Pass1Handler obj1 = new Pass1Handler(loc[0],loc[1],loc[2],loc[3]);
		obj1.ConstructSymTable();
		Pass2Handler obj2 = new Pass2Handler(loc[3],loc[4]);
	}
}