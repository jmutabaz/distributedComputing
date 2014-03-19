package Model;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketException;


public class SocketClient {
	public SocketClient(){
		
	}
	
	public String RunServer() {
		try{
			Server ser = new Server("10.0.0.16", 5555);
			ser.start();

			System.out.println("| Running... ");

			//This is the reporting and terminating means of the thread.
			//._report is a filed that the Thread post messages to and
			//._message is an error where ._flag indicates if there is 
			// an error/

			ser.join();
			return "Done.";
		}
		catch(Exception e)
		{
			return "Couldn't Run Server.";
		}
	}
	
	public String RunClient() {
		try{
			Client cli = new Client("l3lawns.com", 5555);
			cli.start();
			
			//This is the reporting and terminating means of the thread.
			//._report is a filed that the Thread post messages to and
			//._message is an error where ._flag indicates if there is 
			// an error/
			cli.join();
			return "Done.";
		}catch(Exception e){
			return "Couldn't Run Client";
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
	
	
}
