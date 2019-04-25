package enemy;

import java.io.IOException;

public class ZombieFactory {
	public static Enemy createEnemy(EnemyType type, double enemyX, double enemyY) throws IOException {
		Enemy enemy = null;
        try {
			switch (type) {
			    case BASIC:
			        enemy = new BasicZombie(enemyX, enemyY);
			        break;
			    case FAST:
			        enemy = new FastZombie(enemyX, enemyY);
			        break;
			    case LETHAL:
			        enemy = new LethalZombie(enemyX, enemyY);
			        break;
			}
		} catch (Exception e) {
			System.out.println("stuff boke");
			e.printStackTrace();
		}
        return enemy;
	}
}
