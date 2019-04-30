package powerups;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Powerup {
	double powerupX, powerupY;
	Image image;
	ImageView pup;
	PowerupType type;
	ArrayList<Rectangle> pupbacks;

	public Powerup(double powerupX, double powerupY, Image newImage) {
		this.powerupX = powerupX;
		this.powerupY = powerupY;
		this.image = newImage;
		this.pup = new ImageView(image);
		pupbacks = new ArrayList<Rectangle>();

		pup.setVisible(false);
		pup.setScaleX(.4);
		pup.setScaleY(.4);
		
		for (int i = 0; i < 20; i++) {
			pupbacks.add(new Rectangle(100, 15));
			pupbacks.get(i).setFill(Color.rgb(100, 250, 50, .4));
			pupbacks.get(i).setRotate(18*i);
			pupbacks.get(i).setVisible(false);
		}

	}

	public void tick() {
		for (int i = 0, size = pupbacks.size(); i < size; i++) {
			pupbacks.get(i).setRotate(pupbacks.get(i).getRotate()+1);
		}
	}

	public ImageView getPup() {
		return pup;
	}

	public ArrayList<Rectangle> getPupBackground(){
		return pupbacks;
	}

	public void delete() {
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
}
