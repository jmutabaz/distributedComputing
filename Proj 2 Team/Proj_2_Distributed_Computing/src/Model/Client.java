package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread {
	private String _routerIP;
	private int _portNum;
	private Socket _socket;
	private ObjectOutputStream _out;
	private ObjectInputStream _in;
	public boolean _kill = false;
	
	public Client(String routerIP, int port){
		_routerIP = routerIP;
		_portNum = port;
	}
	
	public void run(){
		try{
			connect();
			Message x = new Message();
			x.readFileIntoData("pic.jpg");
			x.setDestination("10.0.0.5");
			x.setType(2);
			_out.writeObject(x);
			Message n = new Message();
			n = (Message)_in.readObject();
			@SuppressWarnings("unused")
			int q = 10;
		}catch(Exception ex){
			log(ex.toString());
		}
	}
	
	public boolean connect() throws UnknownHostException, IOException{
		_socket = new Socket(_routerIP, _portNum);
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_in = new ObjectInputStream(_socket.getInputStream());
		return true;
	}
	
	public static void log(String x){
		System.out.println(x);
	}
}
