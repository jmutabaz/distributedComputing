package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server extends Thread {
	private String _routerIP;
	private int _portNum;
	private Socket _socket;
	private ObjectOutputStream _out;
	private ObjectInputStream _in;
	public boolean _kill = false;
	private String _myIP;

	public Server(String routerIP, int port, String myIP){
		_routerIP = routerIP;
		_portNum = port;
		_myIP = myIP;
	}

	public void run(){
		/*
		 * By: Rhett and Paul
		 * 		
		 */
		addToReport("Starting Up.");
		addToReport("Registering with Router.");
		if(register()){
			try{
				addToReport("Registered.");
				waitForPrey();
				while(!_kill){
					Message msg = (Message)_in.readObject();
					Message complete = new Message();
					if(msg.getType())
					{
						//Cap String.
						complete.setData(((String)msg.getData(true)).toUpperCase());
						complete.setType(true);
					}else{
						//SaveFile
						if(msg.getDataLength() > 0){
							msg.writeFileFromData(msg.getFileName());
							complete.setData("File Saved.");
						}else{
							complete.setData("File Not Saved.");
						}
						complete.setType(true);
					}
					//log((String)complete.getData(true));
					complete.done = true;
					_out.writeObject(complete);
				}
			}catch(Exception ex){
				//log(ex.toString());
			}
		}else{
			addToReport("Failed To Register...");
		}
	}

	private void waitForPrey(){
		/*
		 * By: Rhett Paul - Named by Rhett
		 * 		Waits for a Client Connection.
		 */
		try{
			Socket newSocket = null;
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(5555);
				addToReport("Waiting for Connection.");
				newSocket = serverSocket.accept();
				addToReport("Client Connected.");
				_socket = newSocket;
				_out = new ObjectOutputStream(_socket.getOutputStream());
				_in = new ObjectInputStream(_socket.getInputStream());
			}
			catch (IOException e) {
				addToReport("Couldn't Listen for Connections.");
				return;
			}
			serverSocket.close();
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
			addToReport("Failed to Connect to Router.");
			return false;
		}
		addToReport("Connected to Router.");
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
			msg.setName("RhettPanter");//BANANA - From GUI
			if(!connect()){
				addToReport("Connection Failed.");
				return false;
			}
			_out.writeObject(msg);
			addToReport("Waiting For A Response.");
			RouterMessage newMsg = (RouterMessage)_in.readObject();
			if(newMsg.getError() == '\0')
			{
				addToReport("Registered.");
				return true;
			}else{
				addToReport("Couldn't Register.");
				return false;
			}
		}catch(Exception ex){
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
			msg.setName("BANANA - from GUI");
			if(!connect()){
				return false;
			}
			_out.writeObject(msg);
			RouterMessage newMsg = (RouterMessage)_in.readObject();
			if(newMsg.getType() == 't')
			{
				addToReport("DeRegistered.");
				return true;
			}else{
				addToReport("Couldn't DeRegister.");
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}

	private void addToReport(String report){
		//BANANA - Change how report is set.
		UpdateMessage msg = new UpdateMessage();
		msg.setMessage(report);
		//msg.count=BANANA; make count equal the count number.....ha  ha
		msg.WriteFile(msg);
		System.out.println("<!--Server: " + report + "-->");
	}

}
