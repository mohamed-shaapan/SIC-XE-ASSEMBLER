package obline.imp;

import obline.interfaces.Obline;

public class TextRecord implements Obline {
	private StringBuilder line;

	public TextRecord(String address, Integer size, String content) {
		// TODO Auto-generated constructor stub
		line = new StringBuilder();
		line.append("T^");
		buildLine(address, size, content);
	}

	private void buildLine(String address, Integer size, String content) {
		while (line.length() + address.length() < 8) {
			line.append("0");
		}
		line.append(address + "^");
		String HexSize = Integer.toHexString(size);
		while (line.length() + HexSize.length() < 11) {
			line.append("0");
		}
		line.append(HexSize + "^");
		line.append(content);
	}

	@Override
	public String getLine() {
		// TODO Auto-generated method stub
		return line.toString();
	}
}
