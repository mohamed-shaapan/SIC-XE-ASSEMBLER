package tools;

import java.io.StreamTokenizer;

public class Checker {
	
	public static boolean checkName(String content) {
        int i = 0;
        if (inBetween(content, 0, '0', '9'))
            return false;
        while (i < content.length()) {
            if (content.charAt(i) == ',')
                break;
            i++;
        }
        if (i + 2 == content.length()) {
            return (content.charAt(i + 1) == 'X' || content.charAt(i + 1) == 'x');
        }
        if (i == content.length())
            return true;

        return false;
    }

	public static boolean checkDecimalNumber(String content) {
        content = content.trim();
    	for (int i = 0; i < content.length(); i++) {
            if (inBetween(content, i, '0', '9'))
                continue;
            return false;
        }
        return true;
    }

	public static boolean checkHexaNumber(String content) {
        content = content.trim();
		if(content.isEmpty())return false;
    	for (int i = 0; i < content.length(); i++) {
            if (inBetween(content, i, '0', '9'))
                continue;
            if (inBetween(content, i, 'A', 'F'))
                continue;
            if (inBetween(content, i, 'a', 'f'))
                continue;
            return false;
        }
        return true;
    }

	public static boolean checkStar(String content) {
        content = content.trim();
		if(content.trim().isEmpty())return false;
        return content.equals(new String("*")) || content.equalsIgnoreCase(new String("*,x"));
    }

	
	public static boolean checkHexaAddress(String content) {
        content = content.trim();
		if(content.trim().isEmpty() || content.trim().length()<5)return false;
        boolean flag = false;
        if (content.charAt(0) == '0' && content.charAt(1) == 'X')
            if (content.charAt(2) == '\'' && content.charAt(content.length() - 1) == '\'')
            	flag =  checkHexaNumber(content.substring(3,content.length()-1));
        return flag; 
    }

	
	public static boolean inBetween(String content, int index, char start, char end) {
        return content.isEmpty() || (content.charAt(index) >= start && content.charAt(index) <= end);
    }
	
	public static boolean checkChar(String content) {
		content = content.trim();
        for (int i = 0; i < content.length(); i++) {
            if (inBetween(content, i, 'a', 'z') || inBetween(content, i, 'A', 'Z'))
                continue;
            return false;
        }
        return true;
    }
	
	public static String getHexaFromDecimal(String decimalValue) {
		String value = Integer.toHexString(Integer.parseInt(decimalValue));
		while (value.length() < 6) {
			value = "0" + value.substring(0);
		}
		return value;
	}

	public static int convertFromHexaToDeca(String Hexa) {
		if (Hexa == null)
			return 0;
		int num = 0;
		for (int i = Hexa.length() - 1; i >= 0; i--) {
			num += (Hexa.charAt(i) - '0') * Math.pow(16, Hexa.length() - 1 - i);
		}
		return num;
	}
}
