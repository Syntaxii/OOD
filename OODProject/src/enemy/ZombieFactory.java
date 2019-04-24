package enemy;

public class ZombieFactory {
	public static Enemy createEnemy(EnemyType type, double enemyX, double enemyY) {
		Enemy enemy = null;
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
        return enemy;
	}
}
