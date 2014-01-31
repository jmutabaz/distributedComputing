package application;

import java.net.URL;
import java.util.ResourceBundle;

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
	@FXML		Label				numMessagesPassedlLabel;
	
	
	@FXML
	public void startOrStopRouter(ActionEvent event){
		//start stop code for router goes here
		messageArea.setText("Router Started");
		
	}
	
	@FXML
	public void exitToStartMenu(ActionEvent event){
		//stop and null router
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		Main.PRIMARYSTAGE_STAGE.setHeight(300);
		myController.setScreen(Main.STARTMENU);
		
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
