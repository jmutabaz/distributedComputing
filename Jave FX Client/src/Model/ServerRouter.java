package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ServerRouter extends Thread {
	
	private int _port;
	private int _numOfRowsInTable;
	private SThread t;
	
	private String _message;
	private boolean _flag = false;
	private boolean _kill = false;
	private String _report = "";
	
	
	public int get_port() {
		return _port;
	}

	public void set_port(int _port) {
		this._port = _port;
	}

	public int get_numOfRowsInTable() {
		return _numOfRowsInTable;
	}

	public void set_numOfRowsInTable(int _numOfRowsInTable) {
		this._numOfRowsInTable = _numOfRowsInTable;
	}

	public SThread getSThread() {
		return t;
	}

	public void setSThead(SThread t) {
		this.t = t;
	}

	public String get_message() {
		return _message;
	}

	public boolean is_flag() {
		return _flag;
	}

	public boolean is_kill() {
		return _kill;
	}

	public void set_kill(boolean _kill) {
		this._kill = _kill;
	}

	public String get_report() {
		return _report;
	}

	

	public ServerRouter(int port, int numOfRowsInTable){
		_port = port;
		_numOfRowsInTable = numOfRowsInTable;
	}
	
	public void run(){
		try{
			Socket clientSocket = null; // socket for the thread
			Object [][] RoutingTable = new Object [_numOfRowsInTable][2]; // routing table
			Boolean Running = true;
			int ind = 0; // index in the routing table	

			//Accepting connections
			ServerSocket serverSocket = null; // server socket for accepting connections
			try {
				serverSocket = new ServerSocket(_port);
				report("ServerRouter is Listening on port: " + _port + ".");
			}
			catch (IOException e) {
				_message = "Could not listen on port: " + _port + ".";
				_flag = true;
				while(!_kill){
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						//e1.printStackTrace();
					}
				}
				return;
			}
			
			int y = 2;
			y = y+2;

			// Creating threads with accepted connections
			while (Running == true && ind < _numOfRowsInTable)
			{
				try {
					clientSocket = serverSocket.accept();
					t = new SThread(RoutingTable); // creates a thread with a random port
					//ind += t.insertSocket(clientSocket, ind);
					t.start(); // starts the thread
					ind++; // increments the index
					report("ServerRouter Connection " + t.ind + " with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
				}
				catch (IOException e) {
					report("Client/Server failed to connect.");
					//return false;
				}
			}//end while

			//closing connections
			clientSocket.close();
			serverSocket.close();
		} catch (Exception e){
			_message = "Failed To Run.";
			_flag = true;
			while(!_kill){
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					//e1.printStackTrace();
				}
			}
			return;
		}
	}
	
	private void report(String mesg){
		_report += mesg + "\n";
	}
	
	public String getReport(){
		String rep = _report;
		_report = "";
		return rep;
	}
}
