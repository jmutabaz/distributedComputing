package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	final 	static 	String 					CHATWINDOW 			= "ChatWindow";
	final 	static 	String 					CHATWINDOW_FXML 	= "ChatWindow.fxml";
	final 	static 	String 					START_MENU 			= "startMenu";
	final 	static 	String 					START_MENU_FXML		= "startMenu.fxml";
	//final 	static 	String 					EXIT_MENU 			= "exit";
	//final 	static 	String 					EXIT_MENU_FXML 		= "exitMenu.fxml";
	        static	Stage					PRIMARYSTAGE_STAGE;
	        static  Scene					mainScene;
			static	Group					root;
	public	static	StartMenuController		SMC;
	public	static 	ChatWindowController	CWC;
	//public	static	ExitMenuController		EMC;
	public  static	String					fileName;
	
	@Override
	public void start(Stage primaryStage) {
		PRIMARYSTAGE_STAGE = primaryStage;
		boolean gameLoad, startLoad, exitLoad;
		ScreensController mainContainer = new ScreensController();
		System.out.println("toStringcall::: " + mainContainer.toString());
		
		gameLoad 	= mainContainer.loadScreen(Main.CHATWINDOW, Main.CHATWINDOW_FXML);
		startLoad	= mainContainer.loadScreen(Main.START_MENU, Main.START_MENU_FXML);
//		exitLoad 	= mainContainer.loadScreen(Main.EXIT_MENU, Main.EXIT_MENU_FXML);
		
		System.out.println("toStringcall::: " + mainContainer.toString());
		System.out.println("game loaded? " + gameLoad + " startLoaded? " + startLoad); // + " exitLoaded? " + exitLoad);
		
		mainContainer.setScreen(Main.START_MENU);

		root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		mainScene = scene;
		Main.PRIMARYSTAGE_STAGE.setHeight(800);
		Main.PRIMARYSTAGE_STAGE.setWidth(1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
