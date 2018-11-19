package cse260Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomeScreen extends Scene{
	protected Button playBtn;
	protected Button difficultyBtn;
	protected static BorderPane pane = new BorderPane();
	public HomeScreen() {
		super(pane, 1000, 1000);
		pane = new BorderPane();
		Label welcomeLabel = new Label("Welcome to the Rotating Maze Game");
		welcomeLabel.setScaleY(2);
		welcomeLabel.setMinHeight(100);
		
		
		playBtn = new Button("Play");
		difficultyBtn = new Button("Difficulty Settings");
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(playBtn, difficultyBtn);
		pane.setTop(welcomeLabel);
		pane.setCenter(vBox);
		super.setRoot(pane);
	}

}
