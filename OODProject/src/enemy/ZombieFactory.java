package enemy;

public class ZombieFactory {
	public Enemy createEnemy(EnemyType type) {
		Enemy enemy = null;
        switch (type) {
            case BASIC:
                enemy = new BasicZombie();
                break;
            case FAST:
                enemy = new FastZombie();
                break;
            case LETHAL:
                enemy = new LethalZombie();
                break;
        }
        return enemy;
	}
}
