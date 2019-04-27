package enemy;
import java.util.LinkedList;

public class EnemyHandling {

	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	LinkedList<Enemy> deadEnemies = new LinkedList<Enemy>();

	public void cycleEnemies(double newPlayerX, double newPlayerY) {
		for (int j = 0; j < enemies.size(); j++) {
			if (enemies.get(j).isDelete()) removeEnemy(enemies.get(j));
			else
			enemies.get(j).tick(newPlayerX, newPlayerY);


		}

	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public void removeEnemy(Enemy d) {
		enemies.remove(d);
		deadEnemies.add(d);
		
	}

	public LinkedList<Enemy> getEnemies(){
		return enemies;
	}

	public int getScore() {
		int Score = 0;
		for (int i = 0; i < deadEnemies.size(); i++) {
			switch(deadEnemies.get(i).getEnemyType()) {
			case BASIC: Score += 1;
				break;
			case FAST: Score += 2;
				break;
			case LETHAL: Score += 5;
				break; 
			}
		}
		return Score;
	}

	public void clearEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).delete();
		}
	}

}
