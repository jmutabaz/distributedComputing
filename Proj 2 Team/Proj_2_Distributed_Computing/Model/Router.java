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
	private 				List<ServerID> 				_myServers 				= new ArrayList<ServerID>();
	private 				List<String> 				_routerList 			= new ArrayList<String>();
	private 				int 						_port;
	private 				String 						_myIP;
	public 					boolean 					_running;
	private 				ServerSocket 				_serverSocket 			= null;
	private 				int 						_count 					= 0;
	
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
		try{
			Socket newSocket = null;
			try {
				_serverSocket = new ServerSocket(_port);
			}
			catch (IOException e) {
				System.out.println("**Couldn't Listen, Error: " + e.toString());
				//addToReport("**Couldn't Listen, Error: " + e.toString(), false);
				return;
			}
			if(_running)
				addToReport("Ready For Clients.", false);
			RouterThread t;
			while (_running == true)
			{
				try {
					newSocket = _serverSocket.accept();
					t = new RouterThread(newSocket, _myServers, _routerList, _count);
					t.start();
					//addToReport("Router Thread Started, Count: " + (_myServers != null ? _myServers.size():"0"), false);
				}
				catch (IOException e) {
					System.out.println("Router Class Run method IOException " + e.toString());
				}
				
				
			}
			addToReport("Closing Router.", false);
			newSocket.close();
			if(_serverSocket != null)
				_serverSocket.close();
			_running = false;
			
		}catch(Exception ex){
			System.out.println("Run Method Router Class **Error " + ex.toString());
			//addToReport("Run Method Router Class **Error " + ex.toString(), false);
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
		System.out.println("Router Class deRegister");
		for(String x : _routerList){
			Socket socket;
			ObjectOutputStream out;
			try{
				socket = new Socket(x, 5555);
				out = new ObjectOutputStream(socket.getOutputStream());
				RouterMessage msg = new RouterMessage();
				msg.setType('r');
				msg.setIPToRemove(_myIP);
				out.writeObject(msg);
				out.close();
				socket.close();
				addToReport("DeRegistered.", false);
				System.out.println("Router Class deRegister successfully");
			}catch(Exception ex){
				addToReport("**Couldn't Contact " + x, false);
				System.out.println("Router Class deRegister ERROR could not deregister");
			}
		}
	}
	
	public void killMeOff(){
		/*
		 * By Rhett
		 * 		If the Router is listening for connections, it stops then deregisters.
		 */
		System.out.println("Router Class killmeoff method");
		try {
			System.out.println("Router Class killmeof method closing socket");
			_running = false;
			_serverSocket.close();
		} catch (IOException e) {
			//Eating It is OKAY.
			System.out.println("Router Class killMeOff method ERROR closing socket");
		}
		System.out.println("Router Class killMeOff method deregister");
		deRegister();
		
	}
	
	private void addToReport(String report, boolean updateList){
		UpdateMessage msg = new UpdateMessage();
		_count++;
		msg._fileName = "Router" + _count;
		msg._updateList = updateList;
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
