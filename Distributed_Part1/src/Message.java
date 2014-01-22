
public class Message {
	private String _ReceiverIp;
	private String _Message;
	private String _SenderIp;

	public Message(){

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
