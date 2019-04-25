package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicZombie extends Enemy{
	
	public BasicZombie(double enemyX, double enemyY) {
		super(enemyX, enemyY, new Image("file:src/images/zombie_move.gif"), .4);
		setSpeed(1);
		health = 100;
		damage = 20;
		enemyType = EnemyType.BASIC;
	}

}
