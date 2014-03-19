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
	private Message _msg;

	public Client(String routerIP, int port, Message msg){
		_routerIP = routerIP;
		_portNum = port;
		_msg = msg;
	}

	public void run(){

		//Get Server IP.
		_msg.setDestination(getServerIP(_msg.getServerName()));

		//Send Message.
		try{
			if(!connect())
				return;
			_out.writeObject(_msg);
			Message n = new Message();
			n = (Message)_in.readObject();
			@SuppressWarnings("unused")
			int q = 10;
		}catch(Exception ex){
			log(ex.toString());
		}
	}

	public boolean connect() throws UnknownHostException, IOException{
		try{
			_socket = new Socket(_msg.getDestination(), _portNum);
			_out = new ObjectOutputStream(_socket.getOutputStream());
			_in = new ObjectInputStream(_socket.getInputStream());
		}catch(Exception x){
			log(x.toString());
			return false;
		}
		return true;
	}

	public String getServerIP(String serverName){
		//BANANA - Get server info from server router... joy.
		return "192.168.0.1";
	}

	public static void log(String x){
		System.out.println("<!--" + x + "-->");
	}
}
