package powerups;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Powerup {
	protected double powerupX, powerupY;
	protected int PowerupTimeOutTicker = 0;
	protected boolean isFlashing = false;
	protected ImageView pup; //Powerup
	protected PowerupType type;
	protected ArrayList<Rectangle> pupbacks; //Powerup backgrounds

	public Powerup(double powerupX, double powerupY, ImageView newImage) {
		this.powerupX = powerupX;
		this.powerupY = powerupY;
		this.pup = newImage;
		pupbacks = new ArrayList<Rectangle>();

		for (int i = 0; i < 20; i++) {
			pupbacks.add(new Rectangle(100, 15));
			pupbacks.get(i).setRotate(18*i);
			pupbacks.get(i).setVisible(false);
		}
	}
	public void setColor(Color color) {
		for (int i = 0; i < 20; i++) {
			pupbacks.get(i).setFill(color);
		}
	}

	public void setSize(double width, double height) {
		for (int i = 0; i < 20; i++) {
			pupbacks.get(i).setWidth(width);
			pupbacks.get(i).setHeight(height);
		}
	}

	public void tick() {
		if (isFlashing) flash();
		for (int i = 0, size = pupbacks.size(); i < size; i++) {
			pupbacks.get(i).setRotate(pupbacks.get(i).getRotate()+1);
		}
		PowerupTimeOutTicker++;
	}

	public ImageView getPup() {
		return pup;
	}

	public ArrayList<Rectangle> getPupBackground(){
		return pupbacks;
	}

	public void delete() {
		isFlashing = false;
		pup.setVisible(false);
		pup.relocate(-500, -500);
		int size = pupbacks.size();
		for (int i = 0; i < size; i++) {
			pupbacks.get(i).setVisible(false);
			pupbacks.get(i).relocate(-500, -500);
		}
		pupbacks.removeAll(pupbacks);
	}

	public void spawn() {
		pup.relocate(powerupX, powerupY);
		pup.setVisible(true);
		for (int i = 0; i < pupbacks.size(); i++) {
			pupbacks.get(i).setVisible(true);
			pupbacks.get(i).relocate(powerupX+14, powerupY+53);
		}
	}

	public PowerupType getType() {
		return type;
	}
	public int getTimeoutTicker() {
		return PowerupTimeOutTicker;
	}
	public void flash() {
		if (pup.isVisible()) {
			pup.setVisible(false);
			for (int i = 0; i < pupbacks.size(); i++) {
				pupbacks.get(i).setVisible(false);
			}
		}
		else {
			pup.setVisible(true);
			for (int i = 0; i < pupbacks.size(); i++) {
				pupbacks.get(i).setVisible(true);
			}
		}
	}
}
