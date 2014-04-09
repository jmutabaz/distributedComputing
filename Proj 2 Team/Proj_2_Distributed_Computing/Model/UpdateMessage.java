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
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class UpdateMessage implements Serializable {
	private static String _filePath="";
	private String _message;
	private int _count;
	
	
	
	public boolean WriteFile(UpdateMessage msg)
	{
		try {
			FileOutputStream outputStream =
					new FileOutputStream(_filePath + msg._count + ".spsu", false);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(msg);
			byte[] msgBytes = (out.toByteArray());
			outputStream.write(msgBytes);
			outputStream.close();os.close();out.close();
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
			Path path = Paths.get(_filePath + filename + ".spsu");
			byte[] dataBytes = Files.readAllBytes(path);
			//dataBytes = Message.decompress(dataBytes);
			ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBytes);
			ObjectInputStream objStream = new ObjectInputStream(byteStream);
			UpdateMessage x = (UpdateMessage)objStream.readObject();
			return x;
		}
		catch(Exception ex) {
			System.out.print(ex.toString());
			return null;				
		}
		
	}
	
	//--------------------GET AND SET--------------------//
	
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
	
}
