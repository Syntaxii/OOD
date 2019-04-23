package player;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Collision {
	private ArrayList<ImageView> obstacles; 
	private Image lavapic = new Image("https://www.seekpng.com/png/full/233-2339796_waba-funs-great-adventure-production-lava-pit-craft.png");
	private ImageView lava = new ImageView(lavapic);
	private Rectangle playerBox;
	
	public Collision() {
		playerBox = new Rectangle(60,60);
		playerBox.setX(150);
		playerBox.setY(150);
		playerBox.setFill(Color.rgb(150, 150, 150, 0.0));
		
		lava.setFitWidth(150);
		lava.setFitHeight(150);
		
		obstacles = new ArrayList<ImageView>();
		obstacles.add(lava);
		
		obstacles.get(0).setX(300);
		obstacles.get(0).setY(325);
		
	}
	public Rectangle getPlayercollision() {
		return playerBox;
	}
	public ArrayList<ImageView> getObstacles() {
		return obstacles;
	}
}
