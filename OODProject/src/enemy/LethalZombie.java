package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LethalZombie extends Enemy{
	
	public LethalZombie(double enemyX, double enemyY) {
		super(enemyX, enemyY, new Image("file:src/images/BasicZombie.png"), 1);
		setSpeed(.5);
		health = 250;
		damage = 40;
		enemyType = EnemyType.LETHAL;
	}

}