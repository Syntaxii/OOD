import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UI {
	private static UI theUI = null;
	private ArrayList<Node> UIParts;

	
	private UI(){
		UIParts = new ArrayList<Node>();
		
		Rectangle weaponsUI = new Rectangle(400, 100);
		weaponsUI.setX(175);
		weaponsUI.setY(600);
		weaponsUI.setFill(Color.rgb(50, 50, 200, 0.5));
		weaponsUI.setStroke(Color.rgb(200, 200, 200, 0.8));
		weaponsUI.setStrokeWidth(3);
		
		Rectangle weapon1 = new Rectangle(133.33, 100);
		weapon1.setX(175);
		weapon1.setY(600);
		weapon1.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon1.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon1.setStrokeWidth(2);
		
		Rectangle weapon2 = new Rectangle(133.33, 100);
		weapon2.setX(308.33);
		weapon2.setY(600);
		weapon2.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStrokeWidth(2);
		
		Rectangle weapon3 = new Rectangle(133.33, 100);
		weapon3.setX(441.66);
		weapon3.setY(600);
		weapon3.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon3.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon3.setStrokeWidth(2);
		
		UIParts.add(weaponsUI);
		UIParts.add(weapon1);
		UIParts.add(weapon2);
		UIParts.add(weapon3);
	}
	
	public ArrayList<Node> getUIElements(){
		return UIParts;
	}
	
	public void colorChange(Node node, Color color) {
		UIParts.
	}
	
	public static synchronized UI getUI() {
		if (theUI == null) {
			theUI = new UI();
		}
		return theUI;
	}
	

	
	
}
