package powerups;

import java.util.LinkedList;


public class PowerupHandling {

	LinkedList<Powerup> powerups = new LinkedList<Powerup>();

	public void cyclePowerups() {
		for (int j = 0; j < powerups.size(); j++) {
			powerups.get(j).tick();
		}
	}

	public void addPowerup(Powerup e) {
		powerups.add(e);
	}

	public void removePowerup(Powerup d) {
		powerups.remove(d);
	}

	public LinkedList<Powerup> getPowerups(){
		return powerups;
	}

	public void clearPowerups() {
		for (int i = 0; i < powerups.size(); i++) {
			powerups.get(i).delete();
			
		}
		powerups.removeAll(powerups);
	}

}
