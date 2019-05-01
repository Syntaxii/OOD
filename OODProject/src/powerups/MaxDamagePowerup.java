package powerups;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MaxDamagePowerup extends Powerup{

	public MaxDamagePowerup(double powerupX, double powerupY) {
		super(powerupX, powerupY, getimgview());
		type = PowerupType.MAXDAMAGE;
		setColor(Color.rgb(100, 250, 50, .4));
	}
	
	public static ImageView getimgview() {
		String imgURL = MaxDamagePowerup.class.getClass().getResource("/images/images/maxdamage.png").toString();
		ImageView img = new ImageView(imgURL);
		img.setVisible(false);
		img.setScaleX(.4);
		img.setScaleY(.4);
		return img;
	}

}
