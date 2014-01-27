import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/*
 * This Class Is Meant to be a main Implementation for
 * each server and client computer instance.
 *  - Rhett - 1/24/2014
 */
public class SocketClient extends Thread {
	public SocketClient(){

	}

	/*
	 * Boots up a Server Thread to receive messages from the 
	 * routing server.
	 * 
	 * Params:
	 * 	None, values are ask for from command line.
	 */
	public String RunServer() throws SocketException{
		try{
			System.out.print("| Enter RouterName: ");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String routerName = bufferRead.readLine();
			System.out.print("| Enter Port Number: ");
			int sockNum = Integer.parseInt(bufferRead.readLine());
			System.out.print("| Enter Destination IP: ");
			String destinationIP = bufferRead.readLine();

			Server ser = new Server(routerName, sockNum, destinationIP);
			ser.start();

			System.out.println("| Running... ");

			while(!ser._kill){
				if(ser._flag)
				{
					String x = ser._message;
					ser._kill = true;
					return x;
				}
				if(ser._report != null)
				{
					System.out.println("| " + ser._report);
					ser._report = null;
				}
				Thread.sleep(100);
			}

			ser.join();
			return "Done.";
		}
		catch(Exception e)
		{
			return "Couldn't Run Server.";
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
	public String RunServer(String routerName, int sockNum, String destinationIP) throws SocketException{
		try{
			Server ser = new Server(routerName, sockNum, destinationIP);
			ser.start();
			while(!ser._kill){
				if(ser._flag)
				{
					String x = ser._message;
					ser._kill = true;
					return x;
				}
				if(ser._report != null)
				{
					System.out.println("| " + ser._report);
					ser._report = null;
				}
				Thread.sleep(100);
			}
			ser.join();
			return "Done.";
		}catch(Exception e){
			return "Couldn't Run Server.";
		}
	}

	/*
	 * Boots up a Client Thread to send messages to the 
	 * routing server.
	 * 
	 * Params:
	 * 	None, ask for them from command Line.
	 */
	public String RunClient() throws SocketException{
		try{
			System.out.print("| Enter RouterName: ");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String routerName = bufferRead.readLine();
			System.out.print("| Enter Port Number: ");
			int sockNum = Integer.parseInt(bufferRead.readLine());
			System.out.print("| Enter Destination IP: ");
			String destinationIP = bufferRead.readLine();

			Client cli = new Client(routerName, sockNum, destinationIP);
			cli.start();

			System.out.println("| Running... ");
			
			while(!cli._kill){
				if(cli._flag)
				{
					String x = cli._message;
					cli._kill = true;
					return x;
				}
				if(cli._report != null)
				{
					System.out.println("| " + cli._report);
					cli._report = null;
				}
				Thread.sleep(100);
			}

			cli.join();
			return "Done.";
		}
		catch(Exception e)
		{
			return "Couldn't Run Client.";
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
	public String RunClient(String routerName, int sockNum, String destinationIP) throws SocketException{
		try{
			Client cli = new Client(routerName, sockNum, destinationIP);
			cli.start();
			while(!cli._kill){
				if(cli._flag)
				{
					String x = cli._message;
					cli._kill = true;
					return x;
				}
				if(cli._report != null)
				{
					System.out.println("| " + cli._report);
					cli._report = null;
				}
				Thread.sleep(100);
			}
			cli.join();
			return "Done.";
		}catch(Exception e){
			return "Couldn't Run Client";
		}
	}

	/*
	 * Boots up a ServerRouter Thread.
	 * 
	 * Params:
	 * 	None. Ask for Data From Command Line.
	 */
	public void RunServerRouter() throws SocketException{
		try{
			System.out.print("| Enter Port Number: ");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String port = bufferRead.readLine();
			System.out.println("| Enter Number of Connections: ");
			int numOfRowsInTable = Integer.parseInt(bufferRead.readLine());

			ServerRouter router = new ServerRouter(port, numOfRowsInTable);
			router.start();

			router.join();
		}catch(Exception e){

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
	public void RunServerRouter(String port, int numOfRowsInTable) throws SocketException{
		try{
			ServerRouter router = new ServerRouter(port, numOfRowsInTable);
			router.start();

			router.join();
		}catch(Exception ex){

		}
	}
}




