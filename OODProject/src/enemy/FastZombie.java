package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FastZombie extends Enemy{
	
	public void setEnemy(double enemyX, double enemyY) {
		image = new Image("file:src/images/BasicZombie.png");
		zomb = new ImageView(image);
		this.enemyX = enemyX;
		this.enemyY = enemyY; 
		instantiate(.4);
	}
}
