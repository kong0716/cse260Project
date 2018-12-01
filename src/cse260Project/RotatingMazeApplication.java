package cse260Project;

import javafx.application.Application;
import javafx.stage.Stage;

public class RotatingMazeApplication extends Application{
	private Stage window;
	private HomeScreen homeScreen;
	private DifficultyScreen difficultyScreen;
	private GameScreen gameScreen;
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Rotating Maze Application");
		
		homeScreen = new HomeScreen();
		difficultyScreen = new DifficultyScreen();
		gameScreen = new GameScreen();
		
		homeScreen.playBtn.setOnAction(e -> window.setScene(gameScreen));
		homeScreen.difficultyBtn.setOnAction(e -> window.setScene(difficultyScreen));
		homeScreen.quitBtn.setOnAction(e -> window.close());
		
		difficultyScreen.returnToHomeScreenBtn.setOnAction(e -> window.setScene(homeScreen));
		
		gameScreen.returnToHomeScreenBtn.setOnAction(e -> window.setScene(homeScreen));
		
		window.setScene(homeScreen);
		window.show();
	}
}
