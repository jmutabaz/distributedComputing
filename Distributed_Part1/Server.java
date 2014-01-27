import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Server extends Thread {

	private String _routerName;
	private int _sockNum;
	private String _destinationIP;
	
	public String _message;
	public boolean _flag = false;
	public boolean _kill = false;
	public String _report = null;

	public Server(String routerName, int sockNum, String destinationIP) throws SocketException{
		_routerName = routerName;
		_sockNum = sockNum;
		_destinationIP = destinationIP;
	}

	public void run(){
		// Variables for setting up connection and communicaton
		Socket Socket = null; // socket to connect with ServerRouter
		PrintWriter out = null; // for writing to ServerRouter
		BufferedReader in = null; // for reading form ServerRouter		
		// Tries to connect to the ServerRouter
		try {
			Socket = new Socket(_routerName, _sockNum);
			out = new PrintWriter(Socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
		} 
		catch (UnknownHostException e1) {
			_message = "Don't know about router: " + _routerName + ".";
			_flag = true;
			while(!_kill){
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					//e1.printStackTrace();
				}
			}
			return;
		} 
		catch (IOException e1) {
			_message = "Couldn't get I/O for the connection.";
			_flag = true;
			while(!_kill){
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					//e1.printStackTrace();
				}
			}
			return;
		}

		try{
			// Variables for message passing			
			String fromServer; // messages sent to ServerRouter
			String fromClient; // messages received from ServerRouter      

			// Communication process (initial sends/receives)
			out.println(_destinationIP);// initial send (IP of the destination Client)
			fromClient = in.readLine();// initial receive from router (verification of connection)
			report("ServerRouter: " + fromClient);

			// Communication while loop
			while ((fromClient = in.readLine()) != null) {
				report("Client said: " + fromClient);
				fromServer = fromClient.toUpperCase(); // converting received message to upper case
				report("Server said: " + fromServer);
				out.println(fromServer); // sending the converted message back to the Client via ServerRouter
				if (fromClient.equals("Bye.")){ // exit statement
					report("Connection Ended.");
					break;
				}
			}
			_kill = true;
			out.close();
			in.close();
			Socket.close();

		}catch(Exception e){
			//throw new SocketException("Error Sending Data.");
			return;
		}
	}
	
	public void report(String mesg){
		_report = mesg;
		while(_report != null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				//e1.printStackTrace();
			}
		}
	}
}
