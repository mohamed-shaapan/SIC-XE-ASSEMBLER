package validators;

public class RegisterValidator {

    private static final String[] registers = new String[] {"A", "L", "B", "S", "T", "F"};

    private RegisterValidator() {

    }

    public static boolean isRegister(String content) {
        for (String str : registers) {
            if (str.equalsIgnoreCase(content)) {
                return true;
            }
        }
        return false;
    }

}
