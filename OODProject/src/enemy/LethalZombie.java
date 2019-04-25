package enemy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LethalZombie extends Enemy{
	
	public LethalZombie(double enemyX, double enemyY) {
		super(enemyX, enemyY, new Image("file:src/images/zombie_move.gif"), 1);
		setSpeed(.5);
		health = 250;
		damage = 40;
		enemyType = EnemyType.LETHAL;
	}
	
}