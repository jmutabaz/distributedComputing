import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class ServerRouter extends Thread {
	
	String _port;
	int _numOfRowsInTable;
	
	public ServerRouter(String port, int numOfRowsInTable){
		_port = port;
		_numOfRowsInTable = numOfRowsInTable;
	}
	
	public void run(){
		try{
			Socket clientSocket = null; // socket for the thread
			Object [][] RoutingTable = new Object [_numOfRowsInTable][2]; // routing table
			int SockNum = 5555; // port number
			Boolean Running = true;
			int ind = 0; // index in the routing table	

			//Accepting connections
			ServerSocket serverSocket = null; // server socket for accepting connections
			try {
				serverSocket = new ServerSocket(SockNum);
				System.out.println("| ServerRouter is Listening on port: " + SockNum + ".");
			}
			catch (IOException e) {
				//System.err.println("| Could not listen on port: " + SockNum + ".\nReason: " + e.toString());
				return;
			}

			// Creating threads with accepted connections
			while (Running == true && ind < _numOfRowsInTable)
			{
				try {
					clientSocket = serverSocket.accept();
					SThread t = new SThread(RoutingTable, clientSocket, ind); // creates a thread with a random port
					t.start(); // starts the thread
					ind++; // increments the index
					System.out.println("| ServerRouter Connection " + ind + " with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
				}
				catch (IOException e) {
					System.err.println("| Client/Server failed to connect.");
					//return false;
				}
			}//end while

			//closing connections
			clientSocket.close();
			serverSocket.close();
		}catch(Exception e){
			//throw new SocketException(e.toString());
			return;
		}
	}
}
