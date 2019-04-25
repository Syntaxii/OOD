package enemy;

import javafx.scene.image.Image;

public class FastZombie extends Enemy{
	
	public FastZombie(double enemyX, double enemyY) {
		super(enemyX, enemyY, new Image("file:src/images/zombie_move.gif"), .3);
		setSpeed(3);
		health = 50;
		damage = 10;
		enemyType = EnemyType.FAST;
	}

	
}