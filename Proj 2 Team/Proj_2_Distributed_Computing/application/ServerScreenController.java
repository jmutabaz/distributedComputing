package application;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Model.SocketClient;
import Model.UpdateMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ServerScreenController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML 	AnchorPane	rootScreen;
	
	@FXML	Label		serversIPAddressLabel;
	@FXML	Label		runTimeLabel;
	@FXML	Label		numberOfClientsLabel;
	@FXML	Label		numberOfServersLabel;
	@FXML	Label		hostServerRouterIPAddressLabel;
	
	@FXML 	Button		startServerRouterButton;
	@FXML	Button		exitButton;
	
	@FXML	TextField	hostServerRouterIPAddressField;
	
	@FXML	TextArea	serverRuntimeLogArea;
	@FXML	TextArea	clientTableArea;
	@FXML	TextArea	routerTableArea;
	
	//variables
	private			Timer 				timer;
	private			boolean				serverSetup						= true;
	private 		int 				clock 							= 0,
										counter							= 0,
										updateCounter					= 0,
										ServerPortNumber				= 5555;
	private			String				messageLogHolderString			= "";
	private			SocketClient 		cli;
	
	private			ArrayList<String> 	list;
	//private		dataType to store Tables	clientRouterTable,
	//											serverRouterTable;
	
	
	@FXML
	void exitServerButtonPressed(ActionEvent event){
		// delete all children then exit to start screen
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		myController.setScreen(Main.START_SCREEN);
	}
	
	@FXML
	void startServerButtonPressed(ActionEvent event){
		if (serverSetup){
			//	parse in IP address
			//	parse in Port Number
			//	make left pane visible 
			// 	hide message send button
			//	change Start Client button's text to read reset client
			//	start update loop
			init();
			serverSetup();
			messageLogHolderString = serverRuntimeLogArea.getText();	
			serverRuntimeLogArea.setText("Start Server-Router\n" + messageLogHolderString);
			startUpdateLoop();
			serverSetup = false;
		} else {
			messageLogHolderString = serverRuntimeLogArea.getText();	
			serverRuntimeLogArea.setText("Restart Server-Router\n" + messageLogHolderString);
			
			reset();
			serverSetup = true;
			hostServerRouterIPAddressField.requestFocus();
			
		}
	}

	
	void serverSetup(){
		boolean setup = false;
		if (!setup) {
			try {
				//Rhett - Adding ServerRouter StartUp.
				cli = new SocketClient(Main.IPADDRESSSTRING, hostServerRouterIPAddressField.getText(), "", 3, null);
				cli.start();
			} catch (NumberFormatException e) {
				messageLogHolderString = serverRuntimeLogArea.getText();
				serverRuntimeLogArea.setText("Port Number must be a number between x - y\n" + messageLogHolderString);
				reset();
			} 
		} else {
			reset();
		}
		setup = true;
	}
	
	
	
	void reset() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		cli.killMe();
		//reset all server setting
	}
	
	void init(){
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		//init all server settings
		timer = new Timer();
		clock = 0;
		counter = 0;
	}
	
	
	void startUpdateLoop(){
		messageLogHolderString = serverRuntimeLogArea.getText();
		serverRuntimeLogArea.setText("Start Update Loop\n" + messageLogHolderString);
	
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						//Do Stuff here
						counter++;
						updateCounter++;
						if (counter == 1000){ // one second clock
							counter = 0;
							clock++;
							runTimeLabel.setText("" + clock);
							//messageLogHolderString = serverRuntimeLogArea.getText();
							//serverRuntimeLogArea.setText("Clock tick: " + clock + "\n" + messageLogHolderString);
						}
						if(updateCounter == 2500){
							updateCounter = 0;
							try{
								
								list = new ArrayList<String>();
								File[] files = new File(Main.PATHTOUPDATEString).listFiles();
								files.toString();
								for (File file : files) {
								    if (file.isFile()) {
								        list.add(file.getName());
								    }
								}
								
								
							} catch(Exception e){
								System.out.println("Problem opening folder");
								//messageLogHolderString = messageLogArea.getText();
								//messageLogArea.setText("Problem opening folder" + "\n" + messageLogHolderString);
							}

						}
						
						
						for (int i = 0; i < list.size(); i++){
							String messagePathString = list.get(i);
							System.out.println("servercontroller messagepath " + messagePathString);
							UpdateMessage updateMessage = UpdateMessage.ReadFile(list.get(i));
							//messageLogHolderString = serverRuntimeLogArea.getText();
							//serverRuntimeLogArea.setText(list.get(i) + "\n" + Main.PATHTOUPDATEString + list.get(i) + "\n" + messageLogHolderString);
							if (updateMessage != null){
								if (updateMessage._isRouter){
									messageLogHolderString = "";
									//checks or an updated client list and replaces the current list with the new list
									if(updateMessage._myServers != null){
										for(int s = 0; s < updateMessage._myServers.size(); s++){
											messageLogHolderString += updateMessage._myServers.get(i).getServerName() + "\n";
										}
										clientTableArea.setText(messageLogHolderString);
									} else {
										System.out.println("No change to client table or it is null");
									}
									messageLogHolderString = "";
									//checks for an updated router table and replaces the current list with the new list
									if (updateMessage._routerList != null){
										for(int r = 0; r < updateMessage._routerList.size(); r++){
											messageLogHolderString += updateMessage._routerList.get(i) + "\n";
										}
										routerTableArea.setText(messageLogHolderString);
									} else {
										System.out.println("No change to router table or it is null");
									}
	
								}
								if (updateMessage._fileName != null){
									messageLogHolderString = serverRuntimeLogArea.getText();
									serverRuntimeLogArea.setText("File: " + updateMessage._fileName + " has been received." + "\n" + messageLogHolderString);
								}
								if (updateMessage.get_message() != null){
									messageLogHolderString = serverRuntimeLogArea.getText();
									serverRuntimeLogArea.setText(updateMessage.get_message() + "\n" + messageLogHolderString);
								}
								
							} else {
								System.out.println("\nThe update associated with file: " + messagePathString + " is null");
							}
							System.out.println("delete file: " + messagePathString);
							File file = new File(Main.PATHTOUPDATEString + messagePathString);
							file.delete();
						}
						
						
						
					}
				});
			}
		}, 0, 1);
	}
	
	
	
	
	
	
	
	
	//==============================================================================
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rootScreen.setStyle("-fx-background-color: lightgreen");
		
		Main.SSC = this;
	}

}
