package player;
import java.io.IOException;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import powerups.PowerupType;

public class Player {
	private volatile static ImageView playerpic;

	private static int Health = 100;
	private static Player player;
	private static boolean hpWarn = false;
	private static boolean playerAlive = true;
	private boolean isMAXDAMAGE = false;
	private boolean MAXDAMAGEflash = false;
	private int maxDamageTime = 15*60; //set length
	private int maxDamageTimeRemaining = 0; //length remaining
	ColorAdjust faded;


	private Player() throws IOException {
		
		String imgURL = this.getClass().getResource("/images/images/player.gif").toString();
		Image playerImage = new Image(imgURL);
		playerpic = new ImageView(playerImage);
		faded = new ColorAdjust();
		faded.setSaturation(-.3);
		faded.setBrightness(.3);
	}
	public static Player getInstance() throws IOException {
		if(player != null) {
			System.out.println("Testing 2nd instantiation denied");
		}
		if (player == null) {
			synchronized (Player.class) {
				if (player == null) {
					player = new Player();
				}
			}
		}
		return player;
	}

	public ImageView getPic() {
		return playerpic;
	}

	public int getHealth() {
		return Health;
	}

	public void setHealth(int hp) {
		Health = hp;
		if (hp < 40) {
			hpWarn = true;
			if (hp <= 0) {
				hpWarn = false;
				setDead();
			}

		}
		else hpWarn = false;
	}

	public boolean checkHPWarn() {
		return hpWarn;
	}

	public boolean isAlive() {
		return playerAlive;
	}

	public void setDead() {
		playerAlive = false;
	}
	public void setAlive() {
		playerAlive = true;
	}
	public void setInvulnerable(boolean v) {
		if(v==true) {
			playerpic.setEffect(faded);
		}
		else playerpic.setEffect(null);
	}
	public void setStatus(PowerupType type) {
		switch(type) {
		case MAXDAMAGE:
			isMAXDAMAGE = true;
			maxDamageTimeRemaining = maxDamageTime;
			break;
		}
	}
	public boolean getStatusMaxDamage() {
		return isMAXDAMAGE;
	}
	
	public boolean getFlashStatusMaxDamage() {
		return MAXDAMAGEflash;
	}
	
	public void cycleStatuses() {
		if (isMAXDAMAGE == true) {
			maxDamageTimeRemaining -= 1;
			if (maxDamageTimeRemaining == 0) {
				isMAXDAMAGE = false;
				MAXDAMAGEflash = false;
			}
			else if(maxDamageTimeRemaining == 3*60) {
				MAXDAMAGEflash = true;
			}
		}
	}
	
	public void reset() {
		setHealth(100);
		setAlive();
		
		maxDamageTimeRemaining = 0;
		isMAXDAMAGE = false;
	}
}