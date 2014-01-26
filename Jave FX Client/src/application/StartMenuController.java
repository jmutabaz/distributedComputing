/**
 * @author Clint Walker
 */

package application;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.SocketClient;
import Model.TranslationServer;
import application.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class StartMenuController implements Initializable, ControlledScreen {
	ScreensController myController;
	
	@FXML 	TextField	serverIPAddressField;
	@FXML	TextField	serverRouterIPAddressField;
	@FXML	TextField	clientIPAddressField;
	@FXML	TextField	portsNumberField;
	@FXML	Button		connecButton;
	@FXML	Button		startAsServerButton;
	@FXML	Button		startAsClientButton;
	@FXML 	Label		errorMessagesLabel;
	@FXML	Label		directionsLabel;
	@FXML	Label		client1Label;
	@FXML	Label		client2Label;
	@FXML	Label		client3Label;
	@FXML	Label		statusLabel;
	
	//non FXML variables
	private boolean 			server 							= false; // defaults to client mode
	private String 				serverRouterIPAddressString 	= null;
	private String				serverIPAddressString			= null; 
	private String				clientIPAddressString			= null;
	private int					portNumber						= 0;
	private TranslationServer	ser								= null;
	private SocketClient		cl								= new SocketClient();
	private String				fileNameString					= null;
	private FileChooser 		fileChooser;
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		System.out.println("setScreenParent Game Screen");
		myController = screenPage;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Main.SMC = this;
	}
	
	@FXML
	public void startAsServer(ActionEvent event){
		server = true;
		ser = new TranslationServer();
		cl = null;
		statusLabel.setText("Start as Server");
	}
	
	@FXML
	public void startAsClient(ActionEvent event){
		server = false;
		cl = new SocketClient();
		ser = null;
		statusLabel.setText("Start as Client");
	}
	
	@FXML
	public void connect(ActionEvent event){
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

}
