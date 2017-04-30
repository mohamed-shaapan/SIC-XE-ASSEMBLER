package main;

import java.awt.PageAttributes;

import assembler.Pass1Handler;

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
						".\\extra files\\Test\\input.txt",
						".\\extra files\\Test\\output.txt"
						};
		Pass1Handler obj = new Pass1Handler(loc[0],loc[1],loc[2],loc[3]);
		obj.ConstructSymTable();
	}

}
