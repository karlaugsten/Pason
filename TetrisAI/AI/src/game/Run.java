package game;

public class Run {

	/**
	 * Main function which will setup match
	 */
	public static void main(String[] args) {
		Connection c = new Connection();
		String matchtoken = "40a4da18-7724-4def-bf33-c88d409ee4d0";
		String url = "ec2-54-242-48-216.compute-1.amazonaws.com";
		c.ConnectManual(matchtoken, url);

	}

}
