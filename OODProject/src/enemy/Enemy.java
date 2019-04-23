package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public interface Enemy {
		
	public abstract void setEnemy(double enemyX, double enemyY);
	public abstract void move();
	public abstract void spawn();
	public abstract void rotate(); 
	public abstract void receiveDamage();
	public abstract void attack();
	public abstract void delete();
	public abstract void tick(double newPlayerX, double newPlayerY);
	public abstract void instantiate(double size);
	public abstract ImageView getEnemy();
	public abstract void setSpeed(double i); 
}