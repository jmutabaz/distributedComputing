import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client extends Thread {

	private String _routerName;
	private int _sockNum;
	private String _destinationIP;
	
	public String _message;
	public boolean _flag = false;
	public boolean _kill = false;
	public String _report = null;

	public Client(String routerName, int sockNum, String destinationIP) throws SocketException{
		_routerName = routerName;
		_sockNum = sockNum;
		_destinationIP = destinationIP;
	}

	public void run(){
		// Variables for setting up connection and communication
		Socket Socket = null; // socket to connect with ServerRouter
		PrintWriter out = null; // for writing to ServerRouter
		BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr;
		String host = "";
		try {
			addr = InetAddress.getLocalHost();
			host = addr.getHostAddress(); // Client machine's IP
		} catch (UnknownHostException e1) {
			_message = e1.toString();
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
		//String routerName = "192.168.1.6"; // ServerRouter host name
		//int SockNum = 5555; // port number

		// Tries to connect to the ServerRouter
		try {
			Socket = new Socket(_routerName, _sockNum);
			out = new PrintWriter(Socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
		} 
		catch (UnknownHostException e) {
			_message = "Don't know about router: " + _routerName + ".";
			_flag = true;
			while(!_kill){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					//e1.printStackTrace();
				}
			}
			return;
		} 
		catch (IOException e) {
			_message = "Couldn't get I/O for the connection.";
			_flag = true;
			while(!_kill){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					//e1.printStackTrace();
				}
			}
			return;
		}

		BufferedReader fromFile;
		try{
			// Variables for message passing	
			Reader reader = new FileReader("file.txt"); 
			fromFile =  new BufferedReader(reader); // reader for the string file
			String fromServer; // messages received from ServerRouter
			String fromUser; // messages sent to ServerRouter
			long t0, t1, t;

			// Communication process (initial sends/receives
			out.println(_destinationIP);// initial send (IP of the destination Server)
			fromServer = in.readLine();//initial receive from router (verification of connection)
			report("ServerRouter: " + fromServer);
			//Thread.sleep(1000);
			out.println(host); // Client sends the IP of its machine as initial send
			t0 = System.currentTimeMillis();

			//Thread.sleep(3000);
			//System.out.println("Sending file stuffsssss");

			// Communication while loop
			while ((fromServer = in.readLine()) != null) {
				report("Server: " + fromServer);
				t1 = System.currentTimeMillis();
				t = t1 - t0;
				report("Cycle time: " + t);

				fromUser = fromFile.readLine(); // reading strings from a file
				if (fromUser != null) {
					report("Client: " + fromUser);
					out.println(fromUser); // sending the strings to the Server via ServerRouter
					t0 = System.currentTimeMillis();
				}
				if (fromServer.equals("Bye.") || fromServer.equals("BYE.")){ // exit statement
					report("Connection Ended.");
					break;
				}
			}
			// closing connections
			out.close();
			in.close();
			fromFile.close();
			Socket.close();
		}catch(Exception e){
			_message = "Sending Error: " + e.toString() + ".";
			_flag = true;
		}
		
		while(!_kill){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				//e1.printStackTrace();
			}
		}
		return;
	}
	
	public void report(String mesg){
		_report = mesg;
		while(mesg != null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				//e1.printStackTrace();
			}
		}
	}
}
