package data;

import java.util.HashMap;
import java.util.Map;

import statement.IStatement;
import elements.Literal;

public class Data {
	public static Map<String, IStatement> statementTable = new HashMap<String, IStatement>();
	public static Map<String, String> symbolTable = new HashMap<String, String>();
	public static Map<String, Literal> literalTable = new HashMap<String, Literal>();
}
