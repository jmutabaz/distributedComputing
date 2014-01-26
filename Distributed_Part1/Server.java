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
		catch (UnknownHostException e) {
			//throw new SocketException("Don't know about router: " + _routerName);
			System.exit(1);
		} 
		catch (IOException e) {
			//throw new SocketException("Couldn't get I/O for the connection.");
			System.exit(1);
		}

		try{
			// Variables for message passing			
			String fromServer; // messages sent to ServerRouter
			String fromClient; // messages received from ServerRouter      

			// Communication process (initial sends/receives)
			out.println(_destinationIP);// initial send (IP of the destination Client)
			fromClient = in.readLine();// initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromClient);

			// Communication while loop
			while ((fromClient = in.readLine()) != null) {
				System.out.println("Client said: " + fromClient);
				fromServer = fromClient.toUpperCase(); // converting received message to upper case
				System.out.println("Server said: " + fromServer);
				out.println(fromServer); // sending the converted message back to the Client via ServerRouter
				if (fromClient.equals("Bye.")) // exit statement
					break;
			}
			out.close();
			in.close();
			Socket.close();

		}catch(Exception e){
			//throw new SocketException("Error Sending Data.");
			System.exit(1);
		}
	}
}
