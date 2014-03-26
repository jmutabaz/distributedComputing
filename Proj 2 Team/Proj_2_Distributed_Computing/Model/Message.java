package Model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Message implements Serializable {
	private String _destinationIP; //Destination IP Address.
	private String _myIP; // My IP Address
	private boolean _type; // File or String. // true = String, false = File.
	private byte[] _data; // Byte[] of data Object
	private String _fileName; // Name of File.
	private String _serverName; //Name of Server to send to.
	public boolean done = false;

	public Message(){

	}

	public Message(boolean type, String destination, String myIP, Object data, String fileName, String serverName){
		setType(type);
		setDestination(destination);
		setFileName(fileName);
		setData(data);
		setMyIP(myIP);
		setServerName(serverName);
	}

	public boolean readFileIntoData(String filePath){
		try {
			Path path = Paths.get(filePath);
			setData(Files.readAllBytes(path));
			return true;
		}
		catch(Exception ex) {
			System.out.print(ex.toString());
			return false;				
		}
	}

	public boolean writeFileFromData(String filePath){
		try {
			FileOutputStream outputStream =
					new FileOutputStream(filePath);
			outputStream.write((byte[])getData(false));
			outputStream.close();	
			return true;
		}
		catch(Exception ex) {
			System.out.print(ex.toString());
			return false;
		}
	}

	private byte[] compress(byte[] data) throws IOException {
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
		deflater.finish(); 
		byte[] buffer = new byte[1024];  
		while (!deflater.finished()) { 
			int count = deflater.deflate(buffer); // returns the generated code... index 
			outputStream.write(buffer, 0, count);  
		} 
		deflater.end();
		outputStream.close();
		return outputStream.toByteArray(); 
	}  

	private static byte[] decompress(byte[] data) throws IOException, DataFormatException { 
		Inflater inflater = new Inflater();  
		inflater.setInput(data); 
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length); 
		byte[] buffer = new byte[1024]; 
		while (!inflater.finished()) { 
			int count = inflater.inflate(buffer); 
			outputStream.write(buffer, 0, count); 
		} 
		outputStream.close();   
		return outputStream.toByteArray();
	}  

	public int getDataLength(){
		return _data.length;
	}

	public static boolean validateIP(String IP)
	{
		String[] n = IP.split("\\.");
		if((Integer.parseInt(n[0]) < 0) || (Integer.parseInt(n[0]) > 255 ))
		{
			return false;
		}
		else if((Integer.parseInt(n[1]) < 0) || (Integer.parseInt(n[1]) > 255 ))
		{
			return false;
		}
		else if((Integer.parseInt(n[2]) < 0) || (Integer.parseInt(n[2]) > 255 ))
		{
			return false;
		}
		else if((Integer.parseInt(n[3]) < 0) || (Integer.parseInt(n[3]) > 255 ))
		{
			return false;
		}
		return true;
	}

	//--------------------------------GET AND SET--------------------------------//

	public boolean setData(Object data){
		try{
			if(data.getClass().equals(byte[].class)){
				_data = compress((byte[])data);
			}else{
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				ObjectOutputStream o = new ObjectOutputStream(b);
				o.writeObject(data);
				_data = compress(b.toByteArray());
			}
			return true;
		}catch(Exception ex){
			_data = null;
			return false;
		}
	}

	public Object getData(boolean asObject){
		try{
			if(asObject)
				return new ObjectInputStream(new ByteArrayInputStream(decompress(_data))).readObject();
			else
				return decompress(_data);
		}catch(Exception ex){
			return false;
		}
	}

	public void setMyIP(String myIP){
		_myIP = myIP;
	}

	public String getMyIP(){
		return _myIP;
	}

	public void setServerName(String name){
		_serverName = name;
	}

	public String getServerName(){
		return _serverName;
	}

	public void setFileName(String fileName){
		_fileName = fileName;
	}

	public String getFileName(){
		return _fileName;
	}

	public void setType(boolean type){
		_type = type;
	}

	public boolean getType(){
		return _type;
	}

	public void setDestination(String destination){
		_destinationIP = destination;
	}

	public String getDestination(){
		return _destinationIP;
	}
}
