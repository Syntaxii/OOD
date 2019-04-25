package player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Collision {
	
	private ArrayList<ImageView> obstacles; 
	public Collision() throws IOException {
		String imgURL = this.getClass().getResource("/images/lava.png").toString();
		System.out.println(imgURL);
		
		Image lavapic = new Image(imgURL);
		ImageView lava = new ImageView(lavapic);
		ImageView lava2 = new ImageView(lavapic);
		ImageView lava3 = new ImageView(lavapic);
		ImageView lava4 = new ImageView(lavapic);
		
		lava.setFitWidth(225);
		lava.setFitHeight(225);
		lava2.setFitWidth(175);
		lava2.setFitHeight(175);
		lava3.setFitWidth(150);
		lava3.setFitHeight(150);
		lava4.setFitWidth(200);
		lava4.setFitHeight(200);
		
		obstacles = new ArrayList<ImageView>();
		obstacles.add(lava);
		obstacles.add(lava2);
		obstacles.add(lava3);
		obstacles.add(lava4);
		
		obstacles.get(0).setX(300);
		obstacles.get(0).setY(325);
		obstacles.get(1).setX(1400);
		obstacles.get(1).setY(600);
		obstacles.get(2).setX(900);
		obstacles.get(2).setY(200);
		obstacles.get(3).setX(500);
		obstacles.get(3).setY(800);
	}
	public ArrayList<ImageView> getObstacles() {
		return obstacles;
	}
}
