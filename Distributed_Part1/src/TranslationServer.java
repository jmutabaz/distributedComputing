import java.io.*;
import java.net.*;


public class TranslationServer {
//change comment
	String routerName = null; // ServerRouter host name
	int SockNum = 0; // port number
	String DestinationIP = null;// destination IP (Client)
	
	public void RunTranslationServer (String router, int port, String des) throws IOException{
		routerName = router; // ServerRouter host name
		SockNum = port; // port number
		DestinationIP = des;// destination IP (Client)
		run();
	}
	
	
	public void run() throws IOException{
		Socket Socket = null; // socket to connect with ServerRouter
		PrintWriter out = null; // for writing to ServerRouter
		BufferedReader in = null; // for reading form ServerRouter
		

		// Tries to connect to the ServerRouter
		try {
			Socket = new Socket(routerName, SockNum);
			out = new PrintWriter(Socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
		} 
		catch (UnknownHostException e) {
			System.err.println("Don't know about router: " + routerName);
			System.exit(1);
		} 
		catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + routerName + "\n" + e.toString());
			System.exit(1);
		}

		// Variables for message passing			
		String fromServer; // messages sent to ServerRouter
		String fromClient; // messages received from ServerRouter

		// Communication process (initial sends/receives)
		out.println(DestinationIP);// initial send (IP of the destination Client)
		fromClient = in.readLine();// initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromClient);

		boolean bye = false;
		// Communication while loop
		while ((fromClient = in.readLine()) != null && !bye) {
			System.out.println("Client said: " + fromClient);
			if (fromClient.equals("Bye.")) // exit statement
				bye = true;
			fromServer = fromClient.toUpperCase(); // converting received message to upper case
			System.out.println("Server said: " + fromServer);
			out.println(fromServer); // sending the converted message back to the Client via ServerRouter
		}

		// closing connections
		out.close();
		in.close();
		Socket.close();
	}
	
	
}
