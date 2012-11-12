package ai;

import java.util.Random;

public class PageRank {
	
	private int[][] ranks;
	private int numofstacks = 43046721;
	
	public PageRank(){
		ranks = new int[2][numofstacks];
		Random r = new Random();
		for(int i = 0; i < numofstacks; i++){
			ranks[1][i] = r.nextInt();
		}
	}
	
	public void performRanks(){
		for (int iteration = 0; iteration < 100; iteration++)
		{
			for (int stack = 0; stack < numofstacks; stack++)
			{
				ranks[iteration%2][stack] = rank(iteration, stack);
			}
		}
		
	}
	
	public int rank(int iteration, int stack){
		
		return 0;
	}
	
	public int rankPiece(int iteration, int stack, String piece){
		
		return 0;
	}
	

}