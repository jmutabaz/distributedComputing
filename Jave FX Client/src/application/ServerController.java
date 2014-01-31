package application;

import java.net.URL;
import java.util.ResourceBundle;
import Model.TranslationServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ServerController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML				AnchorPane 				rootScreenAnchorPane;
	@FXML				Button 					startStopServerButton;
	@FXML				Button 					exitButton;
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
	private 			int 					portNumber;
	private 			boolean 				setup 						= false;

	private 			TranslationServer 		sr 							= new TranslationServer();

	@FXML
	public void startOrStopServer(ActionEvent event) {
		// init Server Class
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
				messageArea.appendText("Router IP = " + routerIPAddressString
						+ "\nClient IP address = " + clientIPAddressString
						+ "\nPort Number = " + portNumber);
				setup = true;
			} catch (NumberFormatException e) {
				messageArea.setText("Port Number must be a number between x - y");
				clientIPAddressField.setEditable(true);
				clientIPAddressField.setText("");
				portNumField.setEditable(true);
				portNumField.setText("");
				routerIPAddressField.setEditable(true);
				routerIPAddressField.setText("");
				clientIPAddressString = null;
				routerIPAddressString = null;
				startStopServerButton.setText("Start Server");
				setup = false;
			}
			
		} else {
			clientIPAddressField.setEditable(true);
			clientIPAddressField.setText("");
			portNumField.setEditable(true);
			portNumField.setText("");
			routerIPAddressField.setEditable(true);
			routerIPAddressField.setText("");
			clientIPAddressString = null;
			routerIPAddressString = null;
			startStopServerButton.setText("Start Server");
			setup = false;
		}
	}

	@FXML
	public void exitToStartMenu(ActionEvent event) {
		// Stop and null server
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
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
		Main.SC = this;
	}
}
