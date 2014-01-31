package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import Model.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class ClientController implements Initializable, ControlledScreen {
	ScreensController myController;
	
	@FXML				AnchorPane			rootScreenAnchorPane;
	@FXML				TextArea			messageArea;
	@FXML				TextArea 			routerLogArea;
	@FXML				TextArea 			serverLogArea;
	@FXML				TextField			routerIPAddressField;
	@FXML				TextField			serverIPAddressField;	
	@FXML				TextField			portNumField;
	@FXML				Button				exitButton;
	@FXML				Button				startStopClientButton;
	@FXML				Button				loadFilebButton;
	@FXML				Button				sendMessageButton;
			
	
	//non FXML attributes
	private 			String 				routerIPAddressString;
	private 			String 				serverIPAddressString;
	private 			String 				clientIPAddressString;
	private 			int 				portNumber;
	
	private 			SocketClient		cl								= new SocketClient();
	private 			String				fileNameString					= null;
	private 			boolean 			setup	 						= false,
											fileLoaded;

	private FileChooser fileChooser = new FileChooser();
	
	@FXML
	public void startOrStopClient(ActionEvent event) {
		// init Server Class
		if (!setup) {
			
			try {
				messageArea.setText("Server Started");
				clientIPAddressString = serverIPAddressField.getText();
				serverIPAddressField.setEditable(false);
				routerIPAddressString = routerIPAddressField.getText();
				routerIPAddressField.setEditable(false);
				portNumber = Integer.parseInt(portNumField.getText());
				portNumField.setEditable(false);
				startStopClientButton.setText("Reset Client");
				messageArea.appendText("Router IP = " + routerIPAddressString
						+ "\nClient IP address = " + clientIPAddressString
						+ "\nPort Number = " + portNumber);
				setup = true;
			} catch (NumberFormatException e) {
				messageArea.setText("Port Number must be a number between x - y");
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
			
		} else {
			serverIPAddressField.setEditable(true);
			serverIPAddressField.setText("");
			portNumField.setEditable(true);
			portNumField.setText("");
			routerIPAddressField.setEditable(true);
			routerIPAddressField.setText("");
			clientIPAddressString = null;
			routerIPAddressString = null;
			startStopClientButton.setText("Start Client");
			setup = false;
		}
	}
	
	public void loadFile(ActionEvent event){
		File file = fileChooser.showOpenDialog(Main.PRIMARYSTAGE_STAGE);
		if (file != null) {
            //openFile(file);
			fileNameString = file.getAbsolutePath();
        	System.out.println("filename = " + fileNameString);
        	if (Main.fileName != null){
        		
        		messageArea.setText("File : " + fileNameString + " Loaded");
        	}
        }
	}
	
	public void sendMessage(ActionEvent event) {
		// send the message
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
	}
}
