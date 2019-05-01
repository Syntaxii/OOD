package powerups;

import java.util.LinkedList;


public class PowerupHandling {

	LinkedList<Powerup> powerups = new LinkedList<Powerup>();

	private int PowerupLifespan = 60*10;

	public void cyclePowerups() {
		for (int j = 0; j < powerups.size(); j++) {
			powerups.get(j).tick();
			if (powerups.get(j).getTimeoutTicker() >= .6*PowerupLifespan&& powerups.get(j).getTimeoutTicker() < .8*PowerupLifespan && powerups.get(j).getTimeoutTicker()%16==0) {
				powerups.get(j).flash();
			}

			else if (powerups.get(j).getTimeoutTicker() >= .8*PowerupLifespan && powerups.get(j).getTimeoutTicker()%8==0)
				powerups.get(j).flash();

			else if(powerups.get(j).getTimeoutTicker() > PowerupLifespan) {
				powerups.get(j).delete();
				powerups.remove(powerups.get(j));
			}
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
