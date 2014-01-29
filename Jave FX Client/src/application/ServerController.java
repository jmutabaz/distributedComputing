package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	@FXML	ButtonBase startStopServer;
	@FXML	TextArea messageArea;
	
	@FXML
	public void startOrStopServer(ActionEvent event){
		
		messageArea.setText("Awesome ninja");
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	
}
