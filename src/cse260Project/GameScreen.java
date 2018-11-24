package cse260Project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameScreen extends Scene{
	protected static Group root = new Group();
	public GameScreen() {
		super(root, 1000, 1000);
		Tile tile = new Tile(500, 500, 100, 100, true, true, true, true);
		root.getChildren().addAll(tile.walls);
		
	}

}
