package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
//BANANA
public class RouterThread extends Thread {
	private ArrayList<Connection> _RTable;
	//private PrintWriter _out, _outTo; 
	//private BufferedReader _in;
	ObjectOutputStream _outToClient;
	ObjectInputStream _inFromClient;
	ObjectOutputStream _outToServer;
	ObjectInputStream _inFromServer;
	boolean _is1Server;
	private Connection _current;
	private Message _message;
	private Message _responseMessage;

	public RouterThread(Socket socket, ArrayList<Connection> RTable, int index) throws IOException, ClassNotFoundException{
		_RTable = RTable;
		_responseMessage = new Message();
		if(index == -1){
			_RTable.add(new Connection(socket, socket.getInetAddress().getHostAddress()));
			_current = _RTable.get(_RTable.size()-1);
		}else{
			_current = _RTable.get(index);
		}
	}

	public void run(){
		try{
			ObjectOutputStream outTo1 = new ObjectOutputStream(_current.getSocket().getOutputStream());
			ObjectInputStream inFrom1 = new ObjectInputStream(_current.getSocket().getInputStream());

			_message = (Message)inFrom1.readObject();

			/*if(_message.getType() == 1)
				serverRun(outTo1, inFrom1);
			else if(_message.getType() == 2)
				clientRun(outTo1, inFrom1);
			else*/
				return;
		}catch(Exception ex){
			ex.toString();
		}
		return;
	}
	
	public void serverRun(ObjectOutputStream outTo1, ObjectInputStream inFrom1) throws IOException{
		_outToServer = outTo1;
		_inFromServer = inFrom1;
		
		_current.setWaiting(true);
		_current.setType("SERVER");
		
		_current._inFromServer = _inFromServer;
		_current._outToServer = _outToServer;
	}
	
	public void clientRun(ObjectOutputStream outTo1, ObjectInputStream inFrom1) throws InterruptedException, IOException, ClassNotFoundException{
		_outToClient = outTo1;
		_inFromClient = inFrom1;
		
		int index;
		while((index = findConnectionIndex(_message.getDestination())) == -1){
			Thread.sleep(100);
		}
		
		while(!_RTable.get(index).getWaiting()){
			Thread.sleep(100);
		}
		
		_message.writeFileFromData("newpic.jpg");
		
		Connection server = _RTable.get(index);
		
		server.setWaiting(false);
		
		_outToServer = server._outToServer;
		_inFromServer = server._inFromServer;
		
		_outToServer.writeObject(_message);
		Message incoming = (Message)_inFromServer.readObject();
		server.setWaiting(true);
		_outToClient.writeObject(incoming);
		
		_outToClient.close();
		_inFromClient.close();
		
	}
	
	public int findConnectionIndex(String addr){
		for(int i = 0; i < _RTable.size(); i++){
			if(_RTable.get(i).getAddr().equals(addr) && _RTable.get(i).isConnected())
				return i;
		}
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
}
