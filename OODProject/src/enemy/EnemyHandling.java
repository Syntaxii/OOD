package enemy;
import java.util.ArrayList;

public class EnemyHandling {

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Enemy lastKilled = null;
	int score = 0;
	int basicDead = 0;
	int fastDead = 0;
	int lethalDead = 0;

	public void cycleEnemies(double newPlayerX, double newPlayerY) {
		for (int j = 0; j < enemies.size(); j++) {
			if (enemies.get(j).isDelete()) {
				setLastKilled(enemies.get(j));
				removeEnemy(enemies.get(j));
			}
			else
			enemies.get(j).tick(newPlayerX, newPlayerY);
		}
	}
	
	public void setLastKilled(Enemy e) {
		lastKilled = e;
	}
	
	public Enemy getLastKilled() {
		Enemy temp = lastKilled;
		lastKilled = null;
		return temp;
	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public void removeEnemy(Enemy d) {
		enemies.remove(d);
		
		switch(d.getEnemyType()) {
		case BASIC:
			basicDead += 1;
			break;
			
		case FAST:
			fastDead += 1;
			break;
			
		case LETHAL:
			lethalDead += 1;
			break; 
		}
	}

	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}

	public int getScore() {
		score = 1*basicDead + 2*fastDead + 5*lethalDead;
		return score;
	}
	
	public int getAmount() {
		return enemies.size();
	}

	public void clearEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).delete();
		}
		enemies.removeAll(enemies);
		
		basicDead = 0;
		fastDead = 0;
		lethalDead = 0;
		score = 0;
	}

}
