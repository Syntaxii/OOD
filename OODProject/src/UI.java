import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UI {
	private static UI theUI = null;
	private ArrayList<Node> UIParts;
	private Rectangle weaponsUI, weapon1, weapon2, weapon3;
	private int current;
	private double spaceDifference = 133.33;

	private UI(){
		UIParts = new ArrayList<Node>();

		weaponsUI = new Rectangle(400, 100);
		weaponsUI.setX(175);
		weaponsUI.setY(600);
		weaponsUI.setFill(Color.rgb(50, 50, 200, 0.5));
		weaponsUI.setStroke(Color.rgb(200, 200, 200, 0.8));
		weaponsUI.setStrokeWidth(3);

		weapon1 = new Rectangle(133.33, 100);
		weapon1.setX(175);
		weapon1.setY(600);
		weapon1.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon1.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon1.setStrokeWidth(2);

		weapon2 = new Rectangle(133.33, 100);
		weapon2.setX(175+spaceDifference);
		weapon2.setY(600);
		weapon2.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStrokeWidth(2);

		weapon3 = new Rectangle(133.33, 100);
		weapon3.setX(175+spaceDifference*2);
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

	public void changeWeaponFocus(int i) {
		if (current == 1) {
			changeColorToNormal(weapon1);
		}
		else if (current == 2) {
			changeColorToNormal(weapon2);
		}
		else {
			changeColorToNormal(weapon3);
		}
		
		current = i;

		if (i == 1) {
			changeColorToFocus(weapon1);
		}
		else if (i == 2) {
			changeColorToFocus(weapon2);
		}
		else {
			changeColorToFocus(weapon3);
		}
	}
	
	public void changeUIPositions(double x, double y) {
		weaponsUI.relocate(x, y);
		weapon1.relocate(x, y);
		weapon2.relocate(x+spaceDifference, y);
		weapon3.relocate(x+spaceDifference*2, y);
	}

	private void changeColorToNormal(Rectangle rec) {
		rec.setStroke(Color.rgb(200, 200, 200, 0.5));
		rec.setFill(Color.rgb(200, 200, 200, 0.5));
		rec.setStrokeWidth(2);
	}

	private void changeColorToFocus(Rectangle rec) {
		rec.setStroke(Color.rgb(25, 240, 240, 0.7));
		rec.setFill(Color.rgb(25, 240, 240, 0.7));
		rec.setStrokeWidth(3);
	}

	public static synchronized UI getUI() {
		if (theUI == null) {
			theUI = new UI();
		}
		return theUI;
	}




}
