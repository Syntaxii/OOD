package powerups;

import javafx.scene.image.Image;

public class MaxDamagePowerup extends Powerup{

	public MaxDamagePowerup(double powerupX, double powerupY) {
		super(powerupX, powerupY, getimg());
		type = PowerupType.MAXDAMAGE;
	}
	
	public static Image getimg() {
		String imgURL = MaxDamagePowerup.class.getClass().getResource("/images/images/maxdamage.png").toString();
		return new Image(imgURL);
	}

}
