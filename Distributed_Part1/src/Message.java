
public class Message {
	private String _ReceiverIp;
	private String _Message;
	private String _SenderIp;
	/*Hi....Im Alex. My mom told me today after school we would go to the fair...But when
	 she picked me up from school she hit me with a wrench....Twice...no actually it was
	 three times...I got hit so many times that i tend to forget...but when I got home 
	 she sat me down and asked me "Alex, do you know why I beat you with a wrench?" and 
	 then I said....
	 */
	//This is a test from Paul.
	//Construct.
	public Message(){
		//Rhett Pushed A Big'en.
		//Clint added this
		//Clint added this too
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
