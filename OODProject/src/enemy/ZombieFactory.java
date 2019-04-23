package enemy;

public class ZombieFactory {
	public static Enemy createEnemy(EnemyType type) {
		Enemy enemy = null;
        switch (type) {
            case BASIC:
        		EnemyBehaviors move1 = new EnemyBehaviors(new Normal());
                enemy = new BasicZombie(null);
                enemy.setSpeed(move1.setMoveBehavior());
                break;
            case FAST:
        		EnemyBehaviors move2 = new EnemyBehaviors(new Fast());
                enemy = new FastZombie(null);
                enemy.setSpeed(move2.setMoveBehavior());
                break;
            case LETHAL:
        		EnemyBehaviors move3 = new EnemyBehaviors(new Slow());
                enemy = new LethalZombie(null);
                enemy.setSpeed(move3.setMoveBehavior());
                break;
        }
        return enemy;
	}
}
