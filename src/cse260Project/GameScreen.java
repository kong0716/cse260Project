package cse260Project;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GameScreen extends Scene {
	private static double sizeXScreen = 1000;
	private static double sizeYScreen = 1000;
	private double startPosXMaze;
	private double startPosYMaze;
	private int mazeSize;
	private int rotationSpeed;
	protected Cell[][] maze;
	protected static BorderPane root = new BorderPane();
	protected static BorderPane imagePane = new BorderPane();
	protected Button returnToHomeScreenBtn;
	protected Button rotateBtn;
	protected Button nextLvlBtn;
	private double tileSize;
	private ImageView mazeImage;
	private Image image;
	private RotateTransition rotate;
	private Robot mouse;
	private HBox hBoxTop;
	private HBox hBoxBtm;

	public GameScreen() {
		super(root, sizeXScreen, sizeYScreen);
		this.startPosXMaze = 0;
		this.startPosYMaze = 0;
		setDefaultDifficulty();
		initializeBtns();
		maze = generateMaze(mazeSize, mazeSize);
		mazeImage = genMazeImage(initMazeGraphics(maze));
		rotate = rotateNode(mazeImage);
		
		try {
			mouse = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		rotateBtn.setOnMouseClicked(e -> {
			if (rotate.getStatus() == Animation.Status.RUNNING) {
				rotate.pause();
			} else {
				mouse.mouseMove(1920/2, 1080/2);
				mouse.mouseMove(1920/2, 1080/2);
				mouse.mouseMove(1920/2, 1080/2);
				rotate.play();
			}
			this.mazeImage.setOnMousePressed(event -> { try {

	            // AWT Robot and Color to trace pixel information
	            Robot robot = new Robot();
	            Color color = robot.getPixelColor((int) event.getScreenX(), (int) event.getScreenY());

	            // Initializing pixel info
	            String xPos = Integer.toString((int) event.getX());
	            String yPos = Integer.toString((int) event.getY());
	            String colorRed = Integer.toString(color.getRed());
	            String colorBlue = Integer.toString(color.getBlue());
	            String colorGreen = Integer.toString(color.getGreen());
	            String hexColor = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

	            // Unify and format the information
	            String pixelInfo = "X: " + xPos + " Y: " + yPos + " | "
	                    + "r: " + colorRed + " g: " + colorGreen +
	                    " b: " + colorBlue + " | " + hexColor;
	            System.out.println(pixelInfo);

	            // Pass it on to the MainApp
	            //this.mainApp.getPixelInfo().setInfoString(pixelInfo);

	        } catch (Exception ignore){}});
		});
		hBoxTop = new HBox(20);
		hBoxTop.getChildren().addAll(nextLvlBtn);

		hBoxBtm = new HBox(20);
		hBoxBtm.getChildren().addAll(returnToHomeScreenBtn, rotateBtn);

		root.setTop(hBoxTop);
		root.setCenter(mazeImage);
		root.setBottom(hBoxBtm);

		nextLvlBtn.setOnAction(e -> {
			root.getChildren().remove(mazeImage);
			imagePane.getChildren().clear();
			this.startPosXMaze = 0;
			this.startPosYMaze = 0;
			// Tile tile = new Tile(500, 500, 100, 100, true, true, true, true);
			// root.getChildren().addAll(tile.walls);
			setDefaultDifficulty();

			initializeBtns();

			maze = generateMaze(mazeSize, mazeSize);
			mazeImage = genMazeImage(initMazeGraphics(maze));
			rotate = rotateNode(mazeImage);

			root.setCenter(mazeImage);
		});
	}

	public void setDefaultDifficulty() {
		this.rotationSpeed = 10;// Lower is better
		this.mazeSize = 10;
	}

	public RotateTransition rotateNode(javafx.scene.Node node) {
		RotateTransition rotate = new RotateTransition(Duration.seconds(rotationSpeed), node);
		rotate.setFromAngle(0);
		rotate.setToAngle(360);
		// rotate.setAutoReverse(true);
		rotate.setCycleCount(Animation.INDEFINITE);
		return rotate;
	}

	public BorderPane initMazeGraphics(Cell[][] maze) {
		tileSize = maze.length * 1.5;
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				if (!maze[row][col].isEndCELL()) {
					Tile tile = new Tile(startPosXMaze, startPosYMaze, sizeXScreen / tileSize, sizeYScreen / tileSize,
							!maze[row][col].isPathUP(), !maze[row][col].isPathDOWN(), !maze[row][col].isPathLEFT(),
							!maze[row][col].isPathRIGHT());
					imagePane.getChildren().addAll(tile.walls);
				}
				startPosXMaze += sizeXScreen / tileSize;
			}
			this.startPosXMaze = 0;
			startPosYMaze += sizeYScreen / tileSize;
		}
		return imagePane;
	}

	public ImageView genMazeImage(Pane pane) {
		WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);
		ImageView mazeImage = new ImageView(snapshot);
		return mazeImage;
	}

	public void initializeBtns() {
		returnToHomeScreenBtn = new Button("Home Screen");
		rotateBtn = new Button("Rotate");
		nextLvlBtn = new Button("Next Level");
	}

	public Cell[][] generateMaze(int row, int col) {
		Cell[][] maze = new Cell[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				maze[i][j] = new Cell(i, j);
			}
		}
		List<Cell> frontier = new LinkedList<Cell>();
		pickExits(maze);
		frontier.add(pickStartCELL(maze));
		List<Cell> path = new LinkedList<Cell>();
		boolean openedPath = false;
		while (!frontier.isEmpty()) {
			// Use a flag to find if you need to cut a path
			Random randomNumber = new Random();
			int random = randomNumber.nextInt(frontier.size());

			if (frontier.get(random) == null) {// Avoids null Cells
				frontier.remove(random);
			}
			if (random == frontier.size()) {
				random--;
			}
			if (frontier.isEmpty()) {
				break;
			}
			Cell temp = frontier.remove(random);
			// Maze is vertical heavy because of the way we implemented the if statements
			if (!frontier.contains(returnTopAdjacent(maze, temp))) {
				if (!path.contains(returnTopAdjacent(maze, temp))) {
					frontier.add(returnTopAdjacent(maze, temp));
				} else if (!openedPath) {
					temp.setPathUP(true);
					returnTopAdjacent(maze, temp).setPathDOWN(true);
					openedPath = true;
				}
			}
			if (!frontier.contains(returnBotAdjacent(maze, temp))) {
				if (!path.contains(returnBotAdjacent(maze, temp))) {
					frontier.add(returnBotAdjacent(maze, temp));
				} else if (!openedPath) {
					temp.setPathDOWN(true);
					returnBotAdjacent(maze, temp).setPathUP(true);
					openedPath = true;
				}
			}
			if (!frontier.contains(returnLeftAdjacent(maze, temp))) {
				if (!path.contains(returnLeftAdjacent(maze, temp))) {
					frontier.add(returnLeftAdjacent(maze, temp));
				} else if (!openedPath) {
					temp.setPathLEFT(true);
					returnLeftAdjacent(maze, temp).setPathRIGHT(true);
					openedPath = true;
				}
			}
			if (!frontier.contains(returnRightAdjacent(maze, temp))) {
				if (!path.contains(returnRightAdjacent(maze, temp))) {
					frontier.add(returnRightAdjacent(maze, temp));
				} else if (!openedPath) {
					temp.setPathRIGHT(true);
					returnRightAdjacent(maze, temp).setPathLEFT(true);
					openedPath = true;
				}
			}
			System.out.println(temp.toString());
			path.add(temp);
			// printMaze(maze);
			openedPath = false;
		}
		printMaze(maze);
		return maze;
	}

	public static void printMaze(Cell[][] maze) {
		System.out.println("Maze");
		System.out.print(" ");
		for (int i = 0; i < maze.length; i++) {
			if (!maze[0][i].isPathUP()) {
				System.out.print("_ ");
			}
		}
		for (int i = 0; i < maze.length; i++) {
			System.out.println("");
			for (int j = 0; j < maze[i].length; j++) {
				if (maze[i][j].isPathLEFT()) {
					System.out.print(" ");
				}
				if (!maze[i][j].isPathLEFT()) {
					System.out.print("|");
				}
				if (maze[i][j].isPathDOWN()) {
					System.out.print(" ");
				}
				if (!maze[i][j].isPathDOWN()) {
					System.out.print("_");
				}
				if (j == maze[i].length - 1 && !maze[i][j].isPathRIGHT()) {
					System.out.print("|");
				}
			}
		}
	}

	public static Cell pickStartCELL(Cell[][] maze) {
		maze[maze.length / 2][maze.length / 2].setStartCELL(true);
		maze[maze.length / 2][maze.length / 2].setPathDOWN(true);
		maze[maze.length / 2][maze.length / 2].setPathUP(true);
		maze[maze.length / 2][maze.length / 2].setPathLEFT(true);
		maze[maze.length / 2][maze.length / 2].setPathRIGHT(true);
		return maze[maze.length / 2][maze.length / 2];
	}

	public static Cell pickEntrance(Cell[][] maze) {
		maze[maze.length - 1][maze.length - 1].setStartCELL(true);
		maze[maze.length - 1][maze.length - 1].setPathDOWN(true);
		return maze[maze.length - 1][maze.length - 1];
	}

	public static void pickExits(Cell[][] maze) {
		maze[0][0].setEndCELL(true);
		maze[0][maze.length - 1].setEndCELL(true);
		maze[maze.length - 1][0].setEndCELL(true);
		maze[maze.length - 1][maze.length - 1].setEndCELL(true);
	}

	public static Cell returnTopAdjacent(Cell[][] maze, Cell cell) {
		if (cell.getRow() > 0) {
			return maze[cell.getRow() - 1][cell.getCol()];
		}
		return null;
	}

	public static Cell returnBotAdjacent(Cell[][] maze, Cell cell) {
		if (cell.getRow() < maze.length - 1) {
			return maze[cell.getRow() + 1][cell.getCol()];
		}
		return null;
	}

	public static Cell returnLeftAdjacent(Cell[][] maze, Cell cell) {
		if (cell.getCol() > 0) {
			return maze[cell.getRow()][cell.getCol() - 1];
		}
		return null;
	}

	public static Cell returnRightAdjacent(Cell[][] maze, Cell cell) {
		if (cell.getCol() < maze[cell.getRow()].length - 1) {
			return maze[cell.getRow()][cell.getCol() + 1];
		}
		return null;
	}
}
