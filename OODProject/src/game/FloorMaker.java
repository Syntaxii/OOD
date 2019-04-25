package game;

import javafx.scene.layout.BackgroundImage;

public class FloorMaker {
	private Floor brick, grass;
	
	public FloorMaker() {
		brick = new Brick();
		grass = new Grass();
	}
	public BackgroundImage setGrass() {
		return grass.setFloor();
	}
	public BackgroundImage setBrick() {
		return brick.setFloor();
	}
}
