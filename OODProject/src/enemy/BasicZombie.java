package enemy;

import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BasicZombie extends Enemy{
	
	public BasicZombie(double enemyX, double enemyY) throws IOException {
		super(enemyX, enemyY, null, .4);
		setSpeed(1);
		health = 100;
		damage = 20;
		enemyType = EnemyType.BASIC;
	}

}
