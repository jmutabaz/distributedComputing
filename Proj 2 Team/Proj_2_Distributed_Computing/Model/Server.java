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
	private String _report = null;

	public Server(String routerIP, int port){
		_routerIP = routerIP;
		_portNum = port;
	}

	public void run(){
		/*
		 * By: Rhett and Paul
		 * 		
		 */
		addToReport("Server: Starting Up.");
		addToReport("Server: Registering with Router.");
		if(register()){
			try{
				waitForPrey();
				if(!connect()){
					waitForPickUp();
					return;
				}
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
					log((String)complete.getData(true));
					complete.done = true;
					_out.writeObject(complete);
				}
			}catch(Exception ex){
				log(ex.toString());
			}
			waitForPickUp();
		}
		waitForPickUp();
	}

	private void waitForPrey(){
		try{
			Socket newSocket = null;
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(5555);
				addToReport("Server: Waiting for Connection.");
				newSocket = serverSocket.accept();
				addToReport("Server: Client Connected.");
				_socket = newSocket;
			}
			catch (IOException e) {
				addToReport("Server: Couldn't Listen for Connections.");
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
			addToReport("Server: Failed to Connect to Router.");
			return false;
		}
		addToReport("Server: Connected to Router.");
		return true;
	}

	private boolean register(){
		//BANANA
		RouterMessage msg = new RouterMessage();
		msg.setIPToAdd("MYIP");
		//Send msg

		//connect
		addToReport("Server: Registered.");
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
