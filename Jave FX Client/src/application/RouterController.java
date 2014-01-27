package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextArea;

public class RouterController implements Initializable, ControlledScreen{
	ScreensController myController;
	
	@FXML	ButtonBase startStopRouter;
	@FXML	TextArea messageArea;
	
	
	@FXML
	public void startOrStopRouter(ActionEvent event){
		//start stop code for router goes here
		messageArea.setText("Hello John");
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
