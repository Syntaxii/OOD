package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicZombie extends Enemy{
	private Image image = new Image("file:src/images/BasicZombie.png");
	private ImageView zomb = new ImageView(image);
	double enemyX, enemyY, playerX, playerY;
	double eSpeed; //enemy Speed
	double angle, moveX, moveY;
	int health;


	public void setEnemy(double enemyX, double enemyY, double playerX, double playerY, double speed) {
		this.enemyX = enemyX;
		this.enemyY = enemyY; 
		this.playerX = playerX;
		this.playerY = playerY;
		eSpeed = speed;
		zomb.setVisible(false);
		zomb.setScaleX(.4);
		zomb.setScaleY(.4);
		zomb.relocate(enemyX, enemyY);
		isAlive = true;
	}

	@Override
	public void spawn() {
		zomb.setVisible(true);
	}

	@Override
	public void move() {
		//TODO add move; also make it so the enemy stops moving if within *some* distance of the player
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

	@Override
	public void rotate() {
		angle = Math.atan2(playerY-150 - enemyY, playerX-160 - enemyX) * 180 / Math.PI;
		zomb.setRotate(angle);

	}

	@Override
	public void receiveDamage() {

	}

	@Override
	public void attack() {

	}

	public void delete() {
		zomb.setVisible(false);
		isAlive = false;
	}

	@Override
	public void tick(double newPlayerX, double newPlayerY) { //requires player coordinates
		if (isAlive == true) {
			playerX = newPlayerX;
			playerY = newPlayerY;
			move();
		}
	}

	public ImageView getEnemy() {
		return zomb;
	}
}
