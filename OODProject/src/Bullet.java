import javafx.scene.Node;
import javafx.scene.shape.Circle;
public class Bullet {
    private Circle sphere;
    private double Mx, My, Px, Py, VelX, VelY;
    public Bullet(double mouseX, double mouseY, double cx, double cy) {
        this.Mx = mouseX;
        this.My = mouseY;
        this.Px = cx;
        this.Py = cy;
        VelX = (cx - mouseX) / 10;
        VelY = (cy - mouseY) / 10;
    }
}