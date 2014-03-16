package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import application.ControlledScreen;

public class StartScreenController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML 	AnchorPane	rootScreen;
	
	@FXML 	Button		startClient;
	@FXML	Button		startServer;
	@FXML	Button		exit;
	
	@FXML	Label		testLabel;
	
	
	@FXML
	void startClientButtonPressed(ActionEvent event){
		testLabel.setText("Start Client buttton depressed");
		myController.setScreen(Main.CLIENT_SCREEN);
	}
	
	@FXML
	void startServerButtonPressed(ActionEvent event){
		testLabel.setText("Start Server buttton depressed");
		myController.setScreen(Main.SERVER_SCREEN);
	}
	
	@FXML
	public void exit(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.close();
	}
	
	
	//==============================================================================
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		rootScreen.setStyle("-fx-background-color: beige");
		Main.SC = this;
	}
}
