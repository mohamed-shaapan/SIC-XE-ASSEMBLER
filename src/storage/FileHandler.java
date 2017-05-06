package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import statement.Directive;
import statement.IStatement;
import statement.Operation;

public class FileHandler {
	
	public static Map<String, IStatement> readFiles(String operationsFileDirectory, String directivesFileDirectory) {
		Map<String, IStatement> hashTable = new HashMap<String, IStatement>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(operationsFileDirectory)));
			while (bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				String tmp[] = line.split(new String(" "));
				String name = tmp[0];
				String opCode = tmp[1];
				int numberOfOperands = Integer.valueOf(tmp[2]);
				IStatement operation = new Operation(name, opCode, numberOfOperands);
				hashTable.put(operation.getOpName(), operation);
			}
			bufferedReader.close();
		} catch (Exception e) {
		}
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(directivesFileDirectory)));
			while (bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				String tmp[] = line.split(new String(" "));
				String name = tmp[0];
				int numberOfOperands = Integer.valueOf(tmp[1]);
				int label = Integer.valueOf(tmp[2]);
				IStatement directive = new Directive(name, numberOfOperands,label);
				hashTable.put(directive.getOpName(), directive);
			}
			bufferedReader.close();
		} catch (Exception e) {
		}
		return hashTable;
	}
}