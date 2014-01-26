import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/*
 * This Class Is Meant to be a main Implementation for
 * each server and client computer instance.
 *  - Rhett - 1/24/2014
 */
public class SocketClient extends Thread {
	public SocketClient(){
		
	}

	/*
	 * Boots up a Server Thread to receive messages from the 
	 * routing server.
	 * 
	 * Params:
	 * 	routerName=IP of RouterServer.
	 * 	SockNum=Socket Number to use to Connect to RouterServer.
	 * 	DestinationIP=IP of Client.
	 */
	public void RunServer(String routerName, int sockNum, String destinationIP) throws SocketException{
		Server ser = new Server(routerName, sockNum, destinationIP);
		ser.run();
	}

	/*
	 * Boots up a Client Thread to send messages to the 
	 * routing server.
	 * 
	 * Params:
	 * 	routerName=IP of RouterServer.
	 * 	SockNum=Socket Number to use to Connect to RouterServer.
	 * 	DestinationIP=IP of Client.
	 */
	public void RunClient(String routerName, int sockNum, String destinationIP) throws SocketException{
		Client cli = new Client(routerName, sockNum, destinationIP);
		cli.run();
	}

	public boolean RunServerRouter(String port, int numOfRowsInTable) throws SocketException{
		try{
			Socket clientSocket = null; // socket for the thread
			Object [][] RoutingTable = new Object [numOfRowsInTable][2]; // routing table
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
				System.err.println("| Could not listen on port: " + SockNum + ".\nReason: " + e.toString());
				return false;
			}

			// Creating threads with accepted connections
			while (Running == true && ind < numOfRowsInTable)
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
					return false;
				}
			}//end while

			//closing connections
			clientSocket.close();
			serverSocket.close();
		}catch(Exception e){
			throw new SocketException(e.toString());
		}
		return true;
	}
}
