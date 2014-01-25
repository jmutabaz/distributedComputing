import java.io.*;
import java.net.*;
import java.lang.Exception;


public class SThread extends Thread 
{
	private Object [][] RTable; // routing table
	private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
	private BufferedReader in; // reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; // communication strings
	private Socket outSocket; // socket for communicating with a destination
	private int ind; // index in the routing table
	private boolean _hasMessage = false;
	private String _message;

	// Constructor SThread(Object [][] Table, Socket toClient, int index) throws IOException
	SThread() throws IOException
	{

	}

	SThread(Object [][] Table, Socket toClient, int index) throws IOException
	{
		out = new PrintWriter(toClient.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
		RTable = Table;
		addr = toClient.getInetAddress().getHostAddress();
		RTable[index][0] = addr; // IP addresses 
		RTable[index][1] = toClient; // sockets for communication
		ind = index;
	}

	// Run method (will run for each machine that connects to the ServerRouter)
	public void run()
	{
		try
		{
			// Initial sends/receives
			destination = in.readLine(); // initial read (the destination for writing)
			System.out.println("Forwarding to " + destination);
			out.println("Connected to the router."); // confirmation of connection

			// waits 10 seconds to let the routing table fill with all machines' information
			try{
				Thread.currentThread().sleep(10000); 
			}
			catch(InterruptedException ie){
				System.out.println("Thread interrupted");
			}

			// loops through the routing table to find the destination
			for (int i = 0; i < 10; i++) 
			{
				if (destination.equals((String) RTable[i][0])){
					outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
					System.out.println("Found destination: " + destination);
					outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
				}
			}

			// Communication loop	
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Client/Server said: " + inputLine);
				outputLine = inputLine; // passes the input from the machine to the output string for the destination
				if ( outSocket != null){				
					outTo.println(outputLine); // writes to the destination
				}
				if (inputLine.equals("Bye.") || inputLine.equals("BYE.")) // exit statement
					break;
			}// end while		 
		}// end try
		catch (IOException e) {
			System.err.println("Could not listen to socket.\nReason: " + e.toString());
			System.exit(1);
		}
		
		/* - Code Needed For "StartUp.java" - Above is for
		 * Normal Run.
		for(int i = 0; i < 1; i++)
		{
			try {
				Thread.sleep(10000);
				_hasMessage = true;
				_message = "Some Message";
				System.out.println("| Message Found");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/

	}

	public boolean reportIfMessage(){
		return _hasMessage;
	}
	public String reportMessage(){
		return _message;
	}
}