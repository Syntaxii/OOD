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
				}
			}
		});
		stage.setScene(scene);
		stage.show();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;
				if (goUp) dy -= 3;
				if (goDown) dy += 3;
				if (goRight)  dx += 4;
				if (goLeft)  dx -= 4;
				moveBy(dx, dy);
			}
		};
		timer.start();
	}
	private void moveBy(int dx, int dy) {
		if (dx == 0 && dy == 0) return;
		double cx = player.getBoundsInLocal().getWidth()  / 2;
		double cy = player.getBoundsInLocal().getHeight() / 2;
		double x = cx + player.getLayoutX() + dx;
		double y = cy + player.getLayoutY() + dy;
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
}
