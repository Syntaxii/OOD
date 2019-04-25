package game;

import java.io.IOException;

import javafx.scene.layout.BackgroundImage;

public class FloorMaker {
	private Floor brick, grass;
	
	public FloorMaker() {
		brick = new Brick();
		grass = new Grass();
	}
	public BackgroundImage setGrass() throws IOException {
		return grass.setFloor();
	}
	public BackgroundImage setBrick() throws IOException {
		return brick.setFloor();
	}
}
