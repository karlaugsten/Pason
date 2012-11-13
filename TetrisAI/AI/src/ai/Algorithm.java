package ai;

import integration.Communicator;

public class Algorithm 
{
	private Communicator c;
	private Calculator calculator;

	/**
	 * Create a new Algorithm Class
	 * Pass in Communicator object
	 * @param c
	 */
	public Algorithm(Communicator c)
	{
			calculator = new Calculator();
			this.c = c;
	}
	
	public void start()
	{
		String Tetrimino = null;
		while(Tetrimino == null){
			Tetrimino = c.getPiece();
		}
		
		Tetrimino current = getTetrimino(Tetrimino, c.getPieceOrient(), c.getPieceRow(), c.getPieceColumn());
		int[] boardstate = c.getBoardState();
		GetBestPosition(current, boardstate);
	}
	
	/**
	 * A function which iterates all possible positions of the tetrimino and calculates which position will give the best
	 * score according to the calculator.
	 * @param current The current Tetrimino to try to place.
	 * @param boardstate The current state of the Tetris board
	 * @return The new state of the tetris board after placing the Tetrimino.
	 */
	public int[] GetBestPosition(Tetrimino current, int[] boardstate)
	{
		double bestscore = -Double.MAX_VALUE;
		int bestcol = 0;
		int orientation = 0;
		int[] newboardstate = null;
		for(int orient = 0; orient <= current.possibleorientations; orient++){
			for(int column = current.getLeftMost(); column <= current.getRightMost(); column++){
				newboardstate = current.dropTetrimino(column, boardstate);
				double score = calculator.calculateScore(boardstate, newboardstate);
				if(score > bestscore){
					bestscore = score;
					orientation = orient;
					bestcol = column;
				}
			}
		}
		return newboardstate;
	}
	
	
	public Tetrimino getTetrimino(String tet, int orient, int row, int col){
		char t = tet.charAt(0);
		Tetrimino current = null;
		switch (t) {
			case 'J': 	current = new J();
						break;
			case 'I':	current = new I(orient, col, row);
						break;
			case 'O':	current = new O(orient, col, row);
						break;
			case 'S':	current = new S(orient, col, row);
						break;
			case 'Z':	current = new Z();
						break;
			case 'L':	current = new L();
						break;
			case 'T':	current = new T();
						break;
			default:	System.out.println("ERROR: ALGORITHM could not detect tetrimino string");
						break;
		}
		return current;
	}
	
}
