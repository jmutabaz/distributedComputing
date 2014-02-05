package application;

import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

import sun.launcher.resources.launcher;
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
	@FXML		TextField			portNumField;
	@FXML		TextField			connectionNumField;
	@FXML		Label				numMessagesPassedlLabel;
	@FXML		Label				timeLabel;
	
	private		Timer				timer;
	private 	int					portNumber, count = 0, time = 0,
									connectionNumber = 0;
	private		String				messageString, outsideSetMessageString;
	private		SocketClient		sC;
	private		boolean				setup;
	
	
	@FXML
	public void startOrStopRouter(ActionEvent event){
		
		if (!setup){
			//start stop code for router goes here
			messageArea.setText("");
			try {
				connectionNumber = Integer.parseInt(connectionNumField.getText());
			} catch (Exception e) {
				System.out.println("parse of connection number not working");
				//event.consume();
			}
			
			try {
				portNumber =  Integer.parseInt(portNumField.getText());
				
			} catch (NumberFormatException e) {
				System.out.print("Entry not an Integer");
				messageArea.setText("The port Number you entered does not conform to a standard Integer");
				reset();
				//event.consume();
			}
			
			try{
				sC = new SocketClient();
				sC.RunServerRouter(portNumber, connectionNumber, true);
			}catch(Exception ex){
				System.out.print("Couldn't Start Server Router.");
				messageArea.setText("Couldn't Start a Server Router.");
				reset();
				//event.consume();
			}
			
			messageString = "\nRouter IP = " + sC._MyIP
					+ "\nPort Number = " + portNumber
					+ "\nConnections allowed = " + connectionNumber;
			messageString += "\n" + messageArea.getId();
			messageArea.setText(messageString);
			
			startStopRouter.setText("Stop Router");
			portNumField.setText("");
			setup = true;
			init();
		} else {
			reset();
		}
	}
	
	@FXML
	public void exitToStartMenu(ActionEvent event){
		//stop and null router
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
		myController.setScreen(Main.STARTMENU);
		if (timer != null){
			timer.cancel();
		}
		timer = null;
		count = 0;
		time = 0;
		
	}
	
	
	public void init(){
		//initialization varibles 
		timer = new Timer();
		count = 0;
		time = 0;
		setup = false;
		routerIPAddressField.setText(null);
		messageArea.setWrapText(true);
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {		
						
						//messageString = sC.getReport();
						//System.out.print("\n messageString from socketCLient report :" + messageString);
						/*if (!messageArea.equals("")){
							System.out.print("\n messageString from socketCLient report :" + messageString);
							messageString = "\n" + messageString;
							messageString += "\n" + messageArea.getText();
							messageArea.setText(messageString);
						}*/
						if (routerIPAddressField.getText() == null){
							routerIPAddressField.setText(sC._MyIP);
						}
						
						if (count == 1000){
							messageString = sC.getReport();
							//System.out.print("\n messageString from socketCLient report :" + messageString);
							if (messageString != null){
								System.out.print("\n messageString from socketCLient report :" + messageString);
								messageString = "\n" + messageString;
								messageString += "\n" + messageArea.getText();
								messageArea.setText(messageString);
							}
							timeLabel.setText("Run Time: " + time);
							count = 0;
							time++;
						}
						count++;
					}
				});
			}
		}, 0, 1);
	}
	
	public void reset() {
		messageArea.setText("Router settings cleared");
		routerIPAddressField.setText("");
		portNumField.setEditable(true);
		portNumField.setText("");
		routerIPAddressField.setEditable(true);
		routerIPAddressField.setText("");
		startStopRouter.setText("Start Router");
		if (timer != null){
			timer.cancel();
			timer = null;
		}
		setup = false;
	}
	
	public void updateGUI(String message) {
		if (message != null){
			outsideSetMessageString = "\nOUTSIDE MESSAGE\n" + message;
			outsideSetMessageString += "\n" + messageArea.getText();
			messageArea.setText(outsideSetMessageString);
		}
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
