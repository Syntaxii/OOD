package game;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class UI {
	private static UI theUI = null;
	private ArrayList<Node> UIParts;
	private Rectangle weaponsUI, weapon1, weapon1CDbox, weapon2, weapon2CDbox, weapon3, weapon3CDbox, HealthBar, HealthBarBG;
	private Label weapon1ammo, HealthWarning;
	static final String weapon1URL = ("http://chittagongit.com//images/icon-gun/icon-gun-26.jpg");
	private static ImageView weapon1Image = new ImageView(weapon1URL);
	private int current; //current weapon selection
	private double spaceDifference = 133.33;
	private double x, y; //coordinates that UI is based off of

	private UI(){
		declareUI();
	}

	public ArrayList<Node> getUIElements(){
		return UIParts;
	}

	public void changeWeaponFocus(int i) {
		
		//TODO switch to switch statement lol
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

	public void changeUIPositions(double newx, double newy) {
		x = newx;
		y = newy;
		weaponsUI.relocate(x, y);
		weapon1.relocate(x, y);
		weapon1CDbox.relocate(x, y);
		weapon1Image.relocate(x-30, y-50);
		weapon1ammo.relocate(x+78, y+40);
		weapon2.relocate(x+spaceDifference, y);
		weapon2CDbox.relocate(x+spaceDifference, y);
		weapon3.relocate(x+spaceDifference*2, y);
		weapon3CDbox.relocate(x+spaceDifference*2, y);
		HealthBar.relocate(x, y-60);
		HealthWarning.relocate(x+179, y-63);
		HealthBarBG.relocate(x, y-60);
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

	private void declareUI() {
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
		
		weapon1CDbox = new Rectangle(133.33, 100);
		weapon1CDbox.setX(175);
		weapon1CDbox.setY(600);
		weapon1CDbox.setFill(Color.rgb(50, 50, 200, 0.5));
		weapon1CDbox.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon1CDbox.setStrokeWidth(2);
		weapon1CDbox.setHeight(100);
		
		weapon1Image.setX(180);
		weapon1Image.setY(650);
		weapon1Image.setScaleX(-0.4);
		weapon1Image.setScaleY(.4);
		
		weapon1ammo = new Label("\u221e");
		weapon1ammo.setTextFill(Color.DARKRED);
		weapon1ammo.setFont(new Font("Arial", 72));

		weapon2 = new Rectangle(133.33, 100);
		weapon2.setX(175+spaceDifference);
		weapon2.setY(600);
		weapon2.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStrokeWidth(2);
		
		weapon2CDbox = new Rectangle(133.33, 100);
		weapon2CDbox.setX(175+spaceDifference);
		weapon2CDbox.setY(600);
		weapon2CDbox.setFill(Color.rgb(100, 200, 200, 0.5));
		weapon2CDbox.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon2CDbox.setStrokeWidth(2);
		weapon2CDbox.setHeight(0);

		weapon3 = new Rectangle(133.33, 100);
		weapon3.setX(175+spaceDifference*2);
		weapon3.setY(600);
		weapon3.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon3.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon3.setStrokeWidth(2);
		
		weapon3CDbox = new Rectangle(133.33, 100);
		weapon3CDbox.setX(175+spaceDifference*2);
		weapon3CDbox.setY(600);
		weapon3CDbox.setFill(Color.rgb(100, 200, 200, 0.5));
		weapon3CDbox.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon3CDbox.setStrokeWidth(2);
		weapon3CDbox.setHeight(0);

		HealthBar = new Rectangle(400, 50);
		HealthBar.setX(175);
		HealthBar.setY(540);
		HealthBar.setFill(Color.rgb(250, 15, 15, 0.9));
		
		HealthWarning = new Label("!!!");
		HealthWarning.setFont(new Font("Arial", 50));
		HealthWarning.setTextFill(Color.DARKRED);
		HealthWarning.setVisible(false);

		HealthBarBG = new Rectangle(400, 50);
		HealthBarBG.setX(175);
		HealthBarBG.setY(540);
		HealthBarBG.setFill(Color.rgb(250, 15, 15, 0.2));
		HealthBarBG.setStroke(Color.rgb(250, 250, 250, 1));
		HealthBarBG.setStrokeWidth(3);

		UIParts.add(weaponsUI);
		UIParts.add(weapon1);
		UIParts.add(weapon1CDbox);
		UIParts.add(weapon1Image);
		UIParts.add(weapon1ammo);
		UIParts.add(weapon2);
		UIParts.add(weapon2CDbox);
		UIParts.add(weapon3);
		UIParts.add(weapon3CDbox);
		UIParts.add(HealthBar);
		UIParts.add(HealthWarning);
		UIParts.add(HealthBarBG);
	}

	public void ChangeHP(int hp) {
		HealthBar.setWidth(hp);
	}
	
	public void warnHP() {
		if (HealthWarning.isVisible() == true) HealthWarning.setVisible(false);
		else HealthWarning.setVisible(true);
	}
	
	public void deadHP() {
		HealthWarning.setVisible(true);
		HealthWarning.setText("Dead!");
		HealthWarning.relocate(x+135, y-63);
	}
	
	public void resetHP() {
		HealthWarning.setVisible(false);
		HealthWarning.setText("!!!");
		HealthWarning.relocate(x+179, y-63);
		HealthBar.setWidth(400);
	}
	
	public int getCurrentWeaponSelection() {
		return current;
	}
	
	public void updateWeaponCD(int weapon, int cooldown) {
		switch(weapon) {
		case 1:	weapon1CDbox.setHeight(100-(cooldown%15)*6.6666666); break; //15 is cd of weapon
		case 2: weapon2CDbox.setHeight(100-(cooldown%15)*6.6666666); break; //15 is cd of weapon;
		case 3: weapon3CDbox.setHeight(100-(cooldown%15)*6.6666666); break; //15 is cd of weapon;
			default: break;
		}
	}


}
