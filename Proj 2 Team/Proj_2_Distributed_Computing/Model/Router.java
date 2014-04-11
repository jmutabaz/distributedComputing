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
	private List<ServerID> _myServers = new ArrayList<ServerID>();
	private List<String> _routerList = new ArrayList<String>();
	private int _port;
	private String _myIP;
	public boolean _running;
	private ServerSocket _serverSocket = null;
	private int _count = 0;
	
	public Router(String otherRouterIP, int port, String myIP){
		_myIP = myIP;
		_port = port;
		//Gets info from other routers.
		_running = getMeSetUp(otherRouterIP);
	}
	
	public void run(){
		/*
		 * By: Rhett and Paul
		 * 		Starts the process of listing for incoming connections.
		 */
		_running = true;
		try{
			Socket newSocket = null;
			try {
				_serverSocket = new ServerSocket(_port);
			}
			catch (IOException e) {
				addToReport("**Couldn't Listen, Error: " + e.toString(), false);
				return;
			}
			addToReport("Ready For Clients.", false);
			while (_running == true)
			{
				try {
					addToReport("Router Thread Started, Count: " + (_myServers != null ? _myServers.size():"0"), false);
					newSocket = _serverSocket.accept();
					RouterThread t = new RouterThread(newSocket, _myServers, _routerList, _count);
					t.start();
				}
				catch (IOException e) {
					
				}
			}
			addToReport("Closing Router.", false);
			newSocket.close();
			_serverSocket.close();
			
		}catch(Exception ex){
			addToReport("**Error: " + ex.toString(), false);
		}
		
	}
	
	private boolean getMeSetUp(String routerIP){
		/*
		 * By: Rhett and Paul
		 * 		Gets the data it needs if the routerIP var is not null or ""
		 * 		Contacts the routerIP and gets a list of other Routers logged on.
		 */
		if(routerIP == null || routerIP.equals("")){//First Router?
			addToReport("I'm the First Router.", false);
			return true;
		}
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
			msg.setIPToAdd(_myIP);
			out.writeObject(msg);
			RouterMessage newMsg = (RouterMessage)in.readObject();
			_routerList = newMsg.getRouterList();
			_routerList.add(routerIP);
			addToReport("Found Router List from " + routerIP + ".", true);
			in.close();
			out.close();
			socket.close();
		}catch(Exception ex){
			addToReport("**Couldn't Get Setup.", false);
			return false;
		}
		return true;
	}
	
	public void deRegister(){
		/*
		 * By Rhett
		 * 		Contacts all other Routers and Tells them that I am going offline.
		 */
		for(String x : _routerList){
			Socket socket;
			ObjectOutputStream out;
			try{
				socket = new Socket(x, 5555);
				out = new ObjectOutputStream(socket.getOutputStream());
				RouterMessage msg = new RouterMessage();
				msg.setType('r');
				msg.setIPToRemove("_myIP");
				out.writeObject(msg);
				out.close();
				socket.close();
				addToReport("DeRegistered.", false);
			}catch(Exception ex){
				addToReport("**Couldn't Contact " + x, false);
			}
		}
	}
	
	public void killMeOff(){
		/*
		 * By Rhett
		 * 		If the Router is listening for connections, it stops then deregisters.
		 */
		try {
			_serverSocket.close();
		} catch (IOException e) {
			//Eating It is OKAY.
		}
		deRegister();
		_running = false;
	}
	
	private void addToReport(String report, boolean updateList){
		UpdateMessage msg = new UpdateMessage();
		_count++;
		msg._fileName = "Router" + _count;
		if(updateList)
		{
			msg._myServers = _myServers;
			msg._routerList = _routerList;
		}
		msg._isRouter = true;
		msg.setMessage(report);
		msg.setCount(_count);
		msg.WriteFile(msg);
		System.out.println("<!--Router: " + report + "-->");
	}
}
