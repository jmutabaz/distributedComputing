package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//BANANA - Check For Timeouts!!!!!!!
/*
 * I've got a lovely bunch of coconuts!
 * By: (Rhett && Paul && 1/8*John ? true:false)
 */
public class RouterThread extends Thread {
	private Socket _socket, _tempSoc;
	private ObjectOutputStream _out, _tempOut;
	private ObjectInputStream _in, _tempIn;
	private List<ServerID> _myServers;
	private List<String> _routerList;
	private RouterMessage _incoming;

	public RouterThread(Socket newSocket, List<ServerID> servers, List<String> routers){
		try{
			_socket = newSocket;
			_out = new ObjectOutputStream(_socket.getOutputStream());
			_in = new ObjectInputStream(_socket.getInputStream());
			_myServers = servers;
			_routerList = routers;
		}catch(Exception ex){
			//BANANA - Streams didn't open.
		}
	}

	public void run(){
		try{
			RouterMessage out = new RouterMessage();
			
			_incoming = (RouterMessage)_in.readObject();
			
			if(_incoming.getType() == 's'){
				if(_incoming.getIPToAdd() != null)
					_myServers.add(new ServerID(_incoming.getIPToAdd(),_incoming.getName()));
				else if(_incoming.getIPToRemove() != null)
					_myServers.remove(searchServers(_incoming.getIPToRemove()));
			}else if(_incoming.getType() == 'r'){
				_routerList.add(_incoming.getIPToAdd());
			}else if(_incoming.getType() == 'n'){
				_routerList.add(_incoming.getIPToAdd());
				//BANANA ? - tinks paul refernce pas
				out.setRouterList(_routerList);
				_incoming.setType('r');
				updateOthers();
			}else if(_incoming.getType() == 'c'){
				String IP = findIPFromName();
				if(IP == null){
					_incoming.setType('l');
					IP = searchOthers();
				}
				out.setIPLookup(IP);
			}else if(_incoming.getType() == 'l'){
				String IP = findIPFromName();
				out.setIPLookup(IP);
			}
			
			out.setType('t');
			_out.writeObject(out);
		}catch(Exception ex){
			return;
		}
	}
	
	public void addToRouterList(String ip){
		/*
		 * By: Rhett, Paul
		 */
		for(String i : _routerList){
			if(i.equals(ip)){
				return;
			}
		}
		_routerList.add(ip);
	}
	
	public char addToClientList(ServerID id){
		/*
		 * By: Rhett, Paul
		 */
		//BANANA
		for(ServerID i : _myServers){
			if(i.getServerIP().equals(id.getServerIP()) && !i.getServerName().equals(id.getServerName())){
				return 'a';
			}else if(i.getServerName().equals(id.getServerName())){
				return 'n';
			}
		}
		//_routerList.add(ip);
		return 'x';
	}
	
	public String findIPFromName(){
		/*
		 * By: Rhett, Paul
		 */
		for(ServerID k : _myServers)
		{
			if(k.getServerName().equals(_incoming.getName())){
				return k.getServerIP();
			}
		}
		return null;
	}
	
	public String updateOthers(){
		/*
		 * By: Rhett, Paul
		 */
		try{
			for(String i : _routerList){
				if(!connect(i)){
					removeRouter(i);
				}else{
					_tempOut.writeObject(_incoming);
				}
			}
		}catch(Exception ex){

		}
		return null;
	}
	
	public int searchServers(String ip){
		/*
		 * By: Rhett, Paul
		 * 		Searches server list, returns index.
		 */
		if(ip.trim() == "" || ip == null)
			return -1;
		int count = 0;
		for(ServerID i : _myServers){
			if(i.getServerIP().equals(ip.trim()))
				return count;
			count++;
		}
		return -1;
	}
	
	public String searchOthers(){
		/*
		 * By: Rhett, Paul
		 * 		Searches other routers for person.
		 */
		try{
			for(String i : _routerList){
				if(!connect(i)){
					if(removeRouter(i))
						if(connect(i))
							removeRouter(i);
				}else{
					_tempOut.writeObject(_incoming);
					RouterMessage msg = (RouterMessage)_tempIn.readObject();
					if(msg.getIPLookup() != null)
						return msg.getIPLookup();
				}
			}
		}catch(Exception ex){

		}
		return null;
	}

	public boolean removeRouter(String routerIP){
		//BANANA - Second Chance?
		
		//BANANA - Send Removal Messages
			//BANANA - Remove NonResponsive
		
		//BANANA - If None Respond... assume Russia Attacked...
		return true;
	}
	
	private boolean connect(String ip){
		/*
		 * By: Rhett
		 * 		Connects to Router.
		 */
		try{
			if(_tempSoc.isConnected())
			{
				_tempOut.close();
				_tempIn.close();
				_tempSoc.close();
			}
			_tempSoc = new Socket(ip, 5555);
			_tempOut = new ObjectOutputStream(_socket.getOutputStream());
			_tempIn = new ObjectInputStream(_socket.getInputStream());
		}catch(Exception ex){
			return false;
		}
		return true;
	}
}
