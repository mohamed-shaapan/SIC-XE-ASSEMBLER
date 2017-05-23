package main;

import java.io.File;
import java.util.Scanner;

import assembler.Pass1Handler;
import assembler.Pass2Handler;
import exception.StatementException;

public class Main {
	public static void main(String[] args) {
		while(true){
			try {
				Scanner scanner = new Scanner(System.in);
				String srcFilePath = scanner.nextLine();
				File srcFile = new File(srcFilePath);
				if(!srcFile.exists()){
					throw new StatementException("src file is not exist");
				}
				String path = new File("extra files").getAbsolutePath();
				String[] loc = new String[6];
				loc[2] = srcFilePath;
				loc[0] = path + File.separator + "Operations.txt";
				loc[1] = path + File.separator + "Directives.txt"; 
				loc[3] = loc[2].substring(0, loc[2].length() - 4) + "intermediate.txt";
				loc[4] = loc[2].substring(0, loc[2].length() - 4) + "listing.txt";
				loc[5] = loc[2].substring(0, loc[2].length() - 4) + "object.txt";
				Pass1Handler obj1 = new Pass1Handler(loc[0], loc[1], loc[2], loc[3]);
				boolean pass1Error = obj1.runPass1();
				if (!pass1Error) {
					System.out.println("Successfully path1 completed");
					Pass2Handler obj2 = new Pass2Handler(loc[3], loc[4], loc[5]);
				}
				else{
					throw new StatementException("src File is not valid so intermediate file contains error messeges");
				}
			} catch (Exception e) {
				if(e instanceof StatementException){
					System.out.println(e.getMessage());
				}
			}
		}
	}
}