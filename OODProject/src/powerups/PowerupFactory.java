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
			}
		} catch (Exception e) {
			System.out.println("stuff boke");
			e.printStackTrace();
		}
        return powerup;
	}
}
