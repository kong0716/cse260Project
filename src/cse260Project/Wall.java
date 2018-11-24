package cse260Project;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {

	private boolean UP;
	private boolean DOWN;
	private boolean LEFT;
	private boolean RIGHT;
	private double centerX;
	private double centerY;
	private double height;
	private double width;
	public Wall() {
		// TODO Auto-generated constructor stub
	}
	
	public Wall(double centerX, double centerY, double height, double width, String placement) {
		super(centerX, centerY, width, height);
	}

	private Wall(double arg0, double arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	private Wall(double arg0, double arg1, Paint arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	private Wall(double arg0, double arg1, double arg2, double arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
