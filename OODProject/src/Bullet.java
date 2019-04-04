import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Projectile{
    private Circle sphere;
    private double VelX, VelY;
    public Bullet(double mouseX, double mouseY, double cx, double cy) {
        super(cx, cy, mouseX, mouseY);
        VelX = (mouseX - x ) / 10;
        VelY = (mouseY - y) / 10;
        sphere = new Circle(5);
        sphere.setFill(Color.YELLOW);
        sphere.setCenterX(cx);
        sphere.setCenterY(cy);
        
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

    
//    private void moveBy(double dx, double dy) {
//		if (dx == 0 && dy == 0) return; //redundant
//		double cx = player.getBoundsInLocal().getWidth()  / 2;
//		double cy = player.getBoundsInLocal().getHeight() / 2;
//		double x = cx + player.getLayoutX() + dx;
//		double y = cy + player.getLayoutY() - dy;
//		moveTo(x, y);
//	}
}