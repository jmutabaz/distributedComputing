package application;

/**
 * @author Clint Walker
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Scene;

public class Main extends Application {

	final static String ROUTERWINDOW = "routerWindow";
	final static String ROUTERWINDOW_FXML = "routerWindow.fxml";
	final static String SERVERWINDOW = "serverWindow";
	final static String SERVERWINDOW_FXML = "serverWindow.fxml";
	final static String CLIENTWINDOW = "clientWindow";
	final static String CLIENTWINDOW_FXML = "clientWindow.fxml";
	final static String STARTMENU = "startMenu";
	final static String STARTMENU_FXML = "startMenu.fxml";
	// final static String EXIT_MENU = "exit";
	// final static String EXIT_MENU_FXML = "exitMenu.fxml";
	static Stage PRIMARYSTAGE_STAGE;
	static Scene mainScene;
	static Group root;
	public static StartMenuController SMC;
	public static ClientController CC;
	public static ServerController SC;
	public static RouterController RC;
	// public static ExitMenuController EMC;
	public static String fileName;
	public static boolean advanced = false;

	@Override
	public void start(Stage primaryStage) {
		PRIMARYSTAGE_STAGE = primaryStage;
		boolean routerLoad, startLoad, clientLoad, serverLoad;
		ScreensController mainContainer = new ScreensController();
		System.out.println("toStringcall::: " + mainContainer.toString());

		routerLoad = mainContainer.loadScreen(Main.ROUTERWINDOW,
				Main.ROUTERWINDOW_FXML);
		serverLoad = mainContainer.loadScreen(Main.SERVERWINDOW,
				SERVERWINDOW_FXML);
		clientLoad = mainContainer.loadScreen(CLIENTWINDOW, CLIENTWINDOW_FXML);
		startLoad = mainContainer.loadScreen(Main.STARTMENU,
				Main.STARTMENU_FXML);

		System.out.println("toStringcall::: " + mainContainer.toString());
		System.out.println("router loaded? " + routerLoad + " startLoaded? "
				+ startLoad + " server loaded? " + serverLoad
				+ " client loaded? " + clientLoad); // +
		// " exitLoaded? "
		// + exitLoad);

		mainContainer.setScreen(Main.STARTMENU);

		root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		mainScene = scene;
		Main.PRIMARYSTAGE_STAGE.setWidth(600);
		Main.PRIMARYSTAGE_STAGE.setHeight(300);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public final void setOnCloseRequest(EventHandler<WindowEvent> value) {
		Platform.exit();
	}
}
