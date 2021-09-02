package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    // TODO = possibly add other menus?

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        MediaPlayer player = music();
        player.setVolume(0.2);
        //player.play();
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load the main menu
        ShopMenuController shopMenuController = new ShopMenuController();
        FXMLLoader shopMenuLoader = new FXMLLoader(getClass().getResource("ShopMenuView.fxml"));
        shopMenuLoader.setController(shopMenuController);
        Parent shopMenuRoot = shopMenuLoader.load();
        
        // load the main menu
        GameOverController gameOverController = new GameOverController();
        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        gameOverLoader.setController(gameOverController);
        Parent gameOverRoot = gameOverLoader.load();

        // load the main menu
        GameWonController gameWonController = new GameWonController();
        FXMLLoader gameWonLoader = new FXMLLoader(getClass().getResource("GameWonView.fxml"));
        gameWonLoader.setController(gameWonController);
        Parent gameWonRoot = gameWonLoader.load();

        MusicController musicController = new MusicController();
        

        // load the main menu
        HelpMenuController helpMenuController = new HelpMenuController();
        FXMLLoader helpMenuLoader = new FXMLLoader(getClass().getResource("HelpMenuView.fxml"));
        helpMenuLoader.setController(helpMenuController);
        Parent helpMenuRoot = helpMenuLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainMenuController.setGameSwitcher(() -> {
            player.play();
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setShopMenuSwitcher(() -> {switchToRoot(scene, shopMenuRoot, primaryStage);});
        shopMenuController.setGameSwitcher(() -> {
            
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setGameOverSwitcher(() -> {switchToRoot(scene, gameOverRoot, primaryStage);});
        shopMenuController.setGameSwitcher(() -> {
            
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setGameWonSwitcher(() -> {switchToRoot(scene, gameWonRoot, primaryStage);});
        shopMenuController.setGameSwitcher(() -> {
            
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setHelpMenuSwitcher(() -> {switchToRoot(scene, helpMenuRoot, primaryStage);player.pause();});
        helpMenuController.setGameSwitcher(() -> {
            player.play();
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        
        
        // deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    MediaPlayer mediaPlayer;
	public MediaPlayer music() {
		String s = "src/bensound-goinghigher.mp3";
		//Media h = new Media(getClass().getResource(s).toExternalForm());
        Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		//mediaPlayer.play();
        
		return mediaPlayer;
	}

    @Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
