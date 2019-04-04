
public abstract class Projectile {
	protected double x;
	protected double y;
	protected double VelX = 0, VelY = 0;
	protected int projectileTimeOutTicker = 0;

	public Projectile(double x, double y, double mouseX, double mouseY) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void MoveProjectile();

	public abstract void tick();
	
	public abstract int getTimeoutTicker();
	
	public abstract void timeoutProjectile();
}
