public abstract class Projectile {
	protected double x, y, mouseX, mouseY;
	protected double VelX = 0, VelY = 0;
    protected double speed = 20;
    protected double angle = 0;
	protected int projectileTimeOutTicker = 0;

	public Projectile(double x, double y, double mouseX, double mouseY) {
		this.x = x;
		this.y = y;
		angle = Math.atan2(mouseY - y, mouseX - x) * 180 / Math.PI;
	}
	
	public abstract void MoveProjectile();

	public abstract void tick();
	
	public abstract int getTimeoutTicker();
	
	public abstract void timeoutProjectile();
}