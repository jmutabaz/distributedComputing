package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//BANANA - Check For Timeouts!!!!!!!
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
				_myServers.add(new ServerID(_incoming.getIPToAdd(),_incoming.getName()));
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
	
	public String findIPFromName(){
		for(ServerID k : _myServers)
		{
			if(k.getServerName().equals(_incoming.getName())){
				return k.getServerIP();
			}
		}
		return null;
	}
	
	public String updateOthers(){
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
	
	public String searchOthers(){
		try{
			for(String i : _routerList){
				if(!connect(i)){
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

	public void removeRouter(String routerIP){
		//BANANA - Second Chance?
		
		//BANANA - Send Removal Messages
			//BANANA - Remove NonResponsive
		
		//BANANA - If None Respond... assume Russia Attacked...
	}
	
	private boolean connect(String ip){
		/*
		 * By: Rhett
		 * 		Connects to Router.
		 */
		//Connect To Router
		try{
			_tempSoc = new Socket(ip, 5555);
			_tempOut = new ObjectOutputStream(_socket.getOutputStream());
			_tempIn = new ObjectInputStream(_socket.getInputStream());
		}catch(Exception ex){
			return false;
		}
		return true;
	}
}
