package Model;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketClient {

	private Server _ser;
	private Client _cli;
	private Router _router;
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
		_myIp = "192.168.1.4";
	}

	public boolean RunServer(String ip, int port, String name) {
		try{
			_ser = new Server(ip, port, _myIp, name);
			_ser.start();
			_ser.join();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("<!--" + e.toString() + "-->");
			return false;
		}
	}

	public void KillServer(){
		if(_ser.isAlive()){
			_ser.killMeOff();
		}
	}

	public boolean RunClient(String ip, int port, Message msg) {
		try{
			_cli = new Client(ip, port, msg);
			_cli.start();
			System.out.println("<!--Running-->");
			_ser.join();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("<!--" + e.toString() + "-->");
			return false;
		}
	}

	public String RunServerRouter(String myIP, String otherRouterIP) {
		try{
			_router = new Router(otherRouterIP, 5555, myIP);
			_router.start();
			_router.join();
			return "Server Router Ended.";
		}catch(Exception ex){
			return "Failed To Run ServerRouter.";
		}
	}

	public void KillServerRouter(){
		if(_router.isAlive()){
			_router.killMeOff();
		}
	}

}
