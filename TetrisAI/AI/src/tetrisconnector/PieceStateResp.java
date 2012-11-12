package tetrisconnector;

import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONObject;

public class PieceStateResp extends Response {

	private String gamename;
	private PieceState opponentstate;
	private PieceState state;
	private String[] piecequeue;
	
	
	public PieceStateResp(JSONObject j){
		
		super(Integer.parseInt(j.getString("sequence")),(double)j.get("timestamp"),j.getString("comm_type"), j);
		this.setPiecequeue(j.getJSONArray("queue"));
		JSONObject jsub = j.getJSONObject("states");
		try{
			this.setState(new PieceState(jsub.getJSONObject("Team 112")));
		}catch(NoSuchElementException e){
			this.setState(new PieceState());
		}
		//TODO: change here aswell
		try{
			this.setOpponentstate(new PieceState(jsub.getJSONObject("Test Client")));	
		}catch(NoSuchElementException e){
			this.setOpponentstate(new PieceState());
		}
	}

	public String getGamename() {
		return gamename;
	}


	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public void setOpponentstate(PieceState opponentstate) {
		this.opponentstate = opponentstate;
	}
	
	public String getPiece(){
		return state.getPiece();
	}
	
	public int getPieceRow(){
		return state.getRow();
	}
	
	public int getPieceColumn(){
		return state.getColumn();
	}
	
	public int getPieceOrient(){
		return state.getOrient();
	}

	public void setState(PieceState state) {
		this.state = state;
	}
	
	public String getOppPiece(){
		return opponentstate.getPiece();
	}
	
	public int getOppPieceRow(){
		return opponentstate.getRow();
	}
	
	public int getOppPieceColumn(){
		return opponentstate.getColumn();
	}
	
	public int getOppPieceOrient(){
		return opponentstate.getOrient();
	}

	public String[] getPiecequeue() {
		return piecequeue;
	}

	private void setPiecequeue(JSONArray queue) {
		String[] ret = new String[queue.length()];
		for(int i = 0; i < queue.length(); i++){
			ret[i] = queue.getString(i);
		}
		this.piecequeue = ret;
	}


	class PieceState{
		
		private String piece = null;
		private int orient = 0;
		private int number = 0;
		private int row = 0;
		private int column = 0;
		
		public PieceState(JSONObject st){
			this.piece = st.getString("piece");
			this.orient = st.getInt("orient");
			this.number = st.getInt("number");
			this.row = st.getInt("row");
			this.column = st.getInt("col");
		}
		
		public PieceState(){
			//constructor for empty piece (no piece)
		}
		
		public String getPiece(){
			return piece;
		}
		
		public int getOrient(){
			return orient;
		}
		
		public int getRow(){
			return row;
		}
		
		public int getColumn(){
			return column;
		}
		
	}
	
}
