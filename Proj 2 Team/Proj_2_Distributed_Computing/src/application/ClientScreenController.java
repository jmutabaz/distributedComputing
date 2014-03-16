package application;

import java.io.File;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class ClientScreenController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML 	AnchorPane	rootScreen;
	
	@FXML	Pane		messageSendingPane;
	
	@FXML	Label		clientsIPAddressLabel;
	@FXML	Label		runTimeLabel;
	@FXML	Label		messagesRecievedLabel;
	@FXML	Label		recievingClientsIPAddressLabel;
	@FXML	Label		recievingClientsPortNumberLabel;
	@FXML	Label		loadedFileNameLabel;
	
	@FXML 	Button		startClientButton;
	@FXML	Button		loadFileButton;
	@FXML	Button		sendMessageButton;
	@FXML	Button		exitButton;
	
	@FXML	TextField	clientsNameField;
	@FXML	TextField	clientsPortNumberField;
	@FXML	TextField	serverRouterIPAddressField;
	@FXML	TextField	nameOfRecievingClientField;
	
	@FXML	TextArea	messageLogArea;
	
	//variables
	private			Timer 				timer;
	private			boolean				clientSetup						= true;
	private 		int 				clock 							= 0,
										counter							= 0,
										clientPortNumber				= 0;
	private			String				messageLogHolderString			= "",
										serverRouterIPAddressString		= "",
										clientNameString				= "",
										handlerClientNameString			= "",
										clientIPAddressString			= "",
										handlerClientIPAddressString	= "",
										handlerClientPortNumberString	= "",
										fileNameString					= "";
	private 		FileChooser			fileChooser 					= new FileChooser();
	
	
	@FXML
	void exitClientButtonPressed(ActionEvent event){
		// delete all children then exit to start screen
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		myController.setScreen(Main.START_SCREEN);
	}
	
	@FXML
	void startClientButtonPressed(ActionEvent event){
		if (clientSetup){
			//	parse in IP address
			//	parse in Port Number
			//	make left pane visible 
			// 	hide message send button
			//	change Start Client button's text to read reset client
			//	start update loop
			init();
			clientSetup();
			messageLogHolderString = messageLogArea.getText();	
			messageLogArea.setText("Start Client\n" + messageLogHolderString);
			startUpdateLoop();
			clientSetup = false;
		} else {
			messageLogHolderString = messageLogArea.getText();	
			messageLogArea.setText("Restart Client\n" + messageLogHolderString);
			
			reset();
			clientSetup = true;
			clientsNameField.requestFocus();
			
		}
	}
	
	@FXML
	void loadFileButtonPressed(ActionEvent event){
		//start file load process
		//display file name in message log
		//show send message button
		File file = fileChooser.showOpenDialog(Main.PRIMARYSTAGE_STAGE);
		if (file != null) {
            //openFile(file);
			fileNameString = file.getAbsolutePath();
			loadedFileNameLabel.setText(fileNameString);
        	System.out.println("filename = " + fileNameString);
        	if (fileNameString != null){
        		sendMessageButton.setVisible(true);
        		messageLogHolderString = messageLogArea.getText();
        		messageLogArea.setText("File : " + fileNameString 
        				+ " Loaded" + messageLogHolderString);
        	}
        	sendMessageButton.setVisible(true);
        }
	}
	
	@FXML
	void sendMessageButtonPressed(ActionEvent event){
		//	create socket, connect to server-router to request "Lookup" of handler client
		//	hide load file button 
		//	hide send message button (possible change to cancel send button or something
		// 	display client's information in window
		//	create peer-to-peer with handler client
		//	show conversation specifics in message log window
		//	display returned message and any other data required
		//  terminate socket connection
		//	hide send message button
		// 	show load file button
		// 	clear nameOfRecievingClientField, and recievingClientIPAddressLabel and 
		//		recievingClientsPortNumberLabel
	}
	
	void clientSetup(){
		boolean setup = false;
		if (!setup) {
			try {
				clientNameString = clientsNameField.getText();
				clientsNameField.setEditable(false);
				clientsNameField.setFocusTraversable(false);
				serverRouterIPAddressString = serverRouterIPAddressField.getText();
				serverRouterIPAddressField.setEditable(false);
				serverRouterIPAddressField.setFocusTraversable(false);
				clientPortNumber = Integer.parseInt(clientsPortNumberField.getText());
				clientsPortNumberField.setEditable(false);
				clientsPortNumberField.setFocusTraversable(false);
				startClientButton.setText("Reset Client");
				messageLogHolderString = messageLogArea.getText();
				messageLogArea.setText("Client's Name: " + clientNameString 
						+ "\nServer-Router IP: " + serverRouterIPAddressString
						+ "\nClient IP address: " + clientIPAddressString
						+ "\nPort Number: " + clientPortNumber 
						+ "\n" + messageLogHolderString);
				init();
				setup = true;
				nameOfRecievingClientField.requestFocus();
			} catch (NumberFormatException e) {
				messageLogHolderString = messageLogArea.getText();
				messageLogArea.setText("Port Number must be a number between x - y\n" + messageLogHolderString);
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
		messageLogHolderString = messageLogArea.getText();
		messageLogArea.setText("Client Reset\n" + messageLogHolderString);
		clientsNameField.setEditable(true);
		clientsNameField.setFocusTraversable(true);
		clientsNameField.setText("");
		clientsPortNumberField.setEditable(true);
		clientsPortNumberField.setFocusTraversable(true);
		clientsPortNumberField.setText("");
		serverRouterIPAddressField.setEditable(true);
		serverRouterIPAddressField.setFocusTraversable(true);
		serverRouterIPAddressField.setText("");
		serverRouterIPAddressString = null;
		startClientButton.setText("Start Server");
		loadedFileNameLabel.setText("No File Loaded"); 
		messageSendingPane.setVisible(false);
		fileNameString = "";
	}
	
	void init(){
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		messageLogHolderString = messageLogArea.getText();
		messageLogArea.setText("Reset Client\n" + messageLogHolderString);
		timer = new Timer();
		clock = 0;
		counter = 0;
		messageSendingPane.setVisible(true);
		sendMessageButton.setVisible(false);
	}
	
	
	void startUpdateLoop(){
		messageLogHolderString = messageLogArea.getText();
		messageLogArea.setText("Start Update Loop\n" + messageLogHolderString);
	
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
							//messageLogHolderString = messageLogArea.getText();
							//messageLogArea.setText("Clock tick: " + clock + "\n" + messageLogHolderString);
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
		rootScreen.setStyle("-fx-background-color: lightblue");
		
		Main.CSC = this;
	}

}
