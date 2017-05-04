package obLine.obLineImp;

import obLine.obLineIF.Obline;

public class EndRecord implements Obline {

    private StringBuilder line;

    public EndRecord(String address) {
        // TODO Auto-generated constructor stub
        line = new StringBuilder();
        line.append("E^");
        buildLine(address);
    }

    private void buildLine(String address) {
        while (line.length() + address.length() < 8) {
            line.append("0");
        }
        line.append(address);
    }

    @Override
    public String getLine() {
        // TODO Auto-generated method stub
        return line.toString();
    }

}
