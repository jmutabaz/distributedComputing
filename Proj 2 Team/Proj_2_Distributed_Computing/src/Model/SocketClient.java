package Model;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketException;


public class SocketClient {
	
	private Server ser;
	private Client cli;
	
	public SocketClient(){
		
	}
	
	public boolean RunServer(String ip, int port) {
		try{
			ser = new Server(ip, port);
			ser.start();

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
			cli = new Client(ip, port, msg);
			cli.start();
			
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
			Router router = new Router(5555, 100);
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
		return "";
	}
	
	
}
