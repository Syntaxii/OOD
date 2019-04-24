package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class Enemy{
	double enemyX, enemyY, playerX, playerY;
	double eSpeed; //enemy Speed
	double angle, moveX, moveY;
	boolean alive = true;
	int health;
	boolean isAlive = false;
	Image image;
	ImageView zomb;
	
	public Enemy(double enemyX, double enemyY, Image image, double size) {
		this.image = image;
		zomb = new ImageView(image);
		this.enemyX = enemyX;
		this.enemyY = enemyY;
		instantiate(size);
	}
	
	public void move() {
		rotate();
		double radianangle = Math.toRadians(angle);
		
		if(Math.abs((playerX-160)-enemyX)>= 50) {
			moveX = Math.cos(radianangle);
			enemyX = enemyX + (moveX*eSpeed);
		}
		if(Math.abs((playerY-150)-enemyY)>= 50) {
			moveY = Math.sin(radianangle);
			enemyY = enemyY + (moveY*eSpeed);
		}
		
		zomb.relocate(enemyX, enemyY);		
	}


	public void spawn() {
		zomb.setVisible(true);
	}


	public void rotate() {
		angle = Math.atan2(playerY-150 - enemyY, playerX-160 - enemyX) * 180 / Math.PI;
		zomb.setRotate(angle);

	}

	public void receiveDamage() {

	}

	public void attack() {

	}

	public void delete() {
		zomb.setVisible(false);
		isAlive = false;
	}

	public void tick(double newPlayerX, double newPlayerY) { //requires player coordinates
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
	
}