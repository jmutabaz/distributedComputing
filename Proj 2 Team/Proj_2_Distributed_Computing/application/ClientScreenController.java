package application;

import java.io.File;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;

import Model.Message;
import Model.SocketClient;
import Model.UpdateMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class ClientScreenController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML 	AnchorPane	rootScreen;

	@FXML	Pane		messageSendingPane;

	@FXML	Label		clientsIPAddressLabel;
	@FXML	Label		runTimeLabel;
	@FXML	Label		messagesRecievedLabel;
	@FXML	Label		recievingClientsIPAddressLabel;
	@FXML	Label		recievingClientsPortNumberLabel;

	@FXML 	Button		startClientButton;
	@FXML	Button		loadFileButton;
	@FXML	Button		sendMessageButton;
	@FXML	Button		exitButton;

	@FXML	TextField	clientsNameField;
	@FXML	TextField	serverRouterIPAddressField;
	@FXML	TextField	nameOfRecievingClientField;

	@FXML	TextArea	messageLogArea;
	@FXML	TextArea	messgaeToSendArea;
	@FXML	TextArea	fileMessageBoxArea;

	//variables
	private			Timer 				timer;
	private			boolean				clientSetup						= true;
	private 		int 				clock 							= 0,
										counter							= 0,
										updateCounter					= 0;
	private			String				messageLogHolderString			= "",
										serverRouterIPAddressString		= "",
										clientNameString				= "",
										handlerClientNameString			= "",
										clientIPAddressString			= "",
										handlerClientIPAddressString	= "",
										fileNameString					= "",
										messageToSendString				= "";
	private 		FileChooser			fileChooser 					= new FileChooser();
	private			SocketClient		clientConn						;
	private			SocketClient		serverConn						;
	private			ArrayList<String> 	list							;

	@FXML
	void exitClientButtonPressed(ActionEvent event){
		// delete all children then exit to start screen
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		myController.setScreen(Main.START_SCREEN);
	}

	@FXML
	void startClientButtonPressed(ActionEvent event){
		if (clientSetup){
			init();
			clientSetup();
			messageLogHolderString = messageLogArea.getText();	
			messgaeToSendArea.setText("");
			messageLogArea.setText("Starting Peer\n" + messageLogHolderString);
			startUpdateLoop();
			clientSetup = false;
		} else {
			messageLogHolderString = messageLogArea.getText();	
			messageLogArea.setText("Restarting Peer\n" + messageLogHolderString);

			reset();
			clientSetup = true;
			clientsNameField.requestFocus();

		}
	}

	@FXML
	void loadFileButtonPressed(ActionEvent event){
		//start file load process
		//display file name in message log
		//show send message button
		File file = fileChooser.showOpenDialog(Main.PRIMARYSTAGE_STAGE);
		if (file != null) {
			//openFile(file);
			fileNameString = file.getAbsolutePath();
			fileMessageBoxArea.setText(fileNameString);
			System.out.println("filename = " + fileNameString);
			if (fileNameString != null){
				messageLogHolderString = messageLogArea.getText();
				messageLogArea.setText("File : " + fileNameString 
						+ " Loaded\n" + messageLogHolderString);
			}
		}
	}
	
	
	

	@FXML
	void sendMessageButtonPressed(ActionEvent event){
		boolean flag = true;
		Message msg = new Message();
		msg.setMyIP(Main.IPADDRESSSTRING);
		msg.setServerName(nameOfRecievingClientField.getText());
		msg.setClient(clientsNameField.getText());
		//Sets Up message depending on text area.
		//If true, string to cap, false is a file.
		if (!messgaeToSendArea.getText().equals("")){
			msg.setData(messgaeToSendArea.getText()); // the string to send
			msg.setType(true); // true if string false if file
		} else {
			if (!fileNameString.equals("")){
				flag = msg.readFileIntoData(fileNameString); // file to send
				msg.setType(false);
				File f = new File(fileNameString);
				msg.setFileName(f.getName());
			}
			else{
				flag = false;
			}
		}
		if(flag){
			msg.setStartTime();
			clientConn = new SocketClient(Main.IPADDRESSSTRING, serverRouterIPAddressField.getText(), nameOfRecievingClientField.getText(), 2, msg);
			clientConn.start();
		}
		messageSendingPane.setStyle("-fx-background-color: lightblue");
		fileNameString = "";
		fileMessageBoxArea.setText("");
		messgaeToSendArea.setText("");
	}

	void clientSetup(){
		try {
			clientNameString = clientsNameField.getText();
			clientsNameField.setEditable(false);
			clientsNameField.setFocusTraversable(false);
			serverRouterIPAddressString = serverRouterIPAddressField.getText();
			serverRouterIPAddressField.setEditable(false);
			serverRouterIPAddressField.setFocusTraversable(false);
			startClientButton.setText("Reset Peer");
			init();
			messageSendingPane.setVisible(true);
			nameOfRecievingClientField.requestFocus();
			//Start a Server thread
			serverConn = new SocketClient(Main.IPADDRESSSTRING, serverRouterIPAddressString, clientNameString, 1, null);
			serverConn.start();


		} catch (Exception e) {
			//messageLogHolderString = messageLogArea.getText();
			//messageLogArea.setText("Problem in Client setup\n" + messageLogHolderString);
			System.out.println("Problem in Client setup\n" + messageLogHolderString);
			reset();
		}
	}



	void reset() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		messageLogHolderString = messageLogArea.getText();
		messageLogArea.setText("Peer Reset\n" + messageLogHolderString);
		clientsNameField.setEditable(true);
		clientsNameField.setFocusTraversable(true);
		clientsNameField.setText("");
		serverRouterIPAddressField.setEditable(true);
		serverRouterIPAddressField.setFocusTraversable(true);
		serverRouterIPAddressField.setText("");
		serverRouterIPAddressString = "";
		startClientButton.setText("Start Peer");
		fileMessageBoxArea.setText(""); 
		messageSendingPane.setVisible(false);
		fileNameString = "";
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (serverConn != null){
			serverConn.killMe();
			serverConn = null;
		}
	}

	void init(){
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (serverConn != null){
			serverConn.killMe();
			serverConn = null;
		}
		timer = new Timer();
		clock = 0;
		counter = 0;
		messageSendingPane.setVisible(true);
		
	}


	void startUpdateLoop(){
		//Getting the Text.
		//messageLogHolderString = messageLogArea.getText();
		//Add new at top of box.
		//messageLogArea.setText("Start Update Loop\n" + messageLogHolderString);
		System.out.println("Client Controller sees the path as: " + Main.PATHTOUPDATEString);
		clientsIPAddressLabel.setText(Main.IPADDRESSSTRING);
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						counter++;
						updateCounter++;
						if (counter == 1000){ // one second clock
							counter = 0;
							clock++;
							runTimeLabel.setText("" + clock);
							String data;
						}
						if(updateCounter == 250){
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
							if (list != null){
								if (list.size() > 0){
									System.out.println("Client controller file list size = " + list.size());
								}
								for (int i = 0; i < list.size(); i++){
									String messagePathString = list.get(i);
									System.out.println("Client message path name: " + messagePathString);
									UpdateMessage updateMessage = UpdateMessage.ReadFile(messagePathString);
									if (updateMessage != null){
										//messageLogHolderString = messageLogArea.getText();
										//messageLogArea.setText(list.get(i) + "\n" + Main.PATHTOUPDATEString + "/" + list.get(i) + "\n" + messageLogHolderString);
										if (updateMessage._shouldRestart){
											//messageLogHolderString = messageLogArea.getText();
											//messageLogArea.setText("Error Sending message to remote client" + "\n" + messageLogHolderString);
											System.out.println("Error Sending message to remote client" + "\n" + messageLogHolderString);
											
										}
										if (updateMessage._clientNotFound){
											messageSendingPane.setStyle("-fx-background-color: red");
											nameOfRecievingClientField.requestFocus();
										}
										if (updateMessage._isRouter){ 
											System.out.println(" Error router message in Client ");
										}
										if (updateMessage.get_message() != null){
											messageLogHolderString = messageLogArea.getText();
											messageLogArea.setText(updateMessage.get_message() + "\n" + messageLogHolderString);
										}
									} else {
										System.out.println("\nThe update associated with file: " + messagePathString + " is null");
									}
									// delete the file
									//System.out.println("delete file: " + messagePathString);
									File file = new File(Main.PATHTOUPDATEString + messagePathString);
									file.delete();
								}
								list.clear();
							}
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
		rootScreen.setStyle("-fx-background-color: lightblue");
		clientsIPAddressLabel.setText(Main.IPADDRESSSTRING);
		Main.CSC = this;
	}

}
