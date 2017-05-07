package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import assembler.Pass1Handler;
import assembler.Pass2Handler;

public class Main {
	public static void main(String[] args) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(new File("config.txt")));
			String[] loc = new String[6];
			String path = new File("extra files").getAbsolutePath();
			loc[0] = path + File.separator + "Operations.txt";
			loc[1] = path + File.separator + "Directives.txt";
			loc[2] = bufferedReader.readLine();
			loc[3] = loc[2].substring(0, loc[2].length() - 4) + "intermediate.txt";
			loc[4] = loc[2].substring(0, loc[2].length() - 4) + "listing.txt";
			loc[5] = loc[2].substring(0, loc[2].length() - 4) + "object.txt";
			Pass1Handler obj1 = new Pass1Handler(loc[0], loc[1], loc[2], loc[3]);
			boolean pass1Error = obj1.ConstructSymTable();
			if (!pass1Error) {
				Pass2Handler obj2 = new Pass2Handler(loc[3], loc[4], loc[5]);
			}
		} catch (Exception e) {
			// configuration file is not exitsing
		}
	}
}