package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import operation.Operation;

public class FileHandler {
	public static Map<String, Operation> readFile(String fileDirectory) {
		Map<String, Operation> hashTable = new HashMap<String, Operation>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileDirectory)));
			while (bufferedReader.ready()) {
				String line = bufferedReader.readLine();
				String tmp[] = line.split(new String(" "));
				String name = tmp[0];
				String opCode = tmp[1];
				int numberOfOperands = Integer.valueOf(tmp[2]);
				Operation operation = new Operation(name, opCode, numberOfOperands);
				hashTable.put(operation.getOpName(), operation);
				//operation.print();
			}
			bufferedReader.close();
		} catch (Exception e) {
		}
		return hashTable;
	}
}