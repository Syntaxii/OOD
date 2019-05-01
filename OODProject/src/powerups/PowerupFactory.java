package powerups;

import java.io.IOException;


public class PowerupFactory {
	public static Powerup createPowerup(PowerupType type, double powerupX, double powerupY) throws IOException {
		Powerup powerup = null;
        try {
			switch (type) {
			    case MAXDAMAGE:
			        powerup = new MaxDamagePowerup(powerupX, powerupY);
			        break;
			    case REGENERATION:
			    	//powerup = new Regeneration(powerupX, powerupY);
			    	break;
			    case AMMO2:
			    	//powerup = new Ammo2(powerupX, powerupY);
			    	break;
			    case AMMO3:
			    	//powerup = new Ammo3(powerupX, powerupY);
			    	break;
			}
		} catch (Exception e) {
			System.out.println("stuff broke");
			e.printStackTrace();
		}
        return powerup;
	}
}
