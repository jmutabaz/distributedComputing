package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread {
	private 				String	 			_routerIP;
	private 				int 				_portNum;
	private 				Socket 				_socket;
	private 				ObjectOutputStream 	_out;
	private 				ObjectInputStream 	_in;
	private 				Message 			_msg;
	private 				int 				_count 				= 0;
	public 					String 				_desIP;

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
		addToReport("Starting Up, Getting Server IP from Router.");
		if(!getServerIP()){
			addToReport("**Failed to Get IP from Router.");
			return;
		}else{
			addToReport("Found IP \"" + _desIP + "\" from Router.");
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
			addToReport("Message Sent and Waiting on Response.");
			Message n = new Message();
			n = (Message)_in.readObject();
			addToReport("//---------From: " + n.getServerName() + "\n" + 
					"Response: " + (String)n.getData(true) + "\n" + 
					"//---------");
		}catch(Exception ex){
			addToReport("**Sending Error: " + ex.toString());
		}
	}

	private boolean connect() throws UnknownHostException, IOException{
		/*
		 * By: Rhett
		 * 		Establishes Connection to Server to send Message to.
		 */
		try{
			_socket = new Socket(_desIP, _portNum);
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
			addToReport("Router Is Looking Up IP.");
			resp = (RouterMessage)newIn.readObject();
			newIn.close();
			newOut.close();
			newSocket.close();
		}catch(Exception x){
			addToReport("**Lookup Error: " + x.toString(), true);
			return false;
		}
		if(resp.getIPLookup() == null){
			return false;
		}
		_desIP = resp.getIPLookup();
		return true;
	}

	private void addToReport(String report){
		UpdateMessage msg = new UpdateMessage();
		_count++;
		msg._shouldRestart = false;
		msg._fileName = "Client" + _count;
		msg.setMessage(report);
		msg.setCount(_count);
		msg.WriteFile(msg);
		System.out.println("<!--Client: " + report + "-->");
	}
	
	private void addToReport(String report, boolean shouldRestart){
		UpdateMessage msg = new UpdateMessage();
		_count++;
		msg._shouldRestart = shouldRestart;
		msg._fileName = "Client" + _count;
		msg.setMessage(report);
		msg.setCount(_count);
		msg.WriteFile(msg);
		System.out.println("<!--Client: " + report + "-->");
	}
}
