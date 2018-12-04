package cse260Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EndGameScreen extends Scene{
	protected Button returnToHomeScreenBtn;
	protected Button newGameBtn;
	protected Text loseMsg;
	protected Text score;
	protected HBox centerBox;
	protected static BorderPane pane = new BorderPane();
	public EndGameScreen() {
		super(pane, 1000, 1000);
		pane = new BorderPane();
		initializeNodes();
		centerBox = new HBox(50);
		centerBox.getChildren().addAll(returnToHomeScreenBtn, newGameBtn);
		pane.setCenter(centerBox);
		pane.setTop(loseMsg);
		super.setRoot(pane);
	}
	public void initializeNodes() {
		returnToHomeScreenBtn = new Button("Return to Home Screen");
		newGameBtn = new Button("Start A New Game");
		loseMsg = new Text("Sorry, You Lose!");
		loseMsg.setFont(Font.font(100));
		score = new Text("");
	}

}
