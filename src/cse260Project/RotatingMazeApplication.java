package cse260Project;

import java.awt.event.InputEvent;

import cse260Project.GameScreen.CheckingGameState;
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
			} catch(NullPointerException ex) {
			}
			// Creates a thread that checks the game state
			checking = new CheckingGameState();
			checkingThread = new Thread(checking);
			checkingThread.start();
			gameScreen.nextLvlBtn.fire();
			
			window.setScene(gameScreen);
		});
		homeScreen.difficultyBtn.setOnAction(e -> window.setScene(difficultyScreen));
		homeScreen.quitBtn.setOnAction(e -> {
			try {
				checking.askToDie();
			} catch(NullPointerException ex) {
			}
			window.close();
		});

		difficultyScreen.returnToHomeScreenBtn.setOnAction(e -> window.setScene(homeScreen));
		difficultyScreen.brainFuckBtn.setOnAction(e -> {
			gameScreen.setBrainFuckDifficulty();
			gameScreen.nextLvlBtn.fire();
		});
		difficultyScreen.intermediateBtn.setOnAction(e -> {
			gameScreen.setIntermediateDifficulty();
			gameScreen.nextLvlBtn.fire();
		});
		difficultyScreen.beginnerBtn.setOnAction(e -> {
			gameScreen.setDefaultDifficulty();
			gameScreen.nextLvlBtn.fire();
		});

		gameScreen.returnToHomeScreenBtn.setOnAction(e -> {
			try {
				checking.askToDie();
			} catch(NullPointerException ex) {
			}
			window.setScene(homeScreen);
		});

		endGameScreen.returnToHomeScreenBtn.setOnAction(e -> {
			gameScreen.setDefaultDifficulty();
			gameScreen.nextLvlBtn.fire();
			window.setScene(homeScreen);
		});
		endGameScreen.newGameBtn.setOnAction(e -> {
			gameScreen.setDefaultDifficulty();
			gameScreen.nextLvlBtn.fire();
			window.setScene(gameScreen);
			// Creates a thread that checks the game state
			checking = new CheckingGameState();
			checkingThread = new Thread(checking);
			checkingThread.start();
		});
		/**
		new Thread(new Runnable() {
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
								}
							});
							Thread.sleep(20);
							this.askToDie();
						}
						System.out.println("Check is running");
					}
				} catch (InterruptedException ex) {
					System.out.println("Error");
				}
			}
		}).start();
		**/

		window.setScene(homeScreen);
		window.show();
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
							}
						});
						Thread.sleep(20);
						this.askToDie();
						gameScreen.hexColor = "#FFFFFF";
					}
					System.out.println("Check is running");
				}
			} catch (InterruptedException ex) {
				System.out.println("Error");
			}
		}
	}

}
