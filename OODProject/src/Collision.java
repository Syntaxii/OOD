import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Collision {
	private ArrayList<Rectangle> obstacles; 
	private Rectangle playerBox;
	
	Collision() {
		playerBox = new Rectangle(60,60);
		playerBox.setX(150);
		playerBox.setY(150);
		playerBox.setFill(Color.rgb(150, 150, 150, 0.75));
		
		obstacles = new ArrayList<Rectangle>();
		obstacles.add(new Rectangle(70, 70, Color.RED));
		
		obstacles.get(0).setX(375);
		obstacles.get(0).setY(375);
		
	}
	public Rectangle getPlayercollision() {
		return playerBox;
	}
	public ArrayList<Rectangle> getObstacles() {
		return obstacles;
	}
}
