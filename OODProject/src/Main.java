import javafx.stage.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.image.*;

public class Main extends Application{
	static final double width = 750, height = 700;
	static final String imgURL = "https://i.imgur.com/Jyc4TWx.png";
	private Image playerImage;
	private Node  player;
	boolean goUp, goDown, goRight, goLeft;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		playerImage = new Image(imgURL);
		player = new ImageView(playerImage);
		player.setScaleX(1.4);
		player.setScaleY(1.4);
		BorderPane root = new BorderPane();
		Pane floor = new Pane(player);
		root.getChildren().add(floor);
		moveTo(width/2, height/2);
		Scene scene = new Scene(root, width, height);
		BackgroundImage myBI= new BackgroundImage(new Image("http://www.dundjinni.com/forums/uploads/Eanwulf/237_Sample.jpg",750,700,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		root.setBackground(new Background(myBI));
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case W:    goUp = true; break;
				case S:  goDown = true; break;
				case A:  goLeft  = true; break;
				case D: goRight  = true; break;
				case UP:    goUp = true; break;
				case DOWN:  goDown = true; break;
				case LEFT:  goLeft = true; break;
				case RIGHT: goRight = true; break;
				default:
					break;
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case W:    goUp = false; break;
				case S:  goDown = false; break;
				case A:  goLeft  = false; break;
				case D: goRight  = false; break;
				case UP:    goUp = false; break;
				case DOWN:  goDown = false; break;
				case LEFT:  goLeft = false; break;
				case RIGHT: goRight = false; break;
				default:
					break;
				}
			}
		});
		stage.setScene(scene);
		stage.show();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				double[] offsetAmount = {0, 0};
				double currentAngle = 0; //Proper diagonal movement
				boolean moving = false;
				if (goUp) {
					currentAngle = 90;
					moving = true;
					if (goLeft) {
						currentAngle = 135;
					}
					else if (goRight) {
						currentAngle = 45;
					}
				}
				else if (goDown) {
					currentAngle = 270;
					moving = true;
					if (goLeft) {
						currentAngle = 225;
					}
					else if (goRight) {
						currentAngle = 315;
					}
				}
				else if (goRight) {
					moving = true;
					currentAngle = 0;
				}
				else if (goLeft) {
					moving = true;
					currentAngle = 180;
				}
				if (moving == true) { 
					offsetAmount = getXandY(currentAngle);
					moveBy(offsetAmount[0]*3, offsetAmount[1]*3);
				}
			}
		};
		timer.start();
	}
	private void moveBy(double dx, double dy) {
		if (dx == 0 && dy == 0) return; //redundant
		double cx = player.getBoundsInLocal().getWidth()  / 2;
		double cy = player.getBoundsInLocal().getHeight() / 2;
		double x = cx + player.getLayoutX() + dx;
		double y = cy + player.getLayoutY() - dy;
		moveTo(x, y);
	}
	private void moveTo(double x, double y) {
		double cx = player.getBoundsInLocal().getWidth()  / 2;
		double cy = player.getBoundsInLocal().getHeight() / 2;
		if (x - cx >= 0 && x + cx <= width &&
				y - cy >= 0 && y + cy <= height) {
			player.relocate(x - cx, y - cy);
		}
	}

	private double[] getXandY(double angle) { //for correct diagonal speed
		double radianAngle = Math.toRadians(angle);
		double newX = Math.cos(radianAngle);
		double newY = Math.sin(radianAngle);
		return new double[] {newX, newY};
	}
}
