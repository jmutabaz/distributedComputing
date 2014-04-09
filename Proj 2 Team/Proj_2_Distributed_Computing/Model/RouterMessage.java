package Model;

import java.io.Serializable;
import java.util.List;

public class RouterMessage implements Serializable {
	private char _type = '\0'; //Sending: s - Server(Register), r - Router(Existing Notify Others), n - New Router(Newly Online, need list), c - Client Look up IP, l - Router Look Up.
						//Response: t - All Okay, f - Errors.
	private String _toAdd;
	private String _toRemove;
	private List<String> _routerList;
	private String _IPLookup;
	private String _name;
	private char _errorMsg = '\0';//n - NameExists, a - AddressHasDiffName, g - general.
	
	public RouterMessage(){
		
	}
	
	//--------------------------------GET AND SET--------------------------------//
	
	public void setError(char type){
		/*
		 * By: Rhett??
		 */
		_errorMsg = (type == 'n' ? 'n':(type == 'g' ? 'g':(type == 'a' ? 'a':'\0')));
	}
	
	public char getError(){
		return _errorMsg;
	}
	
	public void setType(char type){
		/*
		 * By: Rhett???????
		 */
		_type = (type == 's' ? 's':(type == 'r' ? 'r':(type == 'n' ? 'n':(type == 't' ? 't':(type == 'f' ? 'f':(type == 'l' ? 'l':(type == 'c' ? 'c':'\0')))))));
	}
	
	public char getType(){
		return _type;
	}
	
	public void setName(String name){
		_name = (name.trim() != "" ? name.trim():null);
	}
	
	public String getName(){
		return _name;
	}
	
	public void setIPToAdd(String ipToAdd){
		_toAdd = (ipToAdd.trim() != "" ? ipToAdd.trim():null);
	}
	
	public String getIPToAdd(){
		return _toAdd;
	}
	
	public void setIPLookup(String ipFound){
		_IPLookup = (ipFound.trim() != "" ? ipFound.trim():null);
	}
	
	public String getIPLookup(){
		return _IPLookup;
	}
	
	public void setIPToRemove(String ipToRemove){
		_toRemove = (ipToRemove.trim() != "" ? ipToRemove.trim():null);
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
