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
	private Rectangle weaponsUI, weapon1, weapon1CDbox, weapon2, weapon2CDbox, weapon3, weapon3CDbox,
	HealthBar, hurtScreen, HealthBarBG, debugBox, pauseScreen;
	private Label weapon1ammo, HealthWarning, debuginfo1, debuginfo2, debuginfo3, debuginfo4, debugLabel, pauseScreenText, instruction;
	static final String weapon1URL = ("http://chittagongit.com//images/icon-gun/icon-gun-26.jpg");
	private static ImageView weapon1Image = new ImageView(weapon1URL);
	private int current; //current weapon selection
	private double spaceDifference = 133.33;
	private boolean debugMode;
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
		pauseScreen.relocate(x-50, y-850);
		pauseScreenText.relocate(x+80, y-845);
		instruction.relocate(x+50, y-750);
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
		
		hurtScreen = new Rectangle(3000, 3000);
		hurtScreen.setFill(Color.rgb(230, 50, 50, 0.2));
		hurtScreen.setVisible(false);
		hurtScreen.setX(-400);
		hurtScreen.setY(-400);
		
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
		
		//debug stuff
		debugLabel = new Label("   Debug Mode");
		debugLabel.setVisible(false);
		debugLabel.setFont(new Font("Arial", 20));
		debugLabel.relocate(30, 20);
		debugLabel.setTextFill(Color.YELLOW);
		debuginfo1 = new Label();
		debuginfo1.setVisible(false);
		debuginfo1.setFont(new Font("Arial", 20));
		debuginfo1.relocate(30, 50);
		debuginfo1.setTextFill(Color.YELLOW);
		debuginfo2 = new Label();
		debuginfo2.setVisible(false);
		debuginfo2.setFont(new Font("Arial", 20));
		debuginfo2.relocate(30, 80);
		debuginfo2.setTextFill(Color.YELLOW);
		debuginfo3 = new Label();
		debuginfo3.setVisible(false);
		debuginfo3.setFont(new Font("Arial", 20));
		debuginfo3.relocate(30, 110);
		debuginfo3.setTextFill(Color.YELLOW);
		debuginfo4 = new Label();
		debuginfo4.setVisible(false);
		debuginfo4.setFont(new Font("Arial", 20));
		debuginfo4.relocate(30, 140);
		debuginfo4.setTextFill(Color.YELLOW);
		debugBox = new Rectangle(175, 155);
		debugBox.setVisible(false);
		debugBox.setX(20);
		debugBox.setY(15);
		debugBox.setFill(Color.rgb(50, 50, 50, 0.8));
		debugBox.setStroke(Color.rgb(200, 200, 200, 0.8));
		debugBox.setStrokeWidth(4);
		
		//pause screen
		pauseScreen = new Rectangle(500, 750);
		pauseScreen.setX(600);
		pauseScreen.setY(300);
		pauseScreen.setFill(Color.rgb(100, 100, 100, 0.8));
		pauseScreen.setStroke(Color.rgb(200, 200, 200, 0.8));
		pauseScreen.setStrokeWidth(3);
		
		pauseScreenText = new Label("PAUSED");
		pauseScreenText.setFont(new Font("Arial", 60));
		pauseScreenText.setTextFill(Color.rgb(200, 200, 60));
		pauseScreenText.relocate(x, y);
		
		instruction = new Label("                    Controls\n"
				+ 				"---------------------------------------------\n"
				+ 				"  WASD/Arrow Keys = Movement\n"
				+ 				"           ESC = Close Game\n"
				+ 				"            P = Spawn Zombie\n"
				+ 				"             M = Debug Mode\n"
				+ 				"           L = Pause/Unpause\n"
				+ "\n\n\n"
				+ 				"                  Instructions\n"
				+ 				"---------------------------------------------\n");
		instruction.setFont(new Font("Arial", 20));
		instruction.setTextFill(Color.rgb(200, 200, 60));
		instruction.relocate(x, y);

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
		
		UIParts.add(debugBox);
		UIParts.add(debuginfo1);
		UIParts.add(debuginfo2);
		UIParts.add(debuginfo3);
		UIParts.add(debuginfo4);
		UIParts.add(debugLabel);
		
		UIParts.add(hurtScreen);
		
		UIParts.add(pauseScreen);
		UIParts.add(pauseScreenText);
		UIParts.add(instruction);
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
	
	public void pauseChange() {
		if (pauseScreen.isVisible()) {
			pauseScreen.setVisible(false);
			pauseScreenText.setVisible(false);
			instruction.setVisible(false);
		}
		else {
			pauseScreen.setVisible(true);
			pauseScreenText.setVisible(true);
			instruction.setVisible(true);
		}
	}
	
	public void setDebug() {
		if(debugMode == false) {
			debugMode = true;
			debugBox.setVisible(true);
			debuginfo1.setVisible(true);
			debuginfo2.setVisible(true);
			debuginfo3.setVisible(true);
			debuginfo4.setVisible(true);
			debugLabel.setVisible(true);
		}
		else {
			debugMode = false;
			debugBox.setVisible(false);
			debuginfo1.setVisible(false);
			debuginfo2.setVisible(false);
			debuginfo3.setVisible(false);
			debuginfo4.setVisible(false);
			debugLabel.setVisible(false);
		}
	}
	
	public boolean isDebug() {
		return debugMode;
	}
	
	public void showInfo(double playerX, double playerY, double MouseX, double MouseY) {
		debuginfo1.setText("player x = " + Math.floor(playerX));
		debuginfo2.setText("player y = " + Math.floor(playerY));
		debuginfo3.setText("mouse x = " + Math.floor(MouseX));
		debuginfo4.setText("mouse y = " + Math.floor(MouseY));
	}
	
	public void showHurtScreen(boolean i) {
		hurtScreen.setVisible(i);
	}


}
