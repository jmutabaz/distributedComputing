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

public class UpdateMessage implements Serializable {
	
	public String filePath;
	public String message;
	
	public boolean WriteFile(UpdateMessage msg)
	{
		
		return false;
	}
	
	public static UpdateMessage ReadFile(String filename)
	{
		try {
			Path path = Paths.get(filename);
			byte[] dataBytes = Files.readAllBytes(path);
			dataBytes = Message.compress(dataBytes);
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
	
}
