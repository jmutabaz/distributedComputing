package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


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

		root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		mainScene = scene;
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
