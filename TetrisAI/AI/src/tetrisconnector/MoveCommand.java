package tetrisconnector;

import org.json.JSONObject;

public class MoveCommand implements Command {

	private JSONObject command = new JSONObject();
	private String message;
	private byte[] output;
	
	public MoveCommand(String move, String token){
		command.put("comm_type", "GameMove");
		command.put("client_token", token);
		command.put("move", move);
		message = command.toString();
		output = message.getBytes();
	}
	
	@Override
	public String getMessage() {
		
		return message;
	}

	@Override
	public byte[] getBytes() {
		
		return output;
	}

}
