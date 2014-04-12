//Version 0.8
package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import Model.UpdateMessage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class Main extends Application {
	
	final 	static 	String 						CLIENT_SCREEN 			= "ClientScreen";
	final 	static 	String 						CLIENT_SCREEN_FXML		= "ClientScreen.fxml";
	final 	static 	String 						START_SCREEN 			= "StartScreen";
	final 	static 	String 						START_SCREEN_FXML		= "StartScreen.fxml";
	final 	static 	String 						SERVER_SCREEN			= "ServerScreen";
	final 	static 	String 						SERVER_SCREEN_FXML 		= "ServerScreen.fxml";
	        static	Stage						PRIMARYSTAGE_STAGE;
	        static  Scene						mainScene;
			static	Group						root;
	public	static	StartScreenController		SC;
	public	static 	ClientScreenController		CSC;
	public	static	ServerScreenController		SSC;
	public  static	String						fileName;
	public 	static 	String						IPADDRESSSTRING;
	public  static	String						PATHTOUPDATEString;
	public	static	String						PATHFILESAVEString;
	public  static  String						LOCALHOSTIPADDRESString;
	public  static  String						INETHOSTIPADDRESString;

	
	
	
	//http://stackoverflow.com/questions/8083479/java-getting-my-ip-address
	public static String getIpAddress() 
	{ 
	        URL myIP;
	        try {
	            myIP = new URL("http://api.externalip.net/ip/");

	            BufferedReader in = new BufferedReader(
	                    new InputStreamReader(myIP.openStream())
	                    );
	            return in.readLine();
	        } catch (Exception e) 
	        {
	            try 
	            {
	                myIP = new URL("http://myip.dnsomatic.com/");

	                BufferedReader in = new BufferedReader(
	                        new InputStreamReader(myIP.openStream())
	                        );
	                return in.readLine();
	            } catch (Exception e1) 
	            {
	                try {
	                    myIP = new URL("http://icanhazip.com/");

	                    BufferedReader in = new BufferedReader(
	                            new InputStreamReader(myIP.openStream())
	                            );
	                    return in.readLine();
	                } catch (Exception e2) {
	                    e2.printStackTrace(); 
	                }
	            }
	        }

	    return null;
	}

	
	
	@Override
	public void start(Stage primaryStage) {
		PRIMARYSTAGE_STAGE = primaryStage;
		boolean startLoad, clientLoad, serverLoad;
		ScreensController mainContainer = new ScreensController();
		System.out.println("toStringcall::: " + mainContainer.toString());
		
		startLoad 	= mainContainer.loadScreen(Main.START_SCREEN, Main.START_SCREEN_FXML);
		clientLoad	= mainContainer.loadScreen(Main.CLIENT_SCREEN, Main.CLIENT_SCREEN_FXML);
		serverLoad 	= mainContainer.loadScreen(Main.SERVER_SCREEN, Main.SERVER_SCREEN_FXML);
		
		System.out.println("toStringcall::: " + mainContainer.toString());
		System.out.println("Start loaded? " + startLoad + " clientloaded? " + clientLoad + " serverLoaded? " + serverLoad);
		
		mainContainer.setScreen(Main.START_SCREEN);

			try {
				LOCALHOSTIPADDRESString = Inet4Address.getLocalHost().getHostAddress();
				System.out.println("My Host name is: " +  " IP address is: " + Inet4Address.getLocalHost().getHostAddress() + "");
				INETHOSTIPADDRESString = getIpAddress();
				System.out.println(getIpAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not get local IP address in Main");
			}
			
		
			
		
			
		root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		mainScene = scene;
		primaryStage.setScene(scene);
		primaryStage.show();
		
		File file1 = new File(".");  
		try {
			System.out.println("Current dir : " + file1.getCanonicalPath());
			String path = file1.getCanonicalPath() + "/POBox/";
			System.out.println("the path is " + path);
			PATHTOUPDATEString = path;
			path = file1.getCanonicalPath() + "/ReceivedFiles/";
			System.out.println("Updated path name: " + PATHTOUPDATEString);
			PATHFILESAVEString = path;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("problem getting canonical path");
		}
	
		UpdateMessage updateMessage = new UpdateMessage();
		System.out.println("Update message toString test: " + updateMessage.toString());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
