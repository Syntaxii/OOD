package projectile;
import java.util.ArrayList;

public class ProjectileHandling {
	
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	private int projectileLifeSpan = 300;
	
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
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public void removeProjectile(Projectile d) {
		projectiles.remove(d);
	}
	
	public void clearProjectiles() {
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).timeoutProjectile();
		}
		
		projectiles.removeAll(projectiles);
	}

}
