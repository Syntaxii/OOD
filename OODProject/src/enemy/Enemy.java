package enemy;

import javafx.scene.image.ImageView;

public abstract class Enemy {
	double enemyX, enemyY, playerX, playerY;
	double eSpeed; //enemy Speed
	double angle;
	boolean alive = true;
	int health;
	boolean isAlive = false;
	
	public Enemy(double enemyX, double enemyY, double playerX, double playerY, double speed) {
		this.enemyX = enemyX;
		this.enemyY = enemyY; 
		this.playerX = playerX;
		this.playerY = playerY;
		eSpeed = speed;
	}
	
	public abstract void spawn();
	public abstract void rotate();
	public abstract void move();
	public abstract void receiveDamage();
	public abstract void attack();
	public abstract void delete();
	public abstract void tick(double newPlayerX, double newPlayerY);
	public abstract ImageView getEnemy();
	
}