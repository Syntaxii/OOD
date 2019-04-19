package enemy;
public abstract class Enemy {
	double enemyX, enemyY, playerX, playerY;
	double eVelosity;
	boolean alive = true;
	int health;
	
	public abstract void spawn();
	public abstract void move();
	public abstract void damage();
	public abstract void attack();
	public abstract void tick();
	
}