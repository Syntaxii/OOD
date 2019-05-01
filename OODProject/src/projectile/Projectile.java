package projectile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Set;

import enemy.Enemy;

public abstract class Projectile {
	protected double x, y, mouseX, mouseY;
	protected double VelX = 0, VelY = 0;
	protected double speed = 20;
	protected double angle = 0;
	protected int damage;
	protected int projectileTimeOutTicker = 0;
	protected Circle sphere;
	protected Set<Enemy> hitEnemies = new HashSet<Enemy>();
	

	public Projectile(double x, double y, double newmouseX, double newmouseY) {
		this.x = x;
		this.y = y;
		mouseX = newmouseX;
		mouseY = newmouseY;
		angle = Math.atan2(mouseY - y, mouseX - x) * 180 / Math.PI;
		setVelocity(angle);
		createProjectile();
	}



	public void setVelocity(double newAngle) {
		double radianAngle = Math.toRadians(newAngle);
		VelX = Math.cos(radianAngle) *speed;
		VelY = Math.sin(radianAngle) *speed;
	}

	public void createProjectile() {
		sphere = new Circle(5);
		sphere.setFill(Color.YELLOW);
		sphere.setCenterX(x);
		sphere.setCenterY(y);

	}

	public Circle getProjectile() {
		return sphere;
	}

	public void MoveProjectile() {
		sphere.setCenterX(x);
		sphere.setCenterY(y);
	}

	public void tick() {
		x += VelX;
		y += VelY;
		projectileTimeOutTicker++;
	}

	public int getTimeoutTicker() {
		return projectileTimeOutTicker;
	}

	public void timeoutProjectile() {
		sphere.setVisible(false);
		sphere.setDisable(true);
	}

	public int getDamage() {
		damage = damage - 10;
		if (damage < 5) damage = 5;
		return damage;
	}
	
	public void addHitEnemy(Enemy e) {
		hitEnemies.add(e);
	}
	
	public Set<Enemy> getHitEnemies(){
		return hitEnemies;
	}
}