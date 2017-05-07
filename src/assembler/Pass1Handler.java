package assembler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IntermediateFile.LineAddressGenerator;
import data.Data;
import exception.StatementException;
import parsers.LineParser;
import statement.IStatement;
import storage.FileHandler;
import storage.IntermediateFileHandler;
import storage.SrcFileHandler;
import validators.LineValidator;

public class Pass1Handler {

	private String operationsFileDirectory, directivesFileDirectory, srcFileDirectory, intermediateFileDirectory;
	private LineParser lineParser;
	private LineValidator lineValidator;
	private Map<String, IStatement> statementTable;
	private Map<String, String> symbolTable;
	private LineAddressGenerator lineAddressGenerator;

	public Pass1Handler(String operationsFileDirectory, String directivesFileDirectory, String srcFileDirectory,
			String intermediateFileDirectory) {
		this.operationsFileDirectory = operationsFileDirectory;
		this.directivesFileDirectory = directivesFileDirectory;
		this.srcFileDirectory = srcFileDirectory;
		this.intermediateFileDirectory = intermediateFileDirectory;
		fillOperationTable();
		lineParser = LineParser.getInstance();
		lineValidator = new LineValidator();
		symbolTable = new HashMap<String, String>();
		lineAddressGenerator = new LineAddressGenerator();
	}

	private void fillOperationTable() {
		statementTable = FileHandler.readFiles(operationsFileDirectory, directivesFileDirectory);
		Data.statementTable = statementTable;
	}

	public boolean ConstructSymTable() {
		Boolean firstStatement = true, endStatement = false;
		try {
			String[] srcFile = SrcFileHandler.readSrcFile(this.srcFileDirectory);
			int ind = 0;
			for (String statement : srcFile) {
				statement = statement.replaceAll("\\t", "    ");
				srcFile[ind++] = statement;
				if (statement.charAt(0) == '.') {
					lineAddressGenerator.appendComment(statement);
					continue;
				}
				String[] data = lineParser.parseLine(statement);
				if (data == null) {
					lineAddressGenerator.appendError(statement, "Invalid Instruction Format");
				} else {
					try {
						lineValidator.validateLine(data, statementTable.get(data[1].toLowerCase()));
						if (symbolTable.get(data[0]) != null)
							throw new StatementException("Duplicate Labels");
						// handling no start or no end or invalid position for
						// them
						if (data[1].equalsIgnoreCase("END")) {
							if (endStatement) {
								lineAddressGenerator.appendError(statement, "Duplicate END");
								continue;
							}
							endStatement = true;
						}
						if (firstStatement) {
							firstStatement = false;
							if (data[1].equalsIgnoreCase("start")) {
								lineAddressGenerator.setInitialAddress(data[2]);
							} else {
								lineAddressGenerator.appendError(statement, "Missing Start at first");
								continue;
							}
						} else {
							if (data[1].equalsIgnoreCase("start")) {
								lineAddressGenerator.appendError(statement, "Invalid poition for Start");
								continue;
							}
						}
						if (endStatement && !data[1].equalsIgnoreCase("END")) {
							lineAddressGenerator.appendError(statement, "Expecting no thing after END");
							continue;
						}
						if (!endStatement && statement.equals(srcFile[srcFile.length - 1])) {
							lineAddressGenerator.appendError(statement, "Missing END Statement");
							continue;
						}
						String address = lineAddressGenerator.appendStatement(statement, data,
								statementTable.get(data[1].toLowerCase()));
						if (data[0].replaceAll(" ", "").length() != 0) {
							symbolTable.put(data[0], address);
						}
					} catch (StatementException e) {
						lineAddressGenerator.appendError(statement, e.getMessage());
					}
				}
			}
			List<String> intermediateFileContent = lineAddressGenerator.getContent();
			IntermediateFileHandler.storeFile(intermediateFileContent, symbolTable, intermediateFileDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineAddressGenerator.error;
	}
}