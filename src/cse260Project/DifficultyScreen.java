package cse260Project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DifficultyScreen extends Scene{
	protected Button returnToHomeScreenBtn;
	protected RadioButton beginnerBtn;
	protected RadioButton intermediateBtn;
	protected RadioButton brainFuckBtn;
	protected static BorderPane pane = new BorderPane();
	public DifficultyScreen() {
		super(pane, 500, 1000);
		pane = new BorderPane();
		Label difficultyLabel = new Label("Difficulty");
		difficultyLabel.setScaleY(2);
		difficultyLabel.setMinHeight(100);
		
		initializeBtns();
		
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(beginnerBtn, intermediateBtn, brainFuckBtn, returnToHomeScreenBtn);
		pane.setTop(difficultyLabel);
		pane.setCenter(vBox);
		super.setRoot(pane);
	}
	public void initializeBtns() {
		returnToHomeScreenBtn = new Button("Home Screen");
		beginnerBtn = new RadioButton("Beginner");
		intermediateBtn = new RadioButton("Intermediate");
		brainFuckBtn = new RadioButton("Good Luck");
		ToggleGroup group = new ToggleGroup();
		beginnerBtn.setToggleGroup(group);
		intermediateBtn.setToggleGroup(group);
		brainFuckBtn.setToggleGroup(group);
	}

}
