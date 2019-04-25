package enemy;

import java.io.IOException;

import javafx.scene.image.Image;

public class FastZombie extends Enemy{
	
	public FastZombie(double enemyX, double enemyY) throws IOException {
		super(enemyX, enemyY, null, .3);
		setSpeed(3);
		health = 50;
		damage = 10;
		enemyType = EnemyType.FAST;
	}

	
}