package storage;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import interfaces.ObjectCodeHandlerIF;
import obLine.obLineIF.Obline;
import obLine.obLineImp.EndRecord;
import obLine.obLineImp.HeaderRecord;
import obLine.obLineImp.TextRecord;

public class ObjectCodeHandler implements ObjectCodeHandlerIF {

    private ArrayList<Obline> obLines;

    private ArrayList<ArrayList<String>> table;

    public ObjectCodeHandler(ArrayList<ArrayList<String>> table) {
        // TODO Auto-generated constructor stub
        this.table = table;
        generateObjectCode();
    }

    private void generateObjectCode() {
        int len = 0;
        StringBuilder content = new StringBuilder();
        int totalSize = 0;
        String startingAddress = new String();
        for (int i = 1; i < table.size() - 1; i++) {
            ArrayList<String> line = table.get(i);
            if (line.get(1).equals("")) {
                if (len == 0) {
                    continue;
                } else {
                    obLines.add(new TextRecord(startingAddress, len, content.toString()));
                    totalSize += len;
                    len = 0;
                    content = new StringBuilder();
                }
            }
            if (len == 0) {
                startingAddress = line.get(0);
            }
            if (len + line.get(1).length() <= 60) {
                len += line.get(1).length();
                content.append(line.get(1));
            } else {
                obLines.add(new TextRecord(startingAddress, len, content.toString()));
                totalSize += len;
                len = 0;
                content = new StringBuilder();
            }
        }
        totalSize += 9;
        obLines.add(0, new HeaderRecord(table.get(0).get(1), table.get(0).get(0), totalSize));
        obLines.add(new EndRecord(table.get(0).get(0)));

    }

    /** Write the Object code in a file */
    public void writeFile(String fileDirectory) {
        try {
            PrintWriter printWriter = new PrintWriter(new File(fileDirectory));
            for (Obline line : obLines) {
                printWriter.println(line.getLine());
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
