package obline.imp;

import obline.interfaces.Obline;

public class HeaderRecord implements Obline {
    private StringBuilder line;

    public HeaderRecord(String name, String address, Integer size) {
        // TODO Auto-generated constructor stub
        line = new StringBuilder();
        line.append("H^");
        buildLine(name, address, size);

    }

    private void buildLine(String name, String address, Integer size) {
        line.append(name);
        while (line.length() < 8) {
            line.append(" ");
        }
        line.append("^");
        while (line.length() + address.length() < 15) {
            line.append("0");
        }
        line.append(address + "^");
        String HexSize = Integer.toHexString(size);
        while (line.length() + HexSize.length() < 22) {
            line.append("0");
        }
        line.append(HexSize);
    }

    @Override
    public String getLine() {
        // TODO Auto-generated method stub
        return line.toString();
    }
}
