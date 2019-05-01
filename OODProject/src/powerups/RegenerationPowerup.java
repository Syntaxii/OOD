package powerups;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RegenerationPowerup  extends Powerup{

	public RegenerationPowerup(double powerupX, double powerupY) {
		super(powerupX, powerupY, getimgview());
		type = PowerupType.REGENERATION;
		setColor(Color.rgb(100, 250, 50, .4));
	}

	public static ImageView getimgview() {
		String imgURL = MaxDamagePowerup.class.getClass().getResource("/images/images/Regeneration.png").toString();
		ImageView img = new ImageView(imgURL);
		img.setVisible(false);
		img.setScaleX(.025);
		img.setScaleY(.025);
		return img;
	}
	@Override
	public void spawn() {
		pup.relocate(powerupX-1136, powerupY-950);
		pup.setVisible(true);
		for (int i = 0; i < pupbacks.size(); i++) {
			pupbacks.get(i).setVisible(true);
			pupbacks.get(i).relocate(powerupX+14, powerupY+53);
		}
	}

}
