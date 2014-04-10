package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.media.sound.ModelAbstractChannelMixer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StartScreenController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML 	AnchorPane	rootScreen;
	
	@FXML 	Button		startClient;
	@FXML	Button		startServer;
	@FXML	Button		exit;
	
	@FXML	Label		testLabel;
	@FXML	Label		iPAddressErrorLabel;
	
	@FXML	TextField	iPAddressTextField;
	
	
	@FXML
	void startClientButtonPressed(ActionEvent event){
		testLabel.setText("Start Client buttton depressed");
		if(getIPAddress()){
			myController.setScreen(Main.CLIENT_SCREEN);
		} else {
			iPAddressTextField.requestFocus();
		}
	}
	
	@FXML
	void startServerButtonPressed(ActionEvent event){
		testLabel.setText("Start Server buttton depressed");
		if(getIPAddress()){
			myController.setScreen(Main.SERVER_SCREEN);
		} else {
			iPAddressTextField.requestFocus();
		}
		
	}
	
	@FXML
	public void exit(ActionEvent event){
		Main.PRIMARYSTAGE_STAGE.close();
	}
	
	@FXML
	private boolean getIPAddress() {
		System.out.println("checking IP address");
		boolean getIPAddress = true;
		
		while(getIPAddress){
			Main.IPADDRESSSTRING = iPAddressTextField.getText();
			System.out.println("IP address = " + Main.IPADDRESSSTRING);
			// test IP address
			 if (!validateIP(Main.IPADDRESSSTRING)){
			 	iPAddressErrorLabel.setText("The IP Address you entered is not valid please enter a valid IP Address");
			 	System.out.println("The IP Address you entered is not valid please enter a valid IP Address");
			 	return false;
			 } else{
			 	getIPAddress = false;
			 	System.out.println("The IP address has been set as: " + Main.IPADDRESSSTRING);
			 	return true;
			 }
			 
		}
		return false;
	}
	
	public boolean validateIP(String IP)
	{
		String[] n = IP.split("\\.");
		
		try{
			if(IP.matches(".*[a-zA-Z]+.*"))
			{
				System.out.println("OOPS!!!Wrong IP format used.");
				return false;
			}
		}catch(Exception e)
		{
			//System.err.println(e.toString());
		}
		
		if((Integer.parseInt(n[0]) < 0) || (Integer.parseInt(n[0]) > 255 ))
		{
			return false;
		}
		else if((Integer.parseInt(n[1]) < 0) || (Integer.parseInt(n[1]) > 255 ))
		{
			return false;
		}
		else if((Integer.parseInt(n[2]) < 0) || (Integer.parseInt(n[2]) > 255 ))
		{
			return false;
		}
		else if((Integer.parseInt(n[3]) < 0) || (Integer.parseInt(n[3]) > 255 ))
		{
			return false;
		}
		return true;
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
