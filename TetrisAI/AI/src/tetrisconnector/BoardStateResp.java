package tetrisconnector;

import org.json.JSONArray;
import org.json.JSONObject;

public class BoardStateResp extends Response {
	
	private String gamename;
	private State opponentstate;
	private State state;
	
	
	public BoardStateResp(JSONObject j){
		
		super(Integer.parseInt(j.getString("sequence")),(double)j.get("timestamp"),j.getString("comm_type"), j);
		JSONObject jsub = j.getJSONObject("states");
		if(jsub.getJSONObject("Team 112") != null)
		{
			setState(new State(jsub.getJSONObject("Team 112")));
			//TODO: This will have to change! if we arent playing against test client
			setOpponentstate(new State(jsub.getJSONObject("Test Client")));
		}
		else
		{
			System.out.println("ERROR: DID NOT RETRIEVE STATE");
		}
	}
	

	public State getOpponentstate() {
		return opponentstate;
	}


	public void setOpponentstate(State opponentstate) {
		this.opponentstate = opponentstate;
	}


	public String getBoardState() {
		return state.getBoardstate();
	}
	
	public String getOppBoardState() {
		return opponentstate.getBoardstate();
	}

	public void setState(State state) {
		this.state = state;
	}


	public String getGamename() {
		return gamename;
	}


	public void setGamename(String gamename) {
		this.gamename = gamename;
	}


	class State{
		
		private String boardstate;
		private int piecenum;
		private int[] clearedrows;
		
		public State(JSONObject st){
			setBoardstate(st.getString("board_state"));
			setPiecenum(Integer.parseInt(st.getString("piece_number")));
			setClearedrows(st.getJSONArray("cleared_rows"));
			
		}

		public String getBoardstate() {
			return boardstate;
		}

		public void setBoardstate(String boardstate) {
			this.boardstate = boardstate;
		}

		public int getPiecenum() {
			return piecenum;
		}

		public void setPiecenum(int piecenum) {
			this.piecenum = piecenum;
		}

		public int[] getClearedrows() {
			return clearedrows;
		}

		public void setClearedrows(JSONArray cleared) {
			int[] ret = new int[cleared.length()];
			for(int i = 0; i < cleared.length(); i++){
				ret[i] = cleared.getInt(i);
			}
			this.clearedrows = ret;
		}
		
	}
}
