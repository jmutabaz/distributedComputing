import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/*
 * This Class Is Meant to be a main Implementation for
 * each server and client computer instance.
 *  - Rhett - 1/24/2014
 */
public class SocketClient {

	public SocketClient(){

	}

	/*
	 * Boots up a Server to receive messages from the 
	 * routing server.
	 * 
	 * Params:
	 * 	routerName=IP of RouterServer.
	 * 	SockNum=Socket Number to use to Connect to RouterServer.
	 * 	DestinationIP=IP of Client.
	 */
	public boolean RunServer(String routerName, int SockNum, String DestinationIP) throws SocketException{
		// Variables for setting up connection and communicaton
		Socket Socket = null; // socket to connect with ServerRouter
		PrintWriter out = null; // for writing to ServerRouter
		BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			throw new SocketException("Couldn't Get Local IP.");
		}
		String host = addr.getHostAddress(); // Server machine's IP			
		// Tries to connect to the ServerRouter
		try {
			Socket = new Socket(routerName, SockNum);
			out = new PrintWriter(Socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
		} 
		catch (UnknownHostException e) {
			throw new SocketException("Don't know about router: " + routerName);
		} 
		catch (IOException e) {
			throw new SocketException("Couldn't get I/O for the connection.");
		}

		try{
			// Variables for message passing			
			String fromServer; // messages sent to ServerRouter
			String fromClient; // messages received from ServerRouter      
			String address = DestinationIP; // destination IP (Client)

			// Communication process (initial sends/receives)
			out.println(address);// initial send (IP of the destination Client)
			fromClient = in.readLine();// initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromClient);

			// Communication while loop
			while ((fromClient = in.readLine()) != null) {
				System.out.println("Client said: " + fromClient);
				if (fromClient.equals("Bye.")) // exit statement
					break;
				fromServer = fromClient.toUpperCase(); // converting received message to upper case
				System.out.println("Server said: " + fromServer);
				out.println(fromServer); // sending the converted message back to the Client via ServerRouter
			}
			out.close();
			in.close();
			Socket.close();

		}catch(Exception e){
			throw new SocketException("Error Sending Data.");
		}
		return true;
	}

	public boolean RunClient(String routerName, int SockNum, String DestinationIP) throws SocketException{
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String routerName = "192.168.1.6"; // ServerRouter host name
		//int SockNum = 5555; // port number

		// Tries to connect to the ServerRouter
		try {
			Socket = new Socket(routerName, SockNum);
			out = new PrintWriter(Socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
		} 
		catch (UnknownHostException e) {
			throw new SocketException("Don't know about router: " + routerName + ".");
		} 
		catch (IOException e) {
			throw new SocketException("Couldn't get I/O for the connection.");
		}

		try{
			// Variables for message passing	
			Reader reader = new FileReader("file.txt"); 
			BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
			String fromServer; // messages received from ServerRouter
			String fromUser; // messages sent to ServerRouter
			String address = DestinationIP; // destination IP (Server)
			long t0, t1, t;

			// Communication process (initial sends/receives
			out.println(address);// initial send (IP of the destination Server)
			fromServer = in.readLine();//initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromServer);
			out.println(host); // Client sends the IP of its machine as initial send
			t0 = System.currentTimeMillis();

			// Communication while loop
			while ((fromServer = in.readLine()) != null) {
				System.out.println("Server: " + fromServer);
				t1 = System.currentTimeMillis();
				if (fromServer.equals("Bye.")) // exit statement
					break;
				t = t1 - t0;
				System.out.println("Cycle time: " + t);

				fromUser = fromFile.readLine(); // reading strings from a file
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser); // sending the strings to the Server via ServerRouter
					t0 = System.currentTimeMillis();
				}
			}

			// closing connections
			out.close();
			in.close();
			Socket.close();
		}catch(Exception e){
			throw new SocketException("Sending Error: " + e.toString() + ".");
		}
		return true;
	}
}
