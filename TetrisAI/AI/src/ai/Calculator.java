package ai;

public class Calculator {

	private double lineweight = 3.1;
	private double walledgeweight = 2.1;
	private double groundedgeweight = 1.5;
	private double holeweight = -2.0;
	private double stepweight = 0.5;
	private double heightweight = -3.5;
	private int LER = 0;
	
	public double calculateScore(int[] oldboardstate, int[] newboardstate){
		// we will store the newboard state as we will be changing it in calculateNumLines
		int[] oldnewboardstate = newboardstate.clone();
		double score = 0;
		findLER(newboardstate);
		score += walledgeweight*numEdgesTouchingWall(oldboardstate, newboardstate);
		score += groundedgeweight*numEdgesTouchingGround(oldboardstate, newboardstate);
		score += lineweight*calculateNumLines(newboardstate);
		findLER(newboardstate);
		score += holeweight*numHolesFormed(oldboardstate, newboardstate);
		score += stepweight*numSteps(newboardstate);
		score += heightweight*heightPenalty(oldboardstate, newboardstate);
		return score;
	}
	
	/**
	 * Function which will find the lowest empty row.
	 * @param boardstate The boardstate for which to find the lowest empty row.
	 */
	private void findLER(int[] boardstate){
		int mask = 0xFFFF;
		for(int i = 0; i < 20; i++){
			if((boardstate[i] & mask) != 0){
				LER = i-1;
				return;
			}
		}
	}
	
	/**
	 * Method which will calculate the number of lines that are cleared from this board state.
	 * This method will also change the board state by erasing the cleared lines.
	 * @param newboardstate The state of the board after placing the new Tetrimino.
	 * @return The number of lines which are cleared.
	 */
	private int calculateNumLines(int[] newboardstate){
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
	
	private int numEdgesTouchingBlocks(int[] oldboardstate, int[] newboardstate){
		
		return 0;
	}
	
	/**
	 * Method which calculates the number of edges touching the ground of the current Tetrimino
	 * @param oldboardstate The old state of the board before dropping tetrimino.
	 * @param newboardstate The new board state right after dropping the tetrimino.
	 * @return Number of edges of the tetrimino touching the ground.
	 */
	private int numEdgesTouchingGround(int[] oldboardstate, int[] newboardstate){
		int numedges = 0;
		
		for(int i = 0; i < 10; i++){
			int mask = 1 << i;
			if((newboardstate[19] & mask) == mask && (oldboardstate[19] & mask) != mask){
				numedges++;
			}
		}
		
		return numedges;
	}
	
	private int numEdgesTouchingWall(int[] oldboardstate, int[] newboardstate){
		int numedges = 0;
		int maskright = 1 << 9;
		int maskleft = 1;
		for(int i = LER; i < 20; i++){
			if(((((newboardstate[i] & maskright) == maskright) && ((oldboardstate[i] & maskright) != maskright)) || 
					(((newboardstate[i] & maskleft) == maskleft) && (oldboardstate[i] & maskleft) != maskleft))){
				numedges++;
			}
		}
		return numedges;
		
	}
	
	/**
	 * Function which takes the difference between holes of the new and old board states, calculating how many holes are formed.
	 * @param oldboardstate
	 * @param newboardstate
	 * @return
	 */
	private int numHolesFormed(int[] oldboardstate, int[] newboardstate){
		int newholes = 0;
		int oldholes = 0;
		for(int i = 0; i < 10; i++){
			int mask = 1 << i;
			boolean inblocks = false;
			boolean inblocksnew = false;
			for(int j = LER; j < 20; j++){
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
	
	/**
	 * This method will calculate the number of 1 steps formed in the playing board, normally it is better to have a few steps then no steps at all.
	 * @param newboardstate
	 * @return
	 */
	private int numSteps(int[] newboardstate){
		
		int numsteps = 0;
		// follow the top of the stack and count the steps
		int colheight = findColumnHeight(newboardstate, 0);
		for(int i = 9; i >= 1; i--){
			int nextcolheight = findColumnHeight(newboardstate, i+1);
			if(Math.abs(colheight - nextcolheight) == 1){
				numsteps++;
			}
			colheight = nextcolheight;
		}
		return numsteps;
	}
	
	/**
	 * This function computes difference in column heights, purely experimental this function will need some improving.
	 * @param oldboardstate
	 * @param newboardstate
	 * @return
	 */
	private int heightPenalty(int[] oldboardstate, int[] newboardstate){
		int newheightsum = 0;
		int oldheightsum = 0;
		for(int i = 0; i < 10; i++){
			newheightsum += findColumnHeight(newboardstate, i);
			oldheightsum += findColumnHeight(oldboardstate, i);
		}
		return newheightsum - oldheightsum;
	}
	
	/**
	 * Function which finds the height of the column on that boardstate.
	 * @param boardstate The boardstate which you are analyzing.
	 * @param column The column which you want to know the height of.
	 * @return The height of the specified column.
	 */
	private int findColumnHeight(int[] boardstate, int column){
		int height = 0;
		int mask = 1 << (9-column);
		for(int i = LER; i < 20; i++){
			if((boardstate[i] & mask) == mask){
				height = i;
				break;
			}
		}
		return height;
	}
}
