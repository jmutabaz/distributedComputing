package Model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Router Class that Boots up to accept all
 * incoming connections and route them where needed.
 */
public class Router extends Thread {
	private int _port;
	private int _numOfRowsInTable;
	private ArrayList<Connection> RTable;
	
	public Router(int port, int numOfRowsInTable){
		_port = port;
		_numOfRowsInTable = numOfRowsInTable;
	}
	
	public void run(){
		RTable = new ArrayList<Connection>();
		boolean running = true;
		try{
			Socket newSocket = null;
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(_port);
			}
			catch (IOException e) {
				return;
			}
			
			while (running == true)
			{
				try {
					newSocket = serverSocket.accept();
					RouterThread t = new RouterThread(newSocket, RTable, findIndex(
							newSocket.getInetAddress().getHostAddress()));
					t.start();
				}
				catch (IOException e) {
					
				}
			}
			
			newSocket.close();
			serverSocket.close();
			
		}catch(Exception ex){
			
		}
		
	}
	
	public void removeClosedConnections(){
		for(Connection c : RTable){
			if(!c.isConnected())
				RTable.remove(c);
		}
	}
	
	public int findIndex(String addr){
		for(int i = 0; i < RTable.size(); i++){
			if(RTable.get(i).getAddr().equals(addr) && RTable.get(i).isConnected())
				return i;
		}
		return -1;
	}
}
