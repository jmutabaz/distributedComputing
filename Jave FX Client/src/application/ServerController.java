package application;

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

public class ServerController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML				AnchorPane 				rootScreenAnchorPane;
	@FXML				Button 					startStopServerButton;
	@FXML				Button 					exitButton;
	@FXML				Label					myIPAddressLabel;
	@FXML				TextArea 				messageArea;
	@FXML				TextArea 				routerLogArea;
	@FXML				TextArea 				clientLogArea;
	@FXML				TextField 				clientIPAddressField;
	@FXML				TextField 				routerIPAddressField;
	@FXML				TextField 				portNumField;

	// non FXML attributes
	private 			String 					routerIPAddressString;
	private 			String 					serverIPAddressString;
	private 			String 					clientIPAddressString;
	private 			String 					messageString;
	private				String					outsideSetMessageString;
	private 			boolean 				setup 						= false;
	private				Timer					timer;
	private 			int						portNumber, count = 0, time = 0;
	private				SocketClient			sC;
		

	@FXML
	public void startOrStopServer(ActionEvent event) {
		// init Server Class
		sC = new SocketClient();
		if (!setup) {
			
			try {
				messageArea.setText("Server Started");
				clientIPAddressString = clientIPAddressField.getText();
				clientIPAddressField.setEditable(false);
				routerIPAddressString = routerIPAddressField.getText();
				routerIPAddressField.setEditable(false);
				portNumber = Integer.parseInt(portNumField.getText());
				portNumField.setEditable(false);
				startStopServerButton.setText("Reset Server");
				messageString = "Router IP = " + routerIPAddressString
						+ "\nClient IP address = " + clientIPAddressString
						+ "\nPort Number = " + portNumber;
				messageString += messageArea.getId();
				messageArea.setText(messageString);
				sC.RunServer(routerIPAddressString, portNumber, clientIPAddressString, true);
				init();
				setup = true;
			} catch (NumberFormatException e) {
				messageArea.setText("Port Number must be a number between x - y");
				reset();
			} catch (SocketException e) {
				
				reset();
			}
			
		} else {
			reset();
		}
		
	}

	@FXML
	public void exitToStartMenu(ActionEvent event) {
		// Stop and null server
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
		myController.setScreen(Main.STARTMENU);
		timer.cancel();
		timer = null;
	}
	
	public void reset() {
		messageArea.setText("Server settings cleared");
		clientIPAddressField.setEditable(true);
		clientIPAddressField.setText("");
		portNumField.setEditable(true);
		portNumField.setText("");
		routerIPAddressField.setEditable(true);
		routerIPAddressField.setText("");
		serverIPAddressString = null;
		routerIPAddressString = null;
		startStopServerButton.setText("Start Server");
		setup = false;
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
							
							
						}
						count++;
					}
				});
			}
		}, 0, 1);
	}
	
	public void updateGUI(String message) {
		outsideSetMessageString = message;
		outsideSetMessageString += messageArea.getText();
		messageArea.setText(outsideSetMessageString);
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		System.out.println("setScreenParent server screen");
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootScreenAnchorPane.setStyle("-fx-background-color: lightblue");
		Main.SC = this;
	}
}
