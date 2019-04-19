package projectile;
import java.util.LinkedList;

public class ProjectileHandling {
	
	LinkedList<Projectile> projectiles = new LinkedList<Projectile>();

	int projectileLifeSpan = 300;
	
	public void cycleProjectiles() {
		for (int j = 0; j < projectiles.size(); j++) {
			Projectile temp = projectiles.get(j);
			
			temp.tick();
			temp.MoveProjectile();
			if(temp.getTimeoutTicker() > projectileLifeSpan) {
				temp.timeoutProjectile();
				projectiles.remove(temp);
			}

		}
	}

	public void addProjectile(Projectile e) {
		projectiles.add(e);
	}
	
	public void removeProjectile(Projectile d) {
		projectiles.remove(d);
	}
	
	public void clearProjectiles() {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).timeoutProjectile();
		}
	}

}
