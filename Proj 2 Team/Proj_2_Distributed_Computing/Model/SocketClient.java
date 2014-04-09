package Model;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketClient extends Thread {

	private Server _ser;
	private Client _cli;
	private Router _router;
	private String _routerIP;
	private String _myIp;
	private String _updatePath;
	private String _name;
	private int _runType;
	private Message _msg;

	public SocketClient(String MyIP, String routerIP, String UpdatePath, String name, int runType, Message msg){
		_updatePath = UpdatePath;
		_myIp = MyIP;
		_name = name;
		_runType = runType;
		_routerIP = routerIP;
		_msg = msg;
	}
	
	public void run(){
		switch(_runType){
		case 1:
			RunServer();
			break;
		default:
			break;
		}
	}

	private boolean RunServer() {
		try{
			_ser = new Server(_routerIP, 5555, _myIp, _name);
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

	private boolean RunClient() {
		try{
			_cli = new Client(_routerIP, 5555, _msg);
			_cli.start();
			System.out.println("<!--Running-->");
			_cli.join();
			return true;
		}
		catch(Exception e)
		{
			System.out.println("<!--" + e.toString() + "-->");
			return false;
		}
	}

	private String RunServerRouter() {
		try{
			_router = new Router(_routerIP, 5555, _myIp);
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
