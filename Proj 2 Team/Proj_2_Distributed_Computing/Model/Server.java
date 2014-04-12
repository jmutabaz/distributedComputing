package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import application.Main;


public class Server extends Thread {
	private 				String 				_routerIP;
	private 				int 				_portNum;
	private 				Socket 				_socket;
	private 				ObjectOutputStream 	_out;
	private 				ObjectInputStream 	_in;
	public 					boolean 			_kill 				= false;
	private 				String 				_myIP;
	private 				String 				_myName;
	private 				ServerSocket 		_serverSocket 		= null;
	private 				int 				_count 				= 0;

	public Server(String routerIP, int port, String myIP, String myName){
		_routerIP = routerIP;
		_portNum = port;
		_myIP = myIP;
		_myName = myName;
	}

	public void run(){
		/*
		 * By: Rhett and Paul
		 * 		
		 */
		//addToReport("Starting, Registering with Router.");//Do we really want this PANANA
		if(register()){
			try{
				while(!_kill){
					waitForPrey();
					//addToReport("Inbound Message.");//Do we really want this PANANA
					Message msg = (Message)_in.readObject();
					Message complete = new Message();
					complete.setServerName(msg.getServerName());
					if(msg.getType())
					{
						//Cap String.
						addToReport("//---------From: " + msg.getClient() + "\n" + 
								"String to Cap: " + (String)msg.getData(true) + "\n" +
								"//---------");
						complete.setData(((String)msg.getData(true)).toUpperCase());
						complete.setType(true);
					}else{
						//SaveFile
						if(msg.getDataLength() > 0){
							if(msg.writeFileFromData(Main.PATHFILESAVEString + msg.getFileName()))
							{
								complete.setData("File " + msg.getFileName() + " Saved.");
								addToReport("//---------From: " + msg.getClient() + "\n" + 
										"File Saved: " + msg.getFileName() + "\n" + 
										"//---------");
							}
							else
							{
								addToReport("**File \"" + msg.getFileName() + "\" From: " + msg.getClient() + " Couldn't be Saved.");
							}
						}else{
							complete.setData("File Not Saved.");
							addToReport("**No File To Save.");
						}
						complete.setType(true);
					}
					//log((String)complete.getData(true));
					complete.done = true;
					_out.writeObject(complete);
					addToReport("Message Complete, Waiting on Next Message.");
				}
			}catch(Exception ex){
				System.out.println("**Server Error: " + ex.toString());
				//addToReport("**Server Error: " + ex.toString());
			}
		}else{
			//addToReport("**Failed To Register...", true);
		}
	}

	private void waitForPrey(){
		/*
		 * By: Rhett Paul - Named by Rhett
		 * 		Waits for a Client Connection.
		 */
		try{
			Socket newSocket = null;
			try {
				_serverSocket = new ServerSocket(5556);
				addToReport("Ready For Messages.");
				newSocket = _serverSocket.accept();
				_socket = newSocket;
				_out = new ObjectOutputStream(_socket.getOutputStream());
				_in = new ObjectInputStream(_socket.getInputStream());
			}
			catch (IOException e) {
				addToReport("**Couldn't Listen for Connections.", true);
				return;
			}
			_serverSocket.close();
		}catch(Exception ex){

		}
	}

	private boolean connect(){
		/*
		 * By: Rhett
		 * 		Connects to Router.
		 */
		//Connect To Router
		try{
			_socket = new Socket(_routerIP, _portNum);
			_out = new ObjectOutputStream(_socket.getOutputStream());
			_in = new ObjectInputStream(_socket.getInputStream());
		}catch(Exception ex){
			return false;
		}
		return true;
	}

	private boolean register(){
		/*
		 * By: Rhett, Paul, kinda John.
		 */
		try{
			RouterMessage msg = new RouterMessage();
			msg.setType('s');
			msg.setIPToAdd(_myIP);
			msg.setName(_myName);
			if(!connect()){
				addToReport("**Failed to Connect to Router.", true);
				return false;
			}
			//addToReport("Connected.");
			_out.writeObject(msg);
			//addToReport("Waiting For A Response...");
			RouterMessage newMsg = (RouterMessage)_in.readObject();
			if(newMsg.getError() == '\0')
			{
				addToReport("Registered on Router: " + _routerIP);
				return true;
			}else{
				addToReport("**Couldn't Register.", true);
				return false;
			}
		}catch(Exception ex){
			addToReport("**Couldn't Register.", true);
			return false;
		}
	}
	
	private boolean deRegister(){
		/*
		 * By: Rhett, Paul, kinda John.
		 */
		try{
			RouterMessage msg = new RouterMessage();
			msg.setType('s');
			msg.setIPToRemove(_myIP);
			msg.setName(_myName);
			if(!connect()){
				return false;
			}
			_out.writeObject(msg);
			RouterMessage newMsg = (RouterMessage)_in.readObject();
			if(newMsg.getType() == 't')
			{
				addToReport("DeRegistered from " + _routerIP + ".");
				return true;
			}else{
				addToReport("**Couldn't DeRegister.");
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}
	
	public void killMeOff(){
		System.out.println("Server Class kill method called");
		try {
			_kill = true;
			_serverSocket.close();
			deRegister();
			System.out.println("Server Thread successfully killed");
		} catch (IOException e) {
			System.out.print("Error closing the thread in Server Class");
		}
	}

	private void addToReport(String report){
		UpdateMessage msg = new UpdateMessage();
		_count++;
		msg._shouldRestart = false;
		msg._fileName = "Server" + _count;
		msg.setMessage(report);
		msg.setCount(_count);
		msg.WriteFile(msg);
		System.out.println("<!--Server: " + report + "-->");
	}
	
	private void addToReport(String report, boolean shouldRestart){
		UpdateMessage msg = new UpdateMessage();
		_count++;
		msg._shouldRestart = shouldRestart;
		msg._fileName = "Server" + _count;
		msg.setMessage(report);
		msg.setCount(_count);
		msg.WriteFile(msg);
		System.out.println("<!--Server: " + report + "-->");
	}

}
