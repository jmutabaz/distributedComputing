package application;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Model.SocketClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ServerScreenController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML 	AnchorPane	rootScreen;
	
	@FXML	Label		serversIPAddressLabel;
	@FXML	Label		runTimeLabel;
	@FXML	Label		numberOfClientsLabel;
	@FXML	Label		numberOfServersLabel;
	@FXML	Label		hostServerRouterIPAddressLabel;
	
	@FXML 	Button		startServerRouterButton;
	@FXML	Button		exitButton;
	
	@FXML	TextField	hostServerRouterIPAddressField;
	
	@FXML	TextArea	serverRuntimeLogArea;
	@FXML	TextArea	clientTableArea;
	@FXML	TextArea	serverRouterTableArea;
	
	//variables
	private			Timer 				timer;
	private			boolean				serverSetup						= true;
	private 		int 				clock 							= 0,
										counter							= 0,
										ServerPortNumber				= 5555;
	private			String				messageLogHolderString			= "";
	private			SocketClient 		cli;
	
	//private		dataType to store Tables	clientRouterTable,
	//											serverRouterTable;
	
	
	@FXML
	void exitServerButtonPressed(ActionEvent event){
		// delete all children then exit to start screen
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		myController.setScreen(Main.START_SCREEN);
	}
	
	@FXML
	void startServerButtonPressed(ActionEvent event){
		if (serverSetup){
			//	parse in IP address
			//	parse in Port Number
			//	make left pane visible 
			// 	hide message send button
			//	change Start Client button's text to read reset client
			//	start update loop
			init();
			serverSetup();
			messageLogHolderString = serverRuntimeLogArea.getText();	
			serverRuntimeLogArea.setText("Start Server-Router\n" + messageLogHolderString);
			startUpdateLoop();
			serverSetup = false;
		} else {
			messageLogHolderString = serverRuntimeLogArea.getText();	
			serverRuntimeLogArea.setText("Restart Server-Router\n" + messageLogHolderString);
			
			reset();
			serverSetup = true;
			hostServerRouterIPAddressField.requestFocus();
			
		}
	}

	
	void serverSetup(){
		boolean setup = false;
		if (!setup) {
			try {
				//Rhett - Adding ServerRouter StartUp.
				cli = new SocketClient(Main.IPADDRESSSTRING, hostServerRouterIPAddressField.getText(), "", 3, null);
				cli.start();
			} catch (NumberFormatException e) {
				messageLogHolderString = serverRuntimeLogArea.getText();
				serverRuntimeLogArea.setText("Port Number must be a number between x - y\n" + messageLogHolderString);
				reset();
			} 
		} else {
			reset();
		}
		setup = true;
	}
	
	
	
	void reset() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		cli.killMe();
		//reset all server setting
	}
	
	void init(){
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		//init all server settings
		timer = new Timer();
		clock = 0;
		counter = 0;
	}
	
	
	void startUpdateLoop(){
		messageLogHolderString = serverRuntimeLogArea.getText();
		serverRuntimeLogArea.setText("Start Update Loop\n" + messageLogHolderString);
	
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						//Do Stuff here
						counter++;
						if (counter == 1000){ // one second clock
							counter = 0;
							clock++;
							runTimeLabel.setText("" + clock);
							messageLogHolderString = serverRuntimeLogArea.getText();
							serverRuntimeLogArea.setText("Clock tick: " + clock + "\n" + messageLogHolderString);
						}
					}
				});
			}
		}, 0, 1);
	}
	
	
	
	
	
	
	
	
	//==============================================================================
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rootScreen.setStyle("-fx-background-color: lightgreen");
		
		Main.SSC = this;
	}

}
