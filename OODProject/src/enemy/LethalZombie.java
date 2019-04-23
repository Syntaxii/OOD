package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LethalZombie extends Enemy{
	private Image image = new Image("https://opengameart.org/sites/default/files/skeleton-attack_0.png");
	private ImageView zomb = new ImageView(image);
	double enemyX, enemyY, playerX, playerY;
	double eSpeed; //enemy Speed
	double angle;
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
	}
	
	@Override
	public void spawn() {
		zomb.setVisible(true);
	}

	@Override
	public void move() {
		//TODO add move; also make it so the enemy stops moving if within *some* distance of the player
		rotate();
	}
	
	@Override
	public void rotate() {
		angle = Math.atan2(playerY - enemyY, playerX - enemyX) * 180 / Math.PI;
		zomb.setRotate(angle);
	}

	@Override
	public void receiveDamage() {
		
	}

	@Override
	public void attack() {
		
	}

	@Override
	public void tick(double newPlayerX, double newPlayerY) { //requires player coordinates
		playerX = newPlayerX;
		playerY = newPlayerY;
	}
	
	public ImageView getEnemy() {
		return zomb;
	}

	@Override
	public void delete() {
		zomb.setVisible(false);
		isAlive = false;
		
	}
}
