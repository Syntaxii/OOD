package powerups;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Ammo3Powerup extends Powerup{

	public Ammo3Powerup(double powerupX, double powerupY) {
		super(powerupX, powerupY, getimgview());
		type = PowerupType.AMMO3;
		setColor(Color.rgb(200, 200, 50, .4));
		setSize(150, 22.5);
	}
	
	public static ImageView getimgview() {
		String imgURL = MaxDamagePowerup.class.getClass().getResource("/images/images/ammo3.png").toString();
		ImageView img = new ImageView(imgURL);
		img.setVisible(false);
		img.setScaleX(.4);
		img.setScaleY(.4);
		return img;
	}
	
	@Override
	public void spawn() {
		pup.relocate(powerupX-110, powerupY-50);
		pup.setVisible(true);
		for (int i = 0; i < pupbacks.size(); i++) {
			pupbacks.get(i).setVisible(true);
			pupbacks.get(i).relocate(powerupX+14, powerupY+53);
		}
	}

}
