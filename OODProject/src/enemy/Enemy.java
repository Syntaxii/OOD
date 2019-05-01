package enemy;

import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class Enemy{
	protected double enemyX, enemyY, playerX, playerY;
	protected double eSpeed; //enemy Speed
	protected double angle, moveX, moveY;
	protected boolean alive = true;
	protected int health = 100;
	protected int damage;
	protected boolean isAlive = false;
	protected Image image;
	protected ImageView zomb;
	protected boolean isDelete = false;
	protected EnemyType enemyType;
	
	public Enemy(double enemyX, double enemyY, Image temporary, double size) throws IOException {
		String imgURL = this.getClass().getResource("/images/sprites/sprite_animation/zombie_animation/regular_zombie_move.gif").toString();
		this.image = new Image(imgURL);
		zomb = new ImageView(image);
		this.enemyX = enemyX;
		this.enemyY = enemyY;
		instantiate(size);
	}

	public void move() {
		rotate();
		double radianangle = Math.toRadians(angle);
		double tempx = (playerX-159)-enemyX;
		double tempy = (playerY-146)-enemyY;
		if (Math.sqrt(Math.pow(tempx, 2) + Math.pow(tempy, 2)) >= 60) {
			moveX = Math.cos(radianangle);
			enemyX = enemyX + (moveX*eSpeed);
			moveY = Math.sin(radianangle);
			enemyY = enemyY + (moveY*eSpeed);
		}
		
		zomb.relocate(enemyX, enemyY);		
	}
	
	public double getEnemyX() {
		return enemyX;
	}
	
	public double getEnemyY() {
		return enemyY;
	}


	public void spawn() {
		zomb.setVisible(true);
	}


	public void rotate() {
		angle = Math.atan2(playerY-150 - enemyY, playerX-160 - enemyX) * 180 / Math.PI;
		zomb.setRotate(angle);

	}

	public void receiveDamage(int d) {
		health = health - d;
	}

	public void delete() {
		zomb.setVisible(false);
		isAlive = false;
		zomb.setDisable(true);
		zomb.relocate(-500, -500);
		isDelete = true;
	}

	public void tick(double newPlayerX, double newPlayerY) { //requires player coordinates
		if (health <=0) {
			isAlive = false;
			delete();
		}
		if (isAlive == true) {
			playerX = newPlayerX;
			playerY = newPlayerY;
			move();
		}
	}
	
	public void instantiate(double size) {
		zomb.setVisible(false);
		zomb.setScaleX(size);
		zomb.setScaleY(size);
		zomb.relocate(enemyX, enemyY);
		isAlive = true;
	}

	public ImageView getEnemy() {
		return zomb;
	}
	
	public void setSpeed(double i) {
		eSpeed = i;
	}
	
	public boolean isDelete() {
		return isDelete;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public EnemyType getEnemyType() {
		return enemyType;
	}
	
}