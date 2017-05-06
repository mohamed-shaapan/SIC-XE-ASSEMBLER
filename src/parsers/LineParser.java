package parsers;

import util.Pair;

public class LineParser {

    private static LineParser parserObj;
    private static final int numberOfParameters = 4;
    private static final Pair[] places = new Pair[numberOfParameters+1];

    private LineParser() {
        places[0] = new Pair(0, 8);
        places[1] = new Pair(9, 15);
        places[2] = new Pair(17, 35);
        places[3] = new Pair(35, 66);
        places[4] = new Pair(15, 17);
    }

    public String[] parseLine(String line) {
        if (validateLineFormat(line)) {
            return extractParameters(line);
        }
        return null;
    }

    // validate line format length range only
    private boolean validateLineFormat(String line) {
        while (line.length() < 66) {
            line += " ";
        }
        boolean cond1 = (line.length() < 67);
        boolean cond2 = (line.substring(places[0].getFirst(), places[0].getSecond()).split(" +").length <= 1);
        boolean cond3 = (line.substring(places[1].getFirst(), places[1].getSecond()).split(" +").length == 1);
        boolean cond4 = (line.substring(places[2].getFirst(), places[2].getSecond()).split(" +").length <= 1);
        boolean cond5 = (line.charAt(8) == ' ');
        boolean cond6 = line.substring(places[4].getFirst(), places[4].getSecond()).equals("  ");
        return cond1 && cond2 && cond3 && cond4 && cond5 && cond6;
    }

    // extract line content [label,operation name,operands,comment]
    private String[] extractParameters(String line) {
        while (line.length() < 66) {
            line += " ";
        }
        String[] parameters = new String[numberOfParameters];
        for (int i = 0; i < numberOfParameters; i++) {
            parameters[i] = line.substring(places[i].getFirst(), places[i].getSecond());
            parameters[i] = parameters[i].trim();
        }
        return parameters;
    }

    // signelton
    public static LineParser getInstance() {
        if (parserObj == null) {
            parserObj = new LineParser();
        }
        return parserObj;
    }
}