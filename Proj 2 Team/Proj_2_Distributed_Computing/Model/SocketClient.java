package Model;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketClient extends Thread {

	private 				Server 				_ser;
	private 				Client 				_cli;
	private 				Router 				_router;
	private 				String 				_routerIP;
	private 				String 				_myIp;
	private 				String 				_name;
	private 				int 				_runType;
	private 				Message 			_msg;

	public SocketClient(String MyIP, String routerIP, String name, int runType, Message msg){
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
		case 2:
			RunClient();
			break;
		case 3:
			RunServerRouter();
			break;
		default:
			break;
		}
	}
	
	public void killMe(){
		switch(_runType){
		case 1:
			System.out.println("Killing server thread");
			KillServer();
			break;
		case 2:
			break;
		case 3:
			KillServerRouter();
			break;
		default:
			break;
		}
	}

	private boolean RunServer() {
		try{
			_ser = new Server(_routerIP, 5556, _myIp, _name);
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

	private void KillServer(){
		if(_ser != null &&_ser.isAlive()){
			_ser.killMeOff();
		}
	}

	private boolean RunClient() {
		try{
			_cli = new Client(_routerIP, 5556, _msg);
			_cli.start();
			_cli.join();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	private boolean RunServerRouter() {
		try{
			_router = new Router(_routerIP, 5555, _myIp);
			_router.start();
			_router.join();
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	private void KillServerRouter(){
		if(_router != null && _router.isAlive()){
			_router.killMeOff();
		}
	}
}
