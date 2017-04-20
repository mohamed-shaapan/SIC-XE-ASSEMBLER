package main;

import java.awt.PageAttributes;

import assembler.Pass1Handler;

public class Main {

	/*--org
	--equate
	--ltorg
	
	*/
	
	public static void main(String[] args) {
		String[] loc = {
						"C:\\Users\\amrmh_000\\Desktop\\Test\\operation code.txt",
						"C:\\Users\\amrmh_000\\Desktop\\Test\\directive operands.txt",
						"C:\\Users\\amrmh_000\\Desktop\\Test\\input.txt",
						"C:\\Users\\amrmh_000\\Desktop\\Test\\output.txt"
						};
		Pass1Handler obj = new Pass1Handler(loc[0],loc[1],loc[2],loc[3]);
		obj.ConstructSymTable();
	}

}
