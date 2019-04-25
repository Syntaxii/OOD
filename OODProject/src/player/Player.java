package player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
	private volatile static ImageView playerpic;
	static final String imgURL = "file:src/images/player.gif";
	private static Image playerImage = new Image(imgURL);
	private static int Health = 100;
	private static Player player;
	private static boolean hpWarn = false;
	private static boolean playerAlive = true;

	private Player() {
		playerpic = new ImageView(playerImage);
	}
	public static Player getInstance() {
		if(player != null) {
			System.out.println("Testing 2nd instantiation denied");
		}
		if (player == null) {
			synchronized (Player.class) {
				if (player == null) {
					player = new Player();
				}
			}
		}
		return player;
	}

	public ImageView getPic() {
		return playerpic;
	}

	public int getHealth() {
		return Health;
	}

	public void setHealth(int hp) {
		Health = hp;
		if (hp < 40) {
			hpWarn = true;
			if (hp <= 0) {
				hpWarn = false;
				setDead();
			}

		}
		else hpWarn = false;
	}

	public boolean checkHPWarn() {
		return hpWarn;
	}

	public boolean isAlive() {
		return playerAlive;
	}

	public void setDead() {
		playerAlive = false;
	}
	public void setAlive() {
		playerAlive = true;
	}
}