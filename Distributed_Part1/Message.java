import java.net.InetAddress;
import java.net.UnknownHostException;


/* Public Class To Be Passed between Sockets.
 * Contains Get and Set Methods for the Message Data.
 */

public class Message {
	// IP of Server to Send To.
	private String _ReceiverIp;
	// Message to Send Out.
	private String _Message;
	// Senders IP.
	private String _SenderIp;

	// Construct with Empty Params.
	// Sets Host Address.
	public Message() throws Exception{
		try {
			InetAddress addr = InetAddress.getLocalHost();
			_SenderIp = addr.getHostAddress();
		} catch (UnknownHostException e) {
			throw new Exception("Sender IP Not Established.");
		}
	}
	
	//Construct with Full Param List.
	public Message(String MyIP, String ReceiverIP, String Message){
		_ReceiverIp = ReceiverIP;
		_SenderIp = MyIP;
		_Message = Message;
	}

	public void setReceiverIp(String receiver){
		_ReceiverIp = receiver;
	}

	public void setMessage(String msg){
		_Message = msg;
	}

	public void setSenderIp(String sender){
		_SenderIp = sender;
	}

	public String getReceiverIp(){
		return _ReceiverIp;
	}

	public String getMessage(){
		return _Message;
	}

	public String getSenderIp(){
		return _SenderIp;
	}

}
