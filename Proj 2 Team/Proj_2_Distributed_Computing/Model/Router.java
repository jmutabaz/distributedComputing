package Model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * Router Class that Boots up to accept all
 * incoming connections and route them where needed.
 */
public class Router extends Thread {
	private List<ServerID> _myServers;
	private List<String> _routerList;
	private String _report;
	private int _port;
	
	public Router(int port){
		_port = port;
	}
	
	public void run(){
		boolean running = true;
		try{
			Socket newSocket = null;
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(_port);
			}
			catch (IOException e) {
				return;
			}
			
			while (running == true)
			{
				try {
					newSocket = serverSocket.accept();
					RouterThread t = new RouterThread(newSocket, _myServers, _routerList);
					t.start();
				}
				catch (IOException e) {
					
				}
			}
			
			newSocket.close();
			serverSocket.close();
			
		}catch(Exception ex){
			
		}
		
	}
	
	private void addToReport(String report){
		log(report);
		_report = "Router: " + report + "\n" + _report;
	}

	public String getReport(){
		/*
		 * By: Rhett
		 * 		Returns current report to the SocketClient.
		 */
		String temp = _report;
		_report = null;
		return temp;
	}

	private void waitForPickUp(){
		/*
		 * By: Rhett
		 * 		Waits for Report to be Retrieved.
		 */
		try{
			while(_report != null){
				Thread.sleep(1000);
			}
		}catch(Exception ex){

		}
	}

	private static void log(String x){
		System.out.println("<!--Router: " + x + "-->");
	}
}
