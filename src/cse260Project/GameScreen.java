package cse260Project;

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
import javafx.scene.image.ImageView;
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
	private int tileSize;
	private ImageView mazeImage;
	private RotateTransition rotate;
	private HBox hBoxTop;
	private HBox hBoxBtm;

	public GameScreen() {
		super(root, sizeXScreen, sizeYScreen);
		this.startPosXMaze = 0;
		this.startPosYMaze = 0;
		// Tile tile = new Tile(500, 500, 100, 100, true, true, true, true);
		// root.getChildren().addAll(tile.walls);
		setDefaultDifficulty();
		
		initializeBtns();
		
		maze = generateMaze(mazeSize, mazeSize);
		tileSize = maze.length * 2;
		
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				Tile tile = new Tile(startPosXMaze, startPosYMaze, sizeXScreen / tileSize, sizeYScreen / tileSize,
						!maze[row][col].isPathUP(), !maze[row][col].isPathDOWN(), !maze[row][col].isPathLEFT(),
						!maze[row][col].isPathRIGHT());
				imagePane.getChildren().addAll(tile.walls);
				startPosXMaze += sizeXScreen / tileSize;
			}
			this.startPosXMaze = 0;
			startPosYMaze += sizeYScreen / tileSize;
		}
		mazeImage = genMazeImage(imagePane);
		//imagePane.getChildren().clear();
		//imagePane.getChildren().add(mazeImage);
		rotate = rotateNode(mazeImage);
		rotateBtn.setOnAction(e -> {
			if (rotate.getStatus() == Animation.Status.RUNNING) {
				rotate.pause();
			} else {
				rotate.play();
			}
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
		this.rotationSpeed = 5;
		this.mazeSize = 5;
	}
	public RotateTransition rotateNode(javafx.scene.Node node) {
		RotateTransition rotate = new RotateTransition(Duration.seconds(rotationSpeed), node);
		rotate.setFromAngle(0);
		rotate.setToAngle(360);
		//rotate.setAutoReverse(true);
		rotate.setCycleCount(Animation.INDEFINITE);
		return rotate;
	}
	public BorderPane initMazeGraphics(Cell[][] maze) {
		tileSize = maze.length * 2;
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				Tile tile = new Tile(startPosXMaze, startPosYMaze, sizeXScreen / tileSize, sizeYScreen / tileSize,
						!maze[row][col].isPathUP(), !maze[row][col].isPathDOWN(), !maze[row][col].isPathLEFT(),
						!maze[row][col].isPathRIGHT());
				imagePane.getChildren().addAll(tile.walls);
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
		frontier.add(pickEntrance(maze));
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
			printMaze(maze);
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

	public static Cell pickEntrance(Cell[][] maze) {
		maze[maze.length-1][maze.length-1].setStartCELL(true);
		maze[maze.length-1][maze.length-1].setPathDOWN(true);
		return maze[maze.length-1][maze.length-1];
	}
	public static Cell pickExit(Cell[][] maze) {
		maze[0][0].setEndCELL(true);
		maze[0][0].setPathUP(true);
		return maze[0][0];
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
