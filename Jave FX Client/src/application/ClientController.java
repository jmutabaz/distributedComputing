package application;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class ClientController implements Initializable, ControlledScreen {
	ScreensController myController;
	
	@FXML	TextArea	messageWindowArea;
	@FXML	TextField	serverRouterIPAddressField;
	@FXML	TextField	serverIPAddressField;	
	@FXML	TextField	portNumberField;
	@FXML	Button		exitButton;
	@FXML	Button		startClientButton;
	@FXML	Button		loadFilebButton;
	@FXML	Button		sendMessageButton;
	
	
	
	private String serverRouterIPAddressString;
	private String serverIPAddressString;
	private String clientIPAddressString;
	private int portNumber;
	
	private SocketClient		cl								= new SocketClient();
	private String				fileNameString					= null;
	private boolean setup = false,
					fileLoaded;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void exitToStart(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		myController.setScreen(Main.START_MENU);
	}

	
}
