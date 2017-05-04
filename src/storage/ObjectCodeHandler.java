package storage;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import obline.interfaces.Obline;

public class ObjectCodeHandler {
    public static void WriteFile(ArrayList<Obline> lines, String fileDirectory) {
        try {
            PrintWriter printWriter = new PrintWriter(new File(fileDirectory));
            for (Obline line : lines) {
                printWriter.println(line.getLine());
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
