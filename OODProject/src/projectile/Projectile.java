package projectile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Projectile {
	protected double x, y, mouseX, mouseY;
	protected double VelX = 0, VelY = 0;
	protected double speed = 20;
	protected double angle = 0;
	protected int damage = 10;
	protected int projectileTimeOutTicker = 0;
	protected Circle sphere;

	public Projectile(double x, double y, double mouseX, double mouseY) {
		this.x = x;
		this.y = y;
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
		return damage;
	}
}