package assembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Data;
import obline.imp.EndRecord;
import obline.imp.HeaderRecord;
import obline.imp.TextRecord;
import obline.interfaces.Obline;
import statement.Directive;
import statement.IStatement;
import storage.IntermediateFileHandler;
import storage.ListingFileHandler;
import storage.ObjectCodeHandler;

public class Pass2Handler {

    // 01_ATTRIBUTES
    // *****************************************
    private Map<String, String> symbolTable;
    private ArrayList<Obline> obLines;
    private List<ArrayList<String>> intermediateFileContent;
    private List<String> intermediateFile, listingFile;
    private boolean error;

    // 02_CONTRSUCTOR
    // *****************************************
    public Pass2Handler(String intermediateFileAddress, String listingFileAddress, String objectFileAddress) {
        intermediateFile = new ArrayList<String>();
        listingFile = new ArrayList<String>();
        intermediateFileContent = new ArrayList<ArrayList<String>>();
        symbolTable = new HashMap<String, String>();
        obLines = new ArrayList<>();
        error = false;

        // 01_load file into memory
        IntermediateFileHandler.loadFile(intermediateFileAddress, symbolTable, intermediateFileContent,
                intermediateFile);
        // 02_generate listing file
        generateListingFile();
        // 03_store listing file to disk
        ListingFileHandler.storeFile(listingFile, listingFileAddress);
        if (!error) {
            ObjectCodeHandler.WriteFile(obLines, objectFileAddress);
        }
    }

    // 03_METHODS
    // *****************************************
    private void generateListingFile() {
        int ind = 0;
        int len = 0;
        StringBuilder content = new StringBuilder();
        String StartingAddress = new String();
        String endOperand = new String();
        for (String line : this.intermediateFile) {
            ind++;
            String objectCode = "";
            if (line.trim().startsWith(".")) {
                this.listingFile.add(line);
                ind--;
                continue;
            }
            System.out.println(Arrays.toString(this.intermediateFileContent.get(ind - 1).toArray()));
            if (Data.statementTable
                    .get(intermediateFileContent.get(ind - 1).get(2).toLowerCase()) instanceof Directive) {
                objectCode = generateDirectiveObjectCode(intermediateFileContent.get(ind - 1));
                if (Data.statementTable.get(intermediateFileContent.get(ind - 1).get(2).toLowerCase()).getOpName()
                        .equalsIgnoreCase("END")) {
                    endOperand = Data.symbolTable.get(intermediateFileContent.get(ind - 1).get(3));
                    if (endOperand == null) {
                        endOperand = intermediateFileContent.get(0).get(3);
                    }
                }
            } else {
                objectCode = generateInstructionObjectCode(this.intermediateFileContent.get(ind - 1));
            }
            /***********************
             * Object Line Generations
             *****************************/
            if (objectCode.equals("")) {
                if (len != 0) {
                    obLines.add(new TextRecord(StartingAddress, len, content.toString()));
                    content = new StringBuilder();
                    len = 0;
                }
            } else {
                if (len == 0) {
                    /***************
                     * I need to Get the Location Counter
                     *****************/
                    StartingAddress = intermediateFileContent.get(ind - 1).get(0);
                }
                if (len + (objectCode.length() / 2) <= 30) {
                    len += (objectCode.length() / 2);
                    content.append(objectCode);
                } else {
                    obLines.add(new TextRecord(StartingAddress, len, content.toString()));
                    len = objectCode.length() / 2;
                    StartingAddress = intermediateFileContent.get(ind - 1).get(0);
                    content = new StringBuilder();
                    content.append(objectCode);

                }
            }
            /************************
             * End of Object Code Generation
             *****************************/
            this.listingFile.add(line + "    " + objectCode);
            while (objectCode.length() > 6) {
                objectCode = objectCode.substring(6);
                line = "";
                while (line.length() < 44) {
                    line += " ";
                }
                this.listingFile.add(line + "    " + objectCode);
            }

            if (intermediateFileContent.size() == ind)
                break;
        }
        if (len != 0) {
            obLines.add(new TextRecord(StartingAddress, len, content.toString()));
        }
        /***************************
         * Adding Start and End Record
         *****************************/
        obLines.add(0,
                new HeaderRecord(intermediateFileContent.get(0).get(1), intermediateFileContent.get(0).get(0),
                        convertFromHexaToDeca(intermediateFileContent.get(intermediateFileContent.size() - 2).get(0))
                                - convertFromHexaToDeca(intermediateFileContent.get(0).get(0)) + 1));
        obLines.add(new EndRecord(endOperand));
        /*****************************************/
        // while (ind < intermediateFile.size()) {
        // this.listingFile.add(intermediateFile.get(ind++));
        // }
    }

    private String generateInstructionObjectCode(ArrayList<String> instructionContent) {
        IStatement statement = Data.statementTable.get(instructionContent.get(2).toLowerCase());
        String opCode = Integer.toHexString(Integer.parseInt(statement.getOpCode()));
        if (opCode.length() < 2)
            opCode = "0" + opCode;
        String operandAddress;
        int indOfColon = instructionContent.get(3).indexOf(',');
        boolean indexing = true;
        if (indOfColon == -1) {
            indOfColon = instructionContent.get(3).length();
            indexing = false;
        }
        operandAddress = this.symbolTable.get(instructionContent.get(3).substring(0, indOfColon).trim());
        // check max length of operand
        if (convertFromHexaToDeca(operandAddress) > Math.pow(2, 15)) {
            error = true;
            this.listingFile.add("==> Exceeding max limit for operand in 15 bits");
            return new String("");
        }
        if (operandAddress == null && statement.getNumberOfOperands() != 0) {
            operandAddress = instructionContent.get(3).trim();
            while (operandAddress.length() < 6)
                operandAddress = "0" + operandAddress;
        }
        if (statement.getNumberOfOperands() == 0) {
            operandAddress = "000000";
        }
        return opCode + concatenate(indexing ? 1 : 0, operandAddress.charAt(2)) + operandAddress.substring(3);
    }

    private String generateDirectiveObjectCode(ArrayList<String> directiveContent) {
        String result = "";
        if (directiveContent.get(2).equalsIgnoreCase("BYTE")) {
            String data = directiveContent.get(3).trim();
            if (data.charAt(0) == 'X' || data.charAt(0) == 'x') {
                if (data.charAt(1) == '\'' && data.charAt(data.length() - 1) == '\'') {
                    result = data.substring(2, directiveContent.get(3).length() - 1);
                }
            } else {
                result = getHexaFromChars(data.substring(2, data.length() - 1));
            }
        } else if (directiveContent.get(2).equalsIgnoreCase("WORD")) {
            result = getHexaFromDecimal(directiveContent.get(3));
        }
        return result;
    }

    private char concatenate(int x, char hexaDigit) {
        // if(hexaDigit == 'f' && x ==1) can not ocuur as address ae 15 bits not
        // 16 bits [x 3bit hex3 hex2 hex1]
        return (char) ((int) hexaDigit + (x * 8));
    }

    private String getHexaFromChars(String stringValue) {
        String value = "";
        for (int i = stringValue.length() - 1; i >= 0; i--) {
            value = Integer.toHexString(((int) stringValue.charAt(i))) + value;
        }
        // while(value.length()<6){
        // value = "0" + value.substring(0);
        // }
        return value;
    }

    // handling if one hexa wrong in phase1 ,, no limit on word
    private String getHexaFromDecimal(String decimalValue) {
        String value = Integer.toHexString(Integer.parseInt(decimalValue));
        while (value.length() < 6) {
            value = "0" + value.substring(0);
        }
        return value;
    }

    private int convertFromHexaToDeca(String Hexa) {
        if (Hexa == null)
            return 0;
        int num = 0;
        for (int i = Hexa.length() - 1; i >= 0; i--) {
            num += (Hexa.charAt(i) - '0') * Math.pow(16, Hexa.length() - 1 - i);
        }
        return num;
    }

}