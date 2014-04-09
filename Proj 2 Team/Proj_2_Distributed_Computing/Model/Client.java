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
		addToReport("Starting Up.");
		//Get Server IP.
		addToReport("Getting Server IP from Router.");
		if(!getServerIP()){
			addToReport("Failed to Get IP from Router.");
			return;
		}else{
			addToReport("Got IP from Router.");
		}


		//Send Message.
		try{
			//Connects...
			if(!connect()){
				addToReport("Couldn't Connect to Server.");
				return;
			}else{
				addToReport("Connected to " + _msg.getServerName() + ".");
			}
			//Send Message...
			_out.writeObject(_msg);
			addToReport("Message Sent.");
			Message n = new Message();
			//Get Response Message...
			n = (Message)_in.readObject();
			addToReport("Server response is: " + (String)n.getData(true));
		}catch(Exception ex){
			addToReport(ex.toString());
		}
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
		//Msg to send to Router.
		RouterMessage resp = null;
		RouterMessage msg = new RouterMessage();
		msg.setIPLookup(_msg.getServerName());
		msg.setType('l');
		
		try{
			Socket newSocket = new Socket(_routerIP, _portNum);
			ObjectOutputStream newOut = new ObjectOutputStream(newSocket.getOutputStream());
			ObjectInputStream newIn = new ObjectInputStream(newSocket.getInputStream());
			
			newOut.writeObject(msg);
			addToReport("Waiting for Lookup Response.");
			resp = (RouterMessage)newIn.readObject();
			newIn.close();
			newOut.close();
			newSocket.close();
		}catch(Exception x){
			addToReport("Lookup Error: " + x.toString());
			return false;
		}
		
		if(resp.getIPLookup() == null){
			addToReport("Server Doesn't Exist.");
			return false;
		}
		
		_msg.setDestination(resp.getIPLookup());
		return true;
	}

	private void addToReport(String report){
		//BANANA - Change how report is set.
		UpdateMessage msg = new UpdateMessage();
		msg.setMessage(report);
		//msg.setCount(0);
		//msg.WriteFile(msg);
		System.out.println("<!--Client: " + report + "-->");
	}
}
