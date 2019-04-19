package enemy;
import java.util.LinkedList;

public class EnemyHandling {
	
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	
	
	public void cycleProjectiles() {
		for (int j = 0; j < enemies.size(); j++) {
			Enemy temp = enemies.get(j);
			
//			temp.tick();
//			temp.MoveProjectile();
			
			}

		}

	public void addProjectile(Enemy e) {
		enemies.add(e);
	}
	
	public void removeProjectile(Enemy d) {
		enemies.remove(d);
	}
	
	public void clearProjectiles() {
		for (int i = 0; i < enemies.size(); i++) {
//			enemies.get(i).timeoutProjectile();
		}
	}

}
