package application;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class ClientController {

	FXML
	/*@FXML
	public void connect(ActionEvent event){
		if (setup){
			//get ip address, port number and send to client class to establish connection
			serverRouterIPAddressString = serverRouterIPAddressField.getText();
			serverIPAddressString = serverIPAddressField.getText();
			clientIPAddressString = clientIPAddressField.getText();
			portNumber =  Integer.parseInt(portsNumberField.getText());
			
			if (server){
				// send ip addresses and port number to server class
				try {
					ser.RunTranslationServer(serverRouterIPAddressString, portNumber, clientIPAddressString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.print("failed to start server (catch statement in start menu controller connect method");
				}
			
			} else {
				// send ip addresses and port number to client class
				if (loadFile(event)){;
					try {
						cl.RunClient(serverRouterIPAddressString, portNumber, serverIPAddressString, fileNameString);
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.print("\nFailed to start client (catch statement in start menu controller connect method)");
					}
				}
				
			}
		// if connection established then switch to Chat Window
		}
	}
	
	@FXML
	public boolean loadFile(ActionEvent event){
		fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(Main.PRIMARYSTAGE_STAGE);
		if (file != null) {
            //openFile(file);
        	fileNameString = file.getAbsolutePath();
        	System.out.println("filename = " + fileNameString);
        	if (fileNameString != null){
        		return true;
        	}
        }
		return false;
	}
	}*/
	
}
