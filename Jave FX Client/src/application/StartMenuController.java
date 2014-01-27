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

	@FXML	Button		startAsServerButton;
	@FXML	Button		startAsClientButton;
	@FXML   Button 		startAsRouterButton;
	@FXML 	Button		exitButton;

	@FXML	Label		statusLabel;
	
	//non FXML variables
	private boolean 			server 							= false; // defaults to client mode
	private boolean				setup							= false;
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
		setup = true;
		server = true;
		ser = new TranslationServer();
		cl = null;
		statusLabel.setText("Start as Server");
	}
	
	@FXML
	public void startAsClient(ActionEvent event){
		setup = true;
		server = false;
		cl = new SocketClient();
		ser = null;
		statusLabel.setText("Start as Client");
	}
	@FXML 
	public void startAsRouter(ActionEvent event)
	{
		
	}
	
	@FXML
	public void exit(ActionEvent event)
	{
		
	}
	

}
