package application;


import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class StartMenuController implements Initializable, ControlledScreen {
	ScreensController myController;
	
	@FXML 	TextField	iPAddress;
	@FXML	TextField	portNumber;
	@FXML	Button		connect;
	@FXML 	Label		ErrorMessages;
	@FXML	Label		Directions;
	
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		System.out.println("setScreenParent Game Screen");
		myController = screenPage;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Main.SMC = this;
	}
	
	@FXML
	public void connectToServer(ActionEvent event){
		//get ip address, port number and send to client class to establish connection
		
		// if connection established then switch to Chat Window
		
	}

}
