package application;

import java.io.File;
import java.net.SocketException;
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
import javafx.stage.FileChooser;

public class ClientController implements Initializable, ControlledScreen {
	ScreensController myController;
	
	@FXML				AnchorPane				rootScreenAnchorPane;
	@FXML				Label					myIPAddressLabel;
	@FXML				TextArea				messageArea;
	@FXML				TextArea 				routerLogArea;
	@FXML				TextArea 				serverLogArea;
	@FXML				TextField				routerIPAddressField;
	@FXML				TextField				serverIPAddressField;	
	@FXML				TextField				portNumField;
	@FXML				Button					exitButton;
	@FXML				Button					startStopClientButton;
	@FXML				Button					loadFilebButton;
	@FXML				Button					sendMessageButton;
			
	
	//non FXML attributes
	private 			String 					routerIPAddressString;
	private 			String 					serverIPAddressString;
	private 			String 					clientIPAddressString;
	private 			String 					messageString;
	private				String					outsideSetMessageString;
	
	private 			SocketClient			cl								= new SocketClient();
	private 			String					fileNameString					= null;
	private 			boolean 				setup	 						= false,
												fileLoaded;
	private				Timer					timer;
	private 			int						portNumber, count = 0, time = 0;
	private				SocketClient			sC;

	private FileChooser fileChooser = new FileChooser();
	
	@FXML
	public void startOrStopClient(ActionEvent event) {
		// init Server Class
		sC = new SocketClient();
		if (!setup) {
			
			try {
				
				serverIPAddressString = serverIPAddressField.getText();
				serverIPAddressField.setEditable(false);
				routerIPAddressString = routerIPAddressField.getText();
				routerIPAddressField.setEditable(false);
				portNumber = Integer.parseInt(portNumField.getText());
				portNumField.setEditable(false);
				startStopClientButton.setText("Reset Client");
				
				messageString = "\nClient Started\nRouter IP = " + routerIPAddressString
						+ "\nServer IP address = " + serverIPAddressString
						+ "\nPort Number = " + portNumber;
				messageString += messageArea.getId();
				messageArea.setText(messageString);
				sC.RunServer(routerIPAddressString, portNumber, serverIPAddressString, true);
				init();
				setup = true;
			} catch (NumberFormatException e) {
				messageArea.setText("Port Number must be a number between x - y");
				reset();
			} catch (SocketException e) {
				messageArea.setText("Client Failed to Connect");
				reset();
			}
			
		} else {
			reset();
		}
	}
	
	public void init(){
		//initialization varibles 
		timer = new Timer();
		routerIPAddressField.setText(null);
		messageArea.setWrapText(true);
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {	
						if (count == 1000){
							messageString = sC.getReport();
							messageString += messageArea.getText();
							messageArea.setText(messageString);
							if (myIPAddressLabel.getText().equals("My IP Address:")){
								routerIPAddressField.setText("My IP Address:" + sC._MyIP);
							}
							messageString = "\nTime in Seconds:  " + time;
							messageString += messageArea.getText();
							messageArea.setText(messageString);
							count = 0;
							time++;
						}
						count++;
					}
				});
			}
		}, 0, 1);
	}
	
	public void loadFile(ActionEvent event){
		File file = fileChooser.showOpenDialog(Main.PRIMARYSTAGE_STAGE);
		if (file != null) {
            //openFile(file);
			fileNameString = file.getAbsolutePath();
        	System.out.println("filename = " + fileNameString);
        	if (Main.fileName != null){
        		sendMessageButton.setVisible(true);
        		messageArea.setText("File : " + fileNameString + " Loaded" + messageArea.getText());
        	}
        }
	}
	
	
	public void reset() {
		messageArea.setText("Client settings cleared");
		serverIPAddressField.setEditable(true);
		serverIPAddressField.setText("");
		portNumField.setEditable(true);
		portNumField.setText("");
		routerIPAddressField.setEditable(true);
		routerIPAddressField.setText("");
		serverIPAddressString = null;
		routerIPAddressString = null;
		startStopClientButton.setText("Start Client");
		setup = false;
	}
	
	
	
	
	public void sendMessage(ActionEvent event) {
		// send the message
		sendMessageButton.setVisible(false);
	}

	@FXML
	public void exitToStartMenu(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		myController.setScreen(Main.STARTMENU);
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		System.out.println("setScreenParent server screen");
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootScreenAnchorPane.setStyle("-fx-background-color: lightblue");
		Main.CC = this;
		sendMessageButton.setVisible(false);
	}
}
