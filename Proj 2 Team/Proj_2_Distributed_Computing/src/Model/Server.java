package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server extends Thread {
	private String _routerIP;
	private int _portNum;
	private Socket _socket;
	private ObjectOutputStream _out;
	private ObjectInputStream _in;
	public boolean _kill = false;
	
	public Server(String routerIP, int port){
		_routerIP = routerIP;
		_portNum = port;
	}
	
	public void run(){
		try{
			connect();
			while(!_kill){
				Message msg = (Message)_in.readObject();
				if(msg.getDataLength() > 0){
					msg.writeFileFromData("newPic.jpg");
				}
				Message complete = new Message();
				complete.done = true;
				_out.writeObject(complete);
			}
		}catch(Exception ex){
			log(ex.toString());
		}
	}
	
	public boolean connect() throws UnknownHostException, IOException{
		_socket = new Socket(_routerIP, _portNum);
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_in = new ObjectInputStream(_socket.getInputStream());
		Message c = new Message();
		c.setType(1);
		_out.writeObject(c);
		return true;
	}
	
	public static void log(String x){
		System.out.println(x);
	}
}
