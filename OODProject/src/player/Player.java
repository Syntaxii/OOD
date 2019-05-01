package player;
import java.io.IOException;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import powerups.PowerupType;

public class Player {

	private static ImageView playerpic1, playerpic2, playerpic3;
	private static double Health = 100;
	private static Player player;
	private static boolean hpWarn = false;
	private static boolean playerAlive = true;
	private boolean isMAXDAMAGE = false;
	private boolean MAXDAMAGEflash = false;
	private int maxDamageTime = 10*60; //set length
	private int maxDamageTimeRemaining = 0; //length remaining
	private boolean isREGENERATION = false;
	private boolean REGENERATIONflash = false;
	private int regenerationTime = 10*60; //set length
	private int regenerationTimeRemaining = 0; //length remaining
	private int weapon2ammo = 0;
	private int weapon3ammo = 0;
	private ImageView [] playerpics;
	ColorAdjust faded;


	private Player() throws IOException {

		String imgURL1 = this.getClass().getResource("/images/sprites/sprite_animation/player_animation/player_handgun/player_handgun_move.gif").toString();
		Image playerImage1 = new Image(imgURL1);

		
		String imgURL2 = this.getClass().getResource("/images/sprites/sprite_animation/player_animation/player_shotgun/player_shotgun_move.gif").toString();
		Image playerImage2 = new Image(imgURL2);
		
		String imgURL3 = this.getClass().getResource("/images/sprites/sprite_animation/player_animation/player_rifle/player_rifle_move.gif").toString();
		Image playerImage3 = new Image(imgURL3);

		playerpic1 = new ImageView(playerImage1);
		playerpic2 = new ImageView(playerImage2);
		playerpic2.setVisible(false);
		playerpic3 = new ImageView(playerImage3);
		playerpic3.setVisible(false);
		playerpics = new ImageView[3];
		playerpics[0] = playerpic1;
		playerpics[1] = playerpic2;
		playerpics[2] = playerpic3;
		
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

	public ImageView[] getPic() {
		return playerpics;
	}

	public double getHealth() {
		return Health;
	}

	public void setHealth(double hp) {
		if (hp >= 100) hp = 100;
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
	public void setInvulnerable(boolean g) {
		for (ImageView v : playerpics)
		if(g==true) {
			v.setEffect(faded);
		}
		else v.setEffect(null);
	}
	public void setStatus(PowerupType type) {
		switch(type) {
		case MAXDAMAGE:
			isMAXDAMAGE = true;
			MAXDAMAGEflash = false;
			maxDamageTimeRemaining = maxDamageTime;
			break;
		case REGENERATION:
			isREGENERATION = true;
			REGENERATIONflash = false;
			regenerationTimeRemaining = regenerationTime; 
			break;
		default:
			break;
		}
	}

	public void increaseAmmo(PowerupType type, int amount) {
		if (type == PowerupType.AMMO2) {
			if (getAmmo(type) + weapon2ammo >= 99) weapon2ammo = 99;
			else weapon2ammo += amount;
		}
		else {
			if (getAmmo(type) + weapon3ammo >= 99) weapon3ammo = 99;
			else weapon3ammo += amount;
		}
	}

	public void decreaseAmmo(PowerupType type) {
		if (type == PowerupType.AMMO2)weapon2ammo--;
		else weapon3ammo--;
	}

	public int getAmmo(PowerupType type) {
		if (type == PowerupType.AMMO2) return weapon2ammo;
		else return weapon3ammo;

	}

	public boolean getStatusMaxDamage() {
		return isMAXDAMAGE;
	}

	public boolean getFlashStatusMaxDamage() {
		return MAXDAMAGEflash;
	}

	public boolean getStatusRegeneration() {
		return isREGENERATION;
	}

	public boolean getFlashStatusRegeneration() {
		return REGENERATIONflash;
	}

	public void cycleStatuses() {
		if (isMAXDAMAGE) {
			maxDamageTimeRemaining--;
			if (maxDamageTimeRemaining == 0) {
				isMAXDAMAGE = false;
				MAXDAMAGEflash = false;
			}
			else if(maxDamageTimeRemaining == 3*60) {
				MAXDAMAGEflash = true;
			}
		}

		if (isREGENERATION) {
			regenerationTimeRemaining--;
			if (regenerationTimeRemaining == 0) {
				isREGENERATION = false;
				REGENERATIONflash = false;
			}
			else if(regenerationTimeRemaining == 3*60) {
				REGENERATIONflash = true;
			}
		}
	}

	public void reset() {
		setHealth(100);
		setAlive();

		maxDamageTimeRemaining = 0;
		isMAXDAMAGE = false;
		MAXDAMAGEflash = false;

		regenerationTimeRemaining = 0;
		isREGENERATION = false;
		REGENERATIONflash = false;
		
		weapon2ammo = 0;
		weapon3ammo = 0;

	}
	public void changePic(int i) {
		switch(i) {
		case 1:
			playerpic1.setVisible(true);
			playerpic2.setVisible(false);
			playerpic3.setVisible(false);
			break;
		case 2:
			playerpic1.setVisible(false);
			playerpic2.setVisible(true);
			playerpic3.setVisible(false);
			break;
		case 3:
			playerpic1.setVisible(false);
			playerpic2.setVisible(false);
			playerpic3.setVisible(true);
			break;
		default:
			break;
		}

	}
}