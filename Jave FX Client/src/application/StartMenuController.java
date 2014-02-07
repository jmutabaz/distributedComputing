/**
 * @author Clint Walker
 */

package application;


import java.net.URL;
import java.util.ResourceBundle;

import application.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class StartMenuController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML	AnchorPane	rootScreenAnchorPane;
	@FXML	Button		startAsServerButton;
	@FXML	Button		startAsClientButton;
	@FXML   Button 		startAsRouterButton;
	@FXML 	Button		exitButton;
	@FXML	Button		advancedButton;
	@FXML	Label		statusLabel;
	
	//non FXML variables
	private boolean 			server 							= false; // defaults to client mode
	private int					portNumber						= 0;
	private String				fileNameString					= null;
	private FileChooser 		fileChooser;
	
	
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
		rootScreenAnchorPane.setStyle("-fx-background-color: lightblue");
		Main.PRIMARYSTAGE_STAGE.setHeight(rootScreenAnchorPane.getHeight());
		Main.PRIMARYSTAGE_STAGE.setWidth(rootScreenAnchorPane.getWidth());
	}
	
	@FXML
	public void startAsServer(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.setWidth(800);
		Main.PRIMARYSTAGE_STAGE.setHeight(600);
		myController.setScreen(Main.SERVERWINDOW);
	}
	
	@FXML
	public void startAsClient(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.setWidth(800);
		Main.PRIMARYSTAGE_STAGE.setHeight(600);
		myController.setScreen(Main.CLIENTWINDOW);
	}
	@FXML 
	public void startAsRouter(ActionEvent event)
	{
		Main.PRIMARYSTAGE_STAGE.setWidth(800);
		Main.PRIMARYSTAGE_STAGE.setHeight(600);
		myController.setScreen(Main.ROUTERWINDOW);
	}
	
	@FXML
	public void exit(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.close();
	}
	
	@FXML
	public void advanced(ActionEvent event){
		if (Main.advanced){
			advancedButton.setText("Normal");
			Main.advanced = false;
		} else {
			advancedButton.setText("Advanced");
			Main.advanced = true;
		}
	}
	

}
