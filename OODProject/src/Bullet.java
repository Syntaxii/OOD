import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Projectile{
    private Circle sphere;
    public Bullet(double mouseX, double mouseY, double cx, double cy) {
        super(cx, cy, mouseX, mouseY);
        setVelocity(speed, angle);
        createBullet();
        
    }
    
    public void setVelocity(double newSpeed, double newAngle) {
		double radianAngle = Math.toRadians(newAngle);
		VelX = Math.cos(radianAngle) *newSpeed;
		VelY = Math.sin(radianAngle) *newSpeed;
    }
    
    public void createBullet() {
        sphere = new Circle(5);
        sphere.setFill(Color.YELLOW);
        //TODO offset x and y using math so bullet comes out of gun
        sphere.setCenterX(x);
        sphere.setCenterY(y);

    }
    
    public Circle getBullet() {
    	return sphere;
    }
    
	@Override
    public void MoveProjectile() {
    	sphere.setCenterX(x);
    	sphere.setCenterY(y);
    }

	@Override
	public void tick() {
		x += VelX;
		y += VelY;
		projectileTimeOutTicker++;
	}
	
	@Override
	public int getTimeoutTicker() {
		return projectileTimeOutTicker;
	}

	public void timeoutProjectile() {
		sphere.setVisible(false);
	}
}