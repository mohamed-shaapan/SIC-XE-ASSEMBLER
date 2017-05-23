package elements;

public class ORGHandler {
	
	private static int originalProgramLocation=-1;
	
	
	public static boolean validateORGStatement(String content){
		content = content.trim();
		
		//01_no operand
		if(content.equalsIgnoreCase("")){
			return true;
		}
		return EquateHandler.validateEquateOperands(content);
		
	}
	
	
	public static int execute(String operand, int currentLocation){
		
		if(operand.trim().isEmpty()){
			if(originalProgramLocation==-1){
				return currentLocation;
			}
			return originalProgramLocation;
		}else{
			originalProgramLocation=currentLocation;
			return Integer.parseInt(EquateHandler.getAddressValue(operand, currentLocation));
		}
	}
	
	
	
}
