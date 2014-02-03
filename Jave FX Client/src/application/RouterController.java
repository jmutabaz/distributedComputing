package application;

import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Model.ServerRouter;
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

public class RouterController implements Initializable, ControlledScreen{
	ScreensController myController;
	
	@FXML		AnchorPane			rootScreenAnchorPane;
	@FXML		Button 				startStopRouter;
	@FXML		Button				exitButton;
	@FXML		TextArea 			messageArea;
	@FXML		TextArea			numServersArea;
	@FXML		TextArea			numClientsArea;
	@FXML		TextField			routerIPAddressField;
	@FXML		TextField			portNumberField;
	@FXML		TextField				connectionNumField;
	@FXML		Label				numMessagesPassedlLabel;
	
	private		Timer				timer;
	private 	int					portNumber,
									connectionNumber;
	private		SocketClient		sC;
	
	
	@FXML
	public void startOrStopRouter(ActionEvent event){
		//start stop code for router goes here
		messageArea.setText("Router Started");
		try {
			connectionNumber = Integer.parseInt(connectionNumField.getText());
		} catch (Exception e) {
			// TODO: handle exception
			event.consume();
		}
		
		
		try {
			portNumber =  Integer.parseInt(portNumberField.getText());
			
		} catch (NumberFormatException e) {
			System.out.print("Entry not an Integer");
			messageArea.setText("The port Number you entered does not conform to a standard Integer");
			event.consume();
		}
		
		try{
			sC = new SocketClient();
			sC.RunServerRouter(portNumber, connectionNumber, true);
		}catch(Exception ex){
			System.out.print("Couldn't Start Server Router.");
			messageArea.setText("Couldn't Start a Server Router.");
			event.consume();
		}
		
		init();
	}
	
	@FXML
	public void exitToStartMenu(ActionEvent event){
		//stop and null router
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
		myController.setScreen(Main.STARTMENU);
		
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
						
						
						messageArea.appendText(sC.getReport());
						if (routerIPAddressField.getText() == null){
							routerIPAddressField.setText(sC._MyIP);
						}
					}
				});
			}
		}, 0, 1);
	}
	
	public void updateGUI(String message) {
		
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		System.out.println("setScreenParent router screen");
		myController = screenPage;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		rootScreenAnchorPane.setStyle("-fx-background-color: lightblue");
		Main.RC = this;
		
	}

}
