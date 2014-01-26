/**
 * @author Clint Walker
 */

package application;


import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
	private boolean 	server = false; // defaults to client mode
	private String 		serverRouterIPAddressString 	= null;
	private String		serverIPAddressString			= null; 
	private int			portNumber						= 0;
	//private TranslationServer	server						= null;
	//private SocketClient		client						= null;
	
	
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
		//server server = new TranslationServer();
		//client = null;
	}
	
	@FXML
	public void startAsClient(ActionEvent event){
		server = false;
		//client = new SocketClient();
		//server = null;
	}
	
	@FXML
	public void connect(ActionEvent event){
		//get ip address, port number and send to client class to establish connection
		serverRouterIPAddressString = serverRouterIPAddressField.getText();
		serverIPAddressString = serverIPAddressField.getText();
		portNumber =  Integer.parseInt(portsNumberField.getText());
		
		if (server){
			// send ip addresses and port number to server class
		} else {
			// send ip addresses and port number to client class
		}
		// if connection established then switch to Chat Window
		
	}

}
