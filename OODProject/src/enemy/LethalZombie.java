package enemy;

import java.io.IOException;

public class LethalZombie extends Enemy{
	

	public LethalZombie(double enemyX, double enemyY) throws IOException {
		super(enemyX, enemyY, null, 1);

		setSpeed(.5);
		health = 250;
		damage = 40;
		enemyType = EnemyType.LETHAL;
	}
	
}