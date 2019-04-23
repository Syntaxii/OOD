package enemy;

import javafx.scene.image.ImageView;

public interface Enemy {
	
	public abstract void setEnemy(double enemyX, double enemyY, double playerX, double playerY, double speed);
	public abstract void spawn();
	public abstract void rotate();
	public abstract void move();
	public abstract void receiveDamage();
	public abstract void attack();
	public abstract void tick(double newPlayerX, double newPlayerY);
	public abstract ImageView getEnemy();
	
}