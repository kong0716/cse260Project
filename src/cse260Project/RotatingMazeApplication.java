package cse260Project;

import java.awt.event.InputEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class RotatingMazeApplication extends Application {
	private Stage window;
	private HomeScreen homeScreen;
	private DifficultyScreen difficultyScreen;
	private GameScreen gameScreen;
	private EndGameScreen endGameScreen;
	private Thread checkingThread;
	private CheckingGameState checking;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Rotating Maze Application");

		homeScreen = new HomeScreen();
		difficultyScreen = new DifficultyScreen();
		gameScreen = new GameScreen();
		endGameScreen = new EndGameScreen();

		homeScreen.playBtn.setOnAction(e -> {
			try {
				checking.askToDie();
			} catch (NullPointerException ex) {
			}
			gameScreen.setDifficulty();
			gameScreen.recreateLvl();
			window.setScene(gameScreen);
		});
		homeScreen.difficultyBtn.setOnAction(e -> window.setScene(difficultyScreen));
		homeScreen.quitBtn.setOnAction(e -> {
			try {
				checking.askToDie();
			} catch (NullPointerException ex) {
			}
			window.close();
		});

		difficultyScreen.returnToHomeScreenBtn.setOnAction(e -> window.setScene(homeScreen));
		difficultyScreen.playBtn.setOnAction(e -> {
			if(difficultyScreen.beginnerBtn.isSelected()) {
				gameScreen.setDefaultDifficulty();
			}
			if(difficultyScreen.intermediateBtn.isSelected()) {
				gameScreen.setIntermediateDifficulty();
			}
			if(difficultyScreen.brainFuckBtn.isSelected()) {
				gameScreen.setBrainFuckDifficulty();
			}
			gameScreen.recreateLvl();
			window.setScene(gameScreen);
		});
		difficultyScreen.brainFuckBtn.setOnAction(e -> {
			gameScreen.setBrainFuckDifficulty();
			gameScreen.recreateLvl();
		});
		difficultyScreen.intermediateBtn.setOnAction(e -> {
			gameScreen.setIntermediateDifficulty();
			gameScreen.recreateLvl();
		});
		difficultyScreen.beginnerBtn.setOnAction(e -> {
			gameScreen.setDefaultDifficulty();
			gameScreen.recreateLvl();
		});

		gameScreen.returnToHomeScreenBtn.setOnAction(e -> {
			try {
				checking.askToDie();
			} catch (NullPointerException ex) {
			}
			window.setScene(homeScreen);
		});
		gameScreen.rotateBtn.setOnAction(e -> {
			// Creates a thread that checks the game state
			checking = new CheckingGameState();
			checkingThread = new Thread(checking);
			checkingThread.start();
		});
		gameScreen.nextLvlBtn.setOnAction(e -> {
			try {
				checking.askToDie();
			} catch (NullPointerException ex) {
			}
		});
		
		gameScreen.mazeImage.setOnMouseExited(e -> {
			try {
				checking.askToDie();
			} catch (NullPointerException ex) {
			}
			
		});

		endGameScreen.returnToHomeScreenBtn.setOnAction(e -> {
			//gameScreen.setDefaultDifficulty();
			gameScreen.nextLvlBtn.fire();
			window.setScene(homeScreen);
		});
		endGameScreen.newGameBtn.setOnAction(e -> {
			gameScreen.setDifficulty();
			gameScreen.recreateLvl();
			window.setScene(gameScreen);
		});

		/**
		 * new Thread(new Runnable() { private boolean die = false;
		 * 
		 * public void askToDie() { die = true; }
		 * 
		 * @Override public void run() { try { while (!die) { if
		 *           (!gameScreen.hexColor.equalsIgnoreCase("#FFFFFF")) {
		 *           Platform.runLater(new Runnable() {
		 * @Override public void run() { window.setScene(endGameScreen); } });
		 *           Thread.sleep(20); this.askToDie(); } System.out.println("Check is
		 *           running"); } } catch (InterruptedException ex) {
		 *           System.out.println("Error"); } } }).start();
		 **/

		window.setScene(homeScreen);
		window.show();
		System.out.println("Screen origin is (" + window.getX() + ", " + window.getY() + ")");
		gameScreen.window = window;
	}

	class CheckingGameState implements Runnable {
		private boolean die = false;

		public void askToDie() {
			die = true;
		}

		@Override
		public void run() {
			try {
				while (!die) {
					if (!gameScreen.hexColor.equalsIgnoreCase("#FFFFFF")) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								window.setScene(endGameScreen);
								System.out.println(gameScreen.hexColor);
							}
						});
						Thread.sleep(5);
						this.askToDie();
						gameScreen.hexColor.equalsIgnoreCase("#FFFFFF");
						System.out.println(gameScreen.hexColor);
					}
					System.out.println("Check is running");
				}
			} catch (InterruptedException ex) {
				System.out.println("Error");
			}
		}
	}
}
