package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
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
		//BANANA - Reporting after done.
		if(register()){
			try{
				waitForPrey();
				//if(!connect())
				//	return;
				while(!_kill){
					Message msg = (Message)_in.readObject();
					Message complete = new Message();
					if(msg.getType())
					{
						//Cap String.
						complete.setData(((String)msg.getData(true)).toUpperCase());
						complete.setType(true);
					}else{
						//SaveFile
						if(msg.getDataLength() > 0){
							msg.writeFileFromData(msg.getFileName());
							complete.setData("File Saved.");
						}else{
							complete.setData("File Not Saved.");
						}
						complete.setType(true);
					}
					log((String)complete.getData(true));
					complete.done = true;
					_out.writeObject(complete);
				}
			}catch(Exception ex){
				log(ex.toString());
			}
		}
	}
	
	public void waitForPrey(){
		try{
			Socket newSocket = null;
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(5555);
				newSocket = serverSocket.accept();
				//BANANA
				_socket = newSocket;
			}
			catch (IOException e) {
				return;
			}

			newSocket.close();
			serverSocket.close();

		}catch(Exception ex){

		}
	}
	
	public boolean connect() throws UnknownHostException, IOException{
		_socket = new Socket(_routerIP, _portNum);
		_out = new ObjectOutputStream(_socket.getOutputStream());
		_in = new ObjectInputStream(_socket.getInputStream());
		Message c = new Message();
		c.setType(true);
		_out.writeObject(c);
		return true;
	}
	
	public boolean register(){
		//BANANA
		//connect
		return true;
	}
	
	public static void log(String x){
		System.out.println("<!--" + x + "-->");
	}
}
