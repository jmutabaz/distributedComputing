package Model;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
	private Socket _socket;
	private String _addr;
	private boolean _isWaiting;
	private String _type;
	public ObjectOutputStream _outToServer;
	public ObjectInputStream _inFromServer;

	public Connection(Socket socket, String addr){
		setSocket(socket);
		setAddr(addr);
	}
	
	public void setType(String type){
		_type = type;
	}
	
	public String getType(){
		return _type;
	}
	
	public void setWaiting(boolean waiting){
		_isWaiting = waiting;
	}
	
	public boolean getWaiting(){
		return _isWaiting;
	}

	public void setSocket(Socket socket){
		_socket = (socket.isConnected() ? socket:null);
	}

	public Socket getSocket(){
		return (_socket == null ? null:(_socket.isConnected() ? _socket:null));
	}
	
	public void setAddr(String addr){
		_addr = addr;
	}
	
	public String getAddr(){
		return _addr;
	}
	
	public boolean isConnected(){
		return (_socket == null ? false:(_socket.isConnected() ? true:false));
	}
}
