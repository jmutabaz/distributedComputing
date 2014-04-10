package Model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import application.Main;

public class UpdateMessage implements Serializable {
	private static String _filePath = Main.PATHTOUPDATEString;
	private String _message;
	private int _count;
	public String _fileName;
	public List<ServerID> _myServers;
	public List<String> _routerList;
	public boolean _shouldRestart;
	public boolean _isRouter;
	
	public boolean WriteFile(UpdateMessage msg)
	{
		System.out.println("Write message tostring: " + msg.toString());
		try {
			FileOutputStream outputStream =
					new FileOutputStream(_filePath + _fileName + ".spsu", false);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(msg);
			byte[] msgBytes = (out.toByteArray());
			outputStream.write(msgBytes);
			outputStream.close();
			os.close();
			out.close();
			return true;
		}
		catch(Exception ex) {
			System.out.print("I died..." + ex.toString());
			return false;
		}
	}
	
	public static UpdateMessage ReadFile(String filename)
	{
		try {
			Path path = Paths.get(_filePath + filename);
			byte[] dataBytes = Files.readAllBytes(path);
			//dataBytes = Message.decompress(dataBytes);
			ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBytes);
			ObjectInputStream objStream = new ObjectInputStream(byteStream);
			UpdateMessage x = (UpdateMessage)objStream.readObject();
			System.out.println("readfile message toString: " + x.toString());
			return x;
		}
		catch(Exception ex) {
			System.out.print(ex.toString());
			return null;				
		}
		
	}
	
	//--------------------GET AND SET--------------------//
	
	public void setRouterList(List<String> in){
		_routerList = new ArrayList<String>();
		for(int i = 0; i < in.size(); i++){
			_routerList.add(in.get(i));
		}
	}
	public List<String> getRouterList(){
		return _routerList;
	}
	
	public void setServerList(List<ServerID> in){
		_myServers = new ArrayList<ServerID>();
		for(int i = 0; i < in.size(); i++){
			_myServers.add(in.get(i));
		}
	}
	public List<ServerID> getServerList(){
		return _myServers;
	}
	
	
	public void setCount(int count){
		_count = (count > 0 ? count:-1);
	}
	
	public int getCount(int count){
		return _count;
	}
	
	public void setMessage(String msg){
		_message = msg;
	}
	
	public String getMessage(int count){
		return _message;
	}
	public String get_message() {
		return _message;
	}

	@Override
	public String toString() {
		String resultString =  "\n[\nUpdateMessage _message=" + _message + ", _count=" + _count
				+ ", _fileName=" + _fileName + ", _shouldRestart="
				+ _shouldRestart + ", _isRouter=" + _isRouter + "\n";
		String listString = "";
		if (_myServers != null){
			listString += "_myServers size = " + _myServers.size() + "\n";
			for (int i = 0; i < _myServers.size(); i++){
				listString += _myServers.get(i).getServerName() + " :: "+ _myServers.get(i).getServerIP() + "\n";
			}
			resultString += "\nServer List:\n";
			resultString += listString;
		}
		listString = "";
		if(_routerList != null){
			listString += "_routerLsit size = " + _routerList.size() + "\n";
			for (int i = 0; i < _routerList.size(); i++){
				listString += _routerList.get(i) + "\n";
			}
			resultString += "\nRouter List:\n";
			resultString += listString;
		}
		resultString += "]";
		return resultString;
	}

	//-------------------- To String --------------------//
	
	
	
	
}
