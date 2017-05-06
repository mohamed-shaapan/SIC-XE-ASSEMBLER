package printer;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Printer {
			
	public static void printTable(ArrayList<ArrayList<String>> data, String tableName ,PrintWriter printWriter) {
		if (tableName == null)
			return;
		printWriter.println(tableName);
		ArrayList<Integer> width = findMaxWidth(data);
		for (int i = 0; i < data.size(); i++) {
			printLine(width,printWriter);
			printWriter.print("| ");
			printRow(width, data.get(i), data.get(0).size(),printWriter);
		}
		printLine(width,printWriter);
		printWriter.println();
	}

	private static ArrayList<Integer> findMaxWidth(ArrayList<ArrayList<String>> data) {
		ArrayList<Integer> width = new ArrayList<Integer>();
		int max = 0;
		for (int i = 0; i < data.get(0).size(); i++) {
			for (int j = 0; j < data.size(); j++) {
				if (i < data.get(j).size() && data.get(j).get(i) != null && max < data.get(j).get(i).length()) {
					max = data.get(j).get(i).length();
				}
			}
			width.add(max + 2);
			max = 0;
		}
		return width;
	}

	private static void printLine(ArrayList<Integer> width, PrintWriter printWriter) {
		printWriter.print("+");
		for (int i = 0; i < width.size(); i++) {
			for (int j = 0; j < width.get(i); j++) {
				printWriter.print("-");
			}
			printWriter.print("+");
		}
		printWriter.println();
	}

	private static void printRow(ArrayList<Integer> width, ArrayList<String> row, int columnNumber, PrintWriter printWriter) {
		for (int i = 0; i < columnNumber; i++) {
			if (i < row.size()) {
				printWriter.print(row.get(i));
				for (int j = 0; j < width.get(i) - row.get(i).length() - 1; j++) {
					printWriter.print(" ");
				}
				printWriter.print("| ");
			} else {
				for (int j = 0; j < width.get(i) - 1; j++) {
					printWriter.print(" ");
				}
				printWriter.print("| ");
			}
		}
		printWriter.println();
	}
}
