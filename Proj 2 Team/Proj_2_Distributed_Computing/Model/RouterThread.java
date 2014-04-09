package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//BANANA - Check For Timeouts!!!!!!!
// - Don't notify ourselves...
// - Do Updates...
/*
 * I've got a lovely bunch of coconuts!
 * By: (Rhett && Paul && (1/8*John) ? true:false)
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
			
		}
	}

	public void run(){
		try{
			RouterMessage out = new RouterMessage();
			
			_incoming = (RouterMessage)_in.readObject();
			
			if(_incoming.getType() == 's'){
				
				if(_incoming.getIPToAdd() != null)
					out.setError(addToServerList(new ServerID(_incoming.getIPToAdd(),_incoming.getName())));
				else if(_incoming.getIPToRemove() != null)
					_myServers.remove(searchServers(_incoming.getIPToRemove()));
				
			}else if(_incoming.getType() == 'c'){
				String IP = findIPFromName();
				if(IP == null){
					_incoming.setType('l');
					IP = searchOthers();
				}
				out.setIPLookup(IP);
			}else if(_incoming.getType() == 'r'){
				if(_incoming.getIPToAdd() != null)
					addToRouterList(_incoming.getIPToAdd());
				else if(_incoming.getIPToRemove() != null)
					removeRouter(_incoming.getIPToRemove());
			}else if(_incoming.getType() == 'n'){
				addToRouterList(_incoming.getIPToAdd());
				//BANANA ? - tinks paul refernce pas
				out.setRouterList(_routerList);
				_incoming.setType('r');
				updateOthers();
			}else if(_incoming.getType() == 'l'){
				String IP = findIPFromName();
				out.setIPLookup(IP);
			}
			
			addToReport("Sending Out Message.");
			out.setType('t');
			_out.writeObject(out);
		}catch(Exception ex){
			addToReport("ERROR: " + ex.toString());
			return;
		}
	}
	
	public void addToRouterList(String ip){
		/*
		 * By: Rhett, Paul
		 */
		if(_routerList == null)
			return;
		for(String i : _routerList){
			if(i.equals(ip)){
				return;
			}
		}
		_routerList.add(ip);
	}
	
	public char addToServerList(ServerID id){
		/*
		 * By: Rhett, Paul
		 */
		System.out.println("--"+id.getServerIP()+":"+id.getServerName());
		if(_myServers != null)
			return 't';
		for(ServerID i : _myServers){
			if(i.getServerIP().equals(id.getServerIP()) && i.getServerName().equals(id.getServerName()))
				return 't';
			if(i.getServerIP().equals(id.getServerIP()) && !i.getServerName().equals(id.getServerName()))
				return 'a';
			if(!i.getServerIP().equals(id.getServerIP()) && i.getServerName().equals(id.getServerName()))
				return 'n';
		}
		_myServers.add(id);
		return 't';
	}
	
	public String findIPFromName(){
		/*
		 * By: Rhett, Paul
		 */
		if(_myServers == null)
			return null;
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
			if(_routerList == null)
				return null;
			for(String i : _routerList){
				if(!connect(i)){
					Thread.sleep(1000);
					if(connect(i))
						removeRouter(i);
				}else{
					_tempOut.writeObject(_incoming);
				}
			}
		}catch(Exception ex){
			addToReport("ERROR: " + ex.toString());
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
		if(_myServers == null)
			return -1;
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
			if(_routerList == null)
				return null;
			for(String i : _routerList){
				if(!connect(i)){
					Thread.sleep(1000);
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
		if(_routerList == null)
			return false;
		for(int i = 0; i < _routerList.size(); i++)
		{
			if(_routerList.get(i).equals(routerIP))
			{
				_routerList.remove(i);
				return true;
			}
		}
		return false;
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
	
	private void addToReport(String report){
		//BANANA - Change how report is set.
		UpdateMessage msg = new UpdateMessage();
		msg.setMessage(report);
		//msg.WriteFile(msg);
		System.out.println("<!--Router Thread: " + report + "-->");
	}
}
