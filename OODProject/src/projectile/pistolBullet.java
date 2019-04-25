package projectile;

public class pistolBullet extends Projectile{

	public pistolBullet(double mouseX, double mouseY, double cx, double cy) {
		super(cx, cy, mouseX, mouseY);
		speed = 20;
		damage = 25;
	}

}