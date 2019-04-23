package enemy;

public class ZombieFactory {
	public static Enemy createEnemy(EnemyType type) {
		Enemy enemy = null;
        switch (type) {
            case BASIC:
                enemy = new BasicZombie();
                enemy.setSpeed(1);
                break;
            case FAST:
                enemy = new FastZombie();
                enemy.setSpeed(2);
                break;
            case LETHAL:
                enemy = new LethalZombie();
                enemy.setSpeed(.75);
                break;
        }
        return enemy;
	}
}
