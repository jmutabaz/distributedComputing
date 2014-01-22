import java.io.*;
import java.net.*;

public class TCPClient {
	public static void main(String[] args) throws IOException {

		// Variables for setting up connection and communication
		Socket Socket = null; // socket to connect with ServerRouter
		PrintWriter out = null; // for writing to ServerRouter
		BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); // Client machine's IP
		String routerName = "192.168.1.6"; // ServerRouter host name
		int SockNum = 5555; // port number
		String DestinationIP = "10.5.2.109"; // destination IP (Server)
		String fileToRead = "file.txt";

		// Tries to connect to the ServerRouter
		try {
			Socket = new Socket(routerName, SockNum);
			out = new PrintWriter(Socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
		} 
		catch (UnknownHostException e) {
			System.err.println("Don't know about router: " + routerName +
					"\nError: " + e.toString());
			System.exit(1);
		} 
		catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + routerName +
					"\nError: " + e.toString());
			System.exit(1);
		}

		// Variables for message passing	
		Reader reader = new FileReader(fileToRead); 
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
		fromFile.close();
		out.close();
		in.close();
		Socket.close();
	}
}
