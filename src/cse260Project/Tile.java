package cse260Project;

import java.util.ArrayList;
import java.util.List;

public class Tile {

	private boolean UP;
	private boolean DOWN;
	private boolean LEFT;
	private boolean RIGHT;
	private double centerX;
	private double centerY;
	private double height;
	private double width;
	public List<Wall> walls;

	public Tile(double centerX, double centerY, double height, double width, boolean UP, boolean DOWN, boolean LEFT, boolean RIGHT) {
		this.walls = new ArrayList<Wall>();
		this.centerX = centerX;
		this.centerY = centerY;
		this.height = height;
		this.width = width;
		this.UP = UP;
		this.DOWN = DOWN;
		this.LEFT = LEFT;
		this.RIGHT = RIGHT;
		if (UP) {
			this.UP = true;
			centerY -= height/2;
			centerX -= width/2 - width/20;
			walls.add(new Wall(centerX, centerY, height/10, width, "UP"));
			centerY = this.centerY;
			centerX = this.centerX;
		}
		if (DOWN) {
			this.DOWN = true;
			centerY += height/2;
			centerX -= width/2 - width/20;
			walls.add(new Wall(centerX, centerY, height/10, width, "DOWN"));
			centerY = this.centerY;
			centerX = this.centerX;
		}
		if (LEFT) {
			this.LEFT = true;
			centerX -= width/2;
			centerY -= height/2 - height/20;
			walls.add(new Wall(centerX, centerY, height, width/10, "LEFT"));
			centerX = this.centerX;
			centerY = this.centerY;
		}
		if (RIGHT) {
			this.RIGHT = true;
			centerX += width/2;
			centerY -= height/2 - height/20;
			walls.add(new Wall(centerX, centerY, height, width/10, "RIGHT"));
			centerX = this.centerX;
			centerY = this.centerY;
		}
	}

}
