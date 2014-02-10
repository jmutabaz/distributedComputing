package Model;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import application.Main;
import Model.ServerRouter;
/*
 * This Class Is Meant to be a main Implementation for
 * each server and client computer instance.
 *  - Rhett - 1/24/2014
 */
public class SocketClient extends Thread {
	
	public String _report = "";
	public String _MyIP;
	
	private Thread _cc;
	private ServerRouter router;
	
	public String get_MyIP() {
		return _MyIP;
	}

	public void set_MyIP(String _MyIP) {
		this._MyIP = _MyIP;
	}

	public ServerRouter getRouter() {
		return router;
	}

	public void setRouter(ServerRouter router) {
		this.router = router;
	}

	public SocketClient(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			_MyIP = addr.getHostAddress(); // Client machine's IP
		} catch (UnknownHostException e1) {
			_MyIP = "";
		}
		
	}

	/*
	 * Boots up a Server Thread to receive messages from the 
	 * routing server.
	 * 
	 * Params:
	 * 	routerName=IP of RouterServer.
	 * 	SockNum=Socket Number to use to Connect to RouterServer.
	 * 	DestinationIP=IP of Client.
	 */
	public String RunServer(String routerName, int sockNum, String destinationIP, boolean GUI) throws SocketException{
		try{
			Server ser = new Server(routerName, sockNum, destinationIP);
			ser.start();
			
			//This is the reporting and terminating means of the thread.
			//._report is a filed that the Thread post messages to and
			//._message is an error where ._flag indicates if there is 
			// an error/
			while(!ser._kill){
				if(ser._flag)
				{
					String x = ser._message;
					ser._kill = true;
					return x;
				}
				if(ser._report != null)
				{
					if(GUI){
						report(ser._report);
					} else {
						System.out.println("| " + ser._report);
					}
					ser._report = null;
				}
				Thread.sleep(100);
			}
			ser.join();
			return null;
		}catch(Exception e){
			return "Couldn't Run Server.";
		}
	}

	/*
	 * Boots up a Client Thread to send messages to the 
	 * routing server.
	 * 
	 * Params:
	 * 	routerName=IP of RouterServer.
	 * 	SockNum=Socket Number to use to Connect to RouterServer.
	 * 	DestinationIP=IP of Client.
	 */
	public String RunClient(String routerName, int sockNum, String destinationIP, boolean GUI) throws SocketException{
		try{
			Client cli = new Client(routerName, sockNum, destinationIP);
			cli.start();
			
			//This is the reporting and terminating means of the thread.
			//._report is a filed that the Thread post messages to and
			//._message is an error where ._flag indicates if there is 
			// an error/
			while(!cli._kill){
				if(cli._flag)
				{
					String x = cli._message;
					cli._kill = true;
					return x;
				}
				if(cli._report != null)
				{
					if(GUI){
						report(cli._report);
					} else {
						System.out.println("| " + cli._report);
					}
					cli._report = null;
				} else {
					cli._report = "No connection in router ";
					Main.RC.updateGUI(_report);
				}
				Thread.sleep(100);
			}
			cli.join();
			return null;
		}catch(Exception e){
			return "Couldn't Run Client";
		}
	}

	
	
	/*
	 * Boots up a ServerRouter Thread.
	 * 
	 * Params:
	 * 	port: Port Number to use.
	 *  numOfRowsInTable: Number of active connections to Server.
	 *  
	 */
	public String RunServerRouter(int port, int numOfRowsInTable, boolean GUI) throws SocketException{
		try{
			//Starts a Thread Class ServerRouter.
			router = new ServerRouter(port, numOfRowsInTable);
			router.start();
			//This is the reporting and terminating means of the thread.
			//._report is a filed that the Thread post messages to and
			//._message is an error where ._flag indicates if there is 
			// an error/
			_cc = router;
			return "";
		}catch(Exception ex){
			return "Failed To Run ServerRouter.";
		}
	}
	
	public void killRouter(){
		router.set_kill(true);
	}
	
	private void report(String mesg){
		_report += mesg + "\n";
	}
	
	public String getReport(){
		return ((ServerRouter) _cc).getReport();
	}
	
}




