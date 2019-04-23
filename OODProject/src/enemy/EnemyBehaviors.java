package enemy;

public  class EnemyBehaviors {
	MoveBehavior move;
	
	public EnemyBehaviors(MoveBehavior move) {
		this.move = move;
	}
	public double setMoveBehavior() {
		return move.setSpeed();
	}
	
}
