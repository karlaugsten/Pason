package ai;

public class Calculator {

	private double lineweight = 3.1;
	
	public double calculateScore(int[] oldboardstate, int[] newboardstate){
		// we will store the newboard state as we will be changing it in calculateNumLines
		int[] oldnewboardstate = newboardstate.clone();
		double score = 0;
		
		score += lineweight*calculateNumLines(newboardstate);
		
		return score;
	}
	
	/**
	 * Method which will calculate the number of lines that are cleared from this board state.
	 * This method will also change the board state by erasing the cleared lines.
	 * @param newboardstate The state of the board after placing the new Tetrimino.
	 * @return The number of lines which are cleared.
	 */
	public int calculateNumLines(int[] newboardstate){
		int numlines = 0;
		int mask = 0x3FF;
		for(int i = 0; i < 20; i++){
			if((newboardstate[i] & mask) == mask){
				numlines++;
				for(int j = i-1; j >= 0; j--){
					newboardstate[j+1] = newboardstate[j];
				}
			}
		}
		return numlines;
	}
	
	public int numEdgesTouchingBlocks(int[] oldboardstate, int[] newboardstate){
		
		return 0;
	}
	
	/**
	 * Function which takes the difference between holes of the new and old board states, calculating how many holes are formed.
	 * @param oldboardstate
	 * @param newboardstate
	 * @return
	 */
	public int numHolesFormed(int[] oldboardstate, int[] newboardstate){
		int newholes = 0;
		int oldholes = 0;
		for(int i = 0; i < 10; i++){
			int mask = 1 << i;
			boolean inblocks = false;
			boolean inblocksnew = false;
			for(int j = 0; j < 20; j++){
				if((mask & oldboardstate[j]) == mask && !inblocks){
					inblocks = true;
				}
				else if((mask & oldboardstate[j]) == 0 && inblocks){
					oldholes++;
				}
				if((mask & newboardstate[j]) == mask && !inblocksnew){
					inblocksnew = true;
				}else if((mask & newboardstate[j]) == 0 && inblocksnew){
					newholes++;
				}
				
			}
		}
		return Math.max((newholes-oldholes), 0);
	}
}
