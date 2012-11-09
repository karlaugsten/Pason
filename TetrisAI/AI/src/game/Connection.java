package game;

import org.zeromq.ZMQ;

public class Connection {

	/**
	 * TODO: generate initialization
	 */
	public Connection(){}
	
	/**
	 * Method to manually connect to test server using 
	 * Match token and server url.
	 * @return True if connection was successful.
	 */
	public boolean ConnectManual(String matchtoken, String url){
		ZMQ.Context context = ZMQ.context(1);
		
		ZMQ.Socket test = context.socket(ZMQ.REQ);
		
		test.connect("tcp://"+url+":5557");
		
		Command connect = new MatchConnectCommand(matchtoken);
		
		
		System.out.println(connect.getMessage());
		test.send(connect.getBytes(), 0);
		byte[] recsig = test.recv(0);
		
		System.out.println(new String(recsig));
		
		return true;
	}
	
	/**
	 * Method which will automatically connect to test server
	 * using an http post.
	 *
	 * @return True if connection was successful.
	 */
	public boolean ConnectAuto(){
		
		return true;
	}
	
	
}
