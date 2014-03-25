package Model;

import java.util.List;

public class RouterMessage {
	private String _toAdd;
	private String _toRemove;
	private List<String> _routerList;
	
	public RouterMessage(){
		
	}
	
	//--------------------------------GET AND SET--------------------------------//
	public void setIPToAdd(String ipToAdd){
		_toAdd = (ipToAdd.trim() != "" ? ipToAdd.trim():null);
	}
	
	public String getIPToAdd(){
		return _toAdd;
	}
	
	public void setIPToRemove(String ipToAdd){
		_toRemove = (ipToAdd.trim() != "" ? ipToAdd.trim():null);
	}
	
	public String getIPToRemove(){
		return _toRemove;
	}
	
	public void setRouterList(List<String> RouterList){
		_routerList = RouterList;
	}
	
	public List<String> getRouterList(){
		return _routerList;
	}
}
