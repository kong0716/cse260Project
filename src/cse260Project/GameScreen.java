package cse260Project;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameScreen extends Scene{
	protected static BorderPane pane = new BorderPane();
	public GameScreen() {
		super(pane, 500, 1000);
	}

}
