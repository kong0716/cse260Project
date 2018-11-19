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
		gameScreen = new GameScreen();
		difficultyScreen = new DifficultyScreen();
		
		homeScreen.playBtn.setOnAction(e -> window.setScene(gameScreen));
		homeScreen.difficultyBtn.setOnAction(e -> window.setScene(difficultyScreen));
		
		difficultyScreen.returnToHomeScreenBtn.setOnAction(e -> window.setScene(homeScreen));
		
		window.setScene(homeScreen);
		window.show();
	}

}
