package enemy;
import java.util.LinkedList;

public class EnemyHandling {
	
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	
	
	public void cycleEnemies(double newPlayerX, double newPlayerY) {
		for (int j = 0; j < enemies.size(); j++) {
			Enemy temp = enemies.get(j);
			
			temp.tick(newPlayerX, newPlayerY);
			
			}

		}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}
	
	public void removeEnemy(Enemy d) {
		enemies.remove(d);
	}
	
	public void clearEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).delete();
		}
	}

}
