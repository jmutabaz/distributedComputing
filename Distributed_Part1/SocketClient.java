import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	 * 	None, values are ask for from command line.
	 */
	public void RunServer() throws SocketException{
		try{
			System.out.println("| Enter RouterName: ");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String routerName = bufferRead.readLine();
			System.out.println("| Enter Port Number: ");
			int sockNum = Integer.parseInt(bufferRead.readLine());
			System.out.println("| Enter Destination IP: ");
			String destinationIP = bufferRead.readLine();
			
			Server ser = new Server(routerName, sockNum, destinationIP);
			ser.run();
			
			System.out.println("| Running... ");
			
			ser.join();
		}
		catch(Exception e)
		{
			throw new SocketException("Couldn't Run Server.");
		}
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
		try{
			Server ser = new Server(routerName, sockNum, destinationIP);
			ser.run();
			ser.join();
		}catch(Exception e){
			throw new SocketException("Couldn't Run Server.");
		}
	}

	/*
	 * Boots up a Client Thread to send messages to the 
	 * routing server.
	 * 
	 * Params:
	 * 	None, ask for them from command Line.
	 */
	public void RunClient() throws SocketException{
		try{
			System.out.println("| Enter RouterName: ");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String routerName = bufferRead.readLine();
			System.out.println("| Enter Port Number: ");
			int sockNum = Integer.parseInt(bufferRead.readLine());
			System.out.println("| Enter Destination IP: ");
			String destinationIP = bufferRead.readLine();
			
			Client cli = new Client(routerName, sockNum, destinationIP);
			cli.run();
			
			System.out.println("| Running... ");
			
			cli.join();
		}
		catch(Exception e)
		{
			throw new SocketException("Couldn't Run Client.");
		}
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
		try{
			Client cli = new Client(routerName, sockNum, destinationIP);
			cli.run();
			cli.join();
		}catch(Exception e){
			throw new SocketException("Couldn't Run Client");
		}
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
