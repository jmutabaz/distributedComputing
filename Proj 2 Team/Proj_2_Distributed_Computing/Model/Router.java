package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * Router Class that Boots up to accept all
 * incoming connections and route them where needed.
 */
public class Router extends Thread {
	private List<ServerID> _myServers;
	private List<String> _routerList;
	private int _port;
	public boolean _running;
	
	public Router(String otherRouterIP, int port){
		_port = port;
		//Gets info from other routers.
		_running = getMeSetUp(otherRouterIP);
	}
	
	public void run(){
		_running = true;
		try{
			Socket newSocket = null;
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(_port);
			}
			catch (IOException e) {
				return;
			}
			addToReport("Router Listening...");
			while (_running == true)
			{
				try {
					newSocket = serverSocket.accept();
					RouterThread t = new RouterThread(newSocket, _myServers, _routerList);
					t.start();
				}
				catch (IOException e) {
					
				}
			}
			
			//newSocket.close();
			serverSocket.close();
			
		}catch(Exception ex){
			
		}
		
	}
	
	private boolean getMeSetUp(String routerIP){
		if(routerIP == null || routerIP.equals(""))//First Router?
			return true;
		//Contacts other routerIP to get the list of Routers.
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		try{
			socket = new Socket(routerIP, 5555);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			RouterMessage msg = new RouterMessage();
			msg.setType('n');
			msg.setIPToAdd("MyOwnIPFromGUI BANANA");
			out.writeObject(msg);
			RouterMessage newMsg = (RouterMessage)in.readObject();
			//tinks paul my nead dep cpy
			_routerList = newMsg.getRouterList();
			in.close();
			out.close();
			socket.close();
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	public void deRegister(){
		//Should Work...
		for(String x : _routerList){
			//BANANA - If My IP is in list, don't contact.
			Socket socket;
			ObjectOutputStream out;
			try{
				socket = new Socket(x, 5555);
				out = new ObjectOutputStream(socket.getOutputStream());
				RouterMessage msg = new RouterMessage();
				msg.setType('r');
				msg.setIPToRemove("MyOwnIPFromGUI BANANA");
				out.writeObject(msg);
				out.close();
				socket.close();
			}catch(Exception ex){
				
			}
		}
	}
	
	private void addToReport(String report){
		//BANANA - Change how report is set.
		UpdateMessage msg = new UpdateMessage();
		msg.message = report;
		//msg.WriteFile(msg);
		System.out.println("<!--Router: " + report + "-->");
	}
}
