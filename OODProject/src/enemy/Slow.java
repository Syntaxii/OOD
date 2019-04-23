package enemy;

public class Slow implements MoveBehavior{
	@Override
	public double setSpeed() {
		return 0.75;
	}
}
