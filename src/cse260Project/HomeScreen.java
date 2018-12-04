package cse260Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomeScreen extends Scene{
	protected Button playBtn;
	protected Button difficultyBtn;
	protected Button quitBtn;
	protected static BorderPane pane = new BorderPane();
	public HomeScreen() {
		super(pane, 1000, 1000);
		pane = new BorderPane();
		Text welcomeLabel = new Text("Welcome to the Rotating Maze Game");
		welcomeLabel.setWrappingWidth(1000);
		welcomeLabel.setFont(Font.font(80));
		
		initializeBtns();
	
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(playBtn, difficultyBtn, quitBtn);
		pane.setTop(welcomeLabel);
		pane.setCenter(vBox);
		super.setRoot(pane);
	}
	
	public void initializeBtns() {
		playBtn = new Button("Play");
		difficultyBtn = new Button("Difficulty Settings");
		quitBtn = new Button("Quit Game");
	}

}
