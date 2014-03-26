package Model;

import java.util.List;

public class ServerID {
	private String _serverName;
	private String _serverIP;
	
	public ServerID(String name, String ip){
		setServerName(name);
		setServerIP(ip);
	}
	
	public void setServerName(String name){
		_serverName = (name.trim() != "" ? name.trim():null);
	}
	
	public String getServerName(){
		return _serverName;
	}
	
	public void setServerIP(String ip){
		_serverIP = (ip.trim() != "" ? ip.trim():null);
	}
	
	public String getServerIP(){
		return _serverIP;
	}
}
