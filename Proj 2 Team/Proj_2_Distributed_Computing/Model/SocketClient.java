package Model;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketClient {
	
	private Server _ser;
	private Client _cli;
	private String _myIp;
	
	public SocketClient(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			_myIp = addr.getHostAddress(); // Client machine's IP
		} catch (UnknownHostException e1) {
			return;
		}
		
		//BANANA - Need to add box to get ip.
		//Type in Ip Address
		_myIp = "192.168.254.3";
	}
	
	public boolean RunServer(String ip, int port) {
		try{
			_ser = new Server(ip, port, _myIp);
			_ser.start();

			System.out.println("<!--Running-->");

			return true;
		}
		catch(Exception e)
		{
			System.out.println("<!--" + e.toString() + "-->");
			return false;
		}
	}
	
	public boolean RunClient(String ip, int port, Message msg) {
		try{
			_cli = new Client(ip, port, msg);
			_cli.start();
			System.out.println("<!--Running-->");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("<!--" + e.toString() + "-->");
			return false;
		}
	}
	
	public String RunServerRouter() {
		try{
			//Starts a Thread Class ServerRouter.
			Router router = new Router(5555);
			router.start();
			
			//This is the reporting and terminating means of the thread.
			//._report is a filed that the Thread post messages to and
			//._message is an error where ._flag indicates if there is 
			// an error/
			router.join();
			return null;
		}catch(Exception ex){
			return "Failed To Run ServerRouter.";
		}
	}
	
	public String report(){
		if(_cli != null)
			return _cli.getReport();
		if(_ser != null)
			return _ser.getReport();
		return null;
	}
	
	
}
