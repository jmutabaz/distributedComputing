package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread {
	private String _routerIP;
	private int _portNum;
	private Socket _socket;
	private ObjectOutputStream _out;
	private ObjectInputStream _in;
	private Message _msg;
	private String _report;

	public Client(String routerIP, int port, Message msg){
		/*
		 * By: Rhett
		 * 		Sets the routerIP, port, and msg.
		 * 		msg already contains the serverName and data.
		 */
		_routerIP = routerIP;
		_portNum = port;
		_msg = msg;
	}

	public void run(){
		/*
		 * By: Rhett, Paul
		 * 		Starts up Client, gets IP from Router.
		 * 		Connects to Server and Sends Message.
		 */
		addToReport("Client: Starting Up.");
		//Get Server IP.
		addToReport("Client: Getting Server IP from Router.");
		if(!getServerIP()){
			addToReport("Client: Failed to Get IP from Router.");
			waitForPickUp();
			return;
		}else{
			addToReport("Client: Got IP from Router.");
		}


		//Send Message.
		try{
			//Connects...
			if(!connect()){
				addToReport("Client: Couldn't Connect to Server.");
				waitForPickUp();
				return;
			}else{
				addToReport("Client: Connected to " + _msg.getServerName() + ".");
			}
			//Send Message...
			_out.writeObject(_msg);
			addToReport("Client: Message Sent.");
			Message n = new Message();
			//Get Response Message...
			n = (Message)_in.readObject();
			addToReport("Client: Server response is: " + (String)n.getData(true));
		}catch(Exception ex){
			addToReport(ex.toString());
		}
		waitForPickUp();
	}

	private boolean connect() throws UnknownHostException, IOException{
		/*
		 * By: Rhett
		 * 		Establishes Connection to Server to send Message to.
		 */
		try{
			_socket = new Socket(_msg.getDestination(), _portNum);
			_out = new ObjectOutputStream(_socket.getOutputStream());
			_in = new ObjectInputStream(_socket.getInputStream());
		}catch(Exception x){
			addToReport(x.toString());
			return false;
		}
		return true;
	}

	private boolean getServerIP(){
		/*
		 * By: Rhett
		 * 		Connects to Router and returns the ip of the given serverName.
		 */

		_msg.getServerName();

		_msg.setDestination("Returned Value");

		//BANANA - Get server info from server router... joy.
		return true;
	}

	private void addToReport(String report){
		log(report);
		_report = "Client: " + report + "\n" + _report;
	}

	public String getReport(){
		/*
		 * By: Rhett
		 * 		Returns current report to the SocketClient.
		 */
		String temp = _report;
		_report = null;
		return temp;
	}

	private void waitForPickUp(){
		/*
		 * By: Rhett
		 * 		Waits for Report to be Retrieved.
		 */
		try{
			while(_report != null){
				Thread.sleep(1000);
			}
		}catch(Exception ex){

		}
	}

	private static void log(String x){
		System.out.println("<!--Client: " + x + "-->");
	}
}
