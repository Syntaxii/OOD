import javafx.stage.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.event.*;
import javafx.scene.image.*;

public class Main extends Application{
	static final double width = 750, height = 700;
	static final String imgURL = "https://i.imgur.com/7Ul9t7I.gif";
	private Image playerImage;
	private Node  player, player2;//testing use
	private Rectangle mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4, UI;
	boolean goUp, goDown, goRight, goLeft;
	private ProjectileHandling pHandler;
	private double centerOffsetX, centerOffsetY, mouseX, mouseY;
	private double weaponX, weaponY;
	private int d = 10; //pixel gap between mouseCursor elements
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		pHandler = new ProjectileHandling();
		mouseX = 0.0;
		mouseY = 0.0;
		playerImage = new Image(imgURL);
		
		player = customImageView.getInstance(); //Singleton instantiation of player
		player2 = customImageView.getInstance(); //Test 2nd instantiation
		player.setScaleX(.4);
		player.setScaleY(.4);
		
		centerOffsetX = (playerImage.getWidth())/2;
		centerOffsetY = (playerImage.getHeight())/2;
		
		UI = new Rectangle(400, 100);
		UI.setX(175);
		UI.setY(600);
		UI.setFill(Color.BLUE);

		createMouseCursor();
		

		BorderPane root = new BorderPane();

		Pane floor = new Pane(player);

		root.getChildren().add(floor);
		floor.getChildren().addAll(mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4, UI);

		moveTo(width/2, height/2);

		Scene scene = new Scene(root, width, height);
		scene.setCursor(Cursor.NONE);

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
		scene.setOnMouseMoved(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent event) {
				mouseX = event.getX();
				mouseY = event.getY();
				moveMouse();
				rotatePlayer();

		//		System.out.println("PlayerX: " + player.getBoundsInParent().getMinX() + (player.getBoundsInParent().getMaxX()-player.getBoundsInParent().getMinX())/2 + " MouseX: " + event.getX());
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				setMouseCursorGap(8);
				moveMouse();
				rotatePlayer();
			}
		});

		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setMouseColor(Color.FLORALWHITE);
				setMouseCursorGap(10);
				moveMouse();
			}
		});

		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				double MouseX = e.getX();
				double MouseY = e.getY();		
				setMouseColor(Color.SALMON);
				setMouseCursorGap(8);
				moveMouse();

				double cx = player.getLayoutX()+centerOffsetX;
				double cy = player.getLayoutY()+centerOffsetY;

				System.out.println(MouseX + " MouseX\n " + MouseY + " MouseY\n " + cx + " cx\n " + cy + " cy\n");
		//		createBullet(MouseX, MouseY, cx, cy, floor);
				createBullet(MouseX, MouseY, weaponX, weaponY, cx, cy, floor);

			}
		});
		stage.setScene(scene);
		stage.setTitle("ZombiLand");
		stage.getIcons().add(new Image("https://cdn4.iconfinder.com/data/icons/pretty-office-part-2-simple-style/256/Briefcase.png")); //we mean business :^)
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
					moveBy(offsetAmount[0]*5, offsetAmount[1]*5);
				}

				pHandler.cycleProjectiles();
				rotatePlayer();

			}
		};
		timer.start();


	}

	private void createMouseCursor() {
		mouseCursor1 = new Rectangle(10, 5);
		mouseCursor1.setRotate(45);
		mouseCursor1.setFill(Color.FLORALWHITE);
		mouseCursor1.setStroke(Color.BLACK);

		mouseCursor2 = new Rectangle(10, 5);
		mouseCursor2.setRotate(135);
		mouseCursor2.setFill(Color.FLORALWHITE);
		mouseCursor2.setStroke(Color.BLACK);

		mouseCursor3 = new Rectangle(10, 5);
		mouseCursor3.setRotate(225);
		mouseCursor3.setFill(Color.FLORALWHITE);
		mouseCursor3.setStroke(Color.BLACK);

		mouseCursor4 = new Rectangle(10, 5);
		mouseCursor4.setRotate(315);
		mouseCursor4.setFill(Color.FLORALWHITE);
		mouseCursor4.setStroke(Color.BLACK);
		
	}

	private void createBullet(double mouseX, double mouseY, double cx, double cy, double cx2, double cy2, Pane thefloor) {
		Bullet pBullet = new Bullet(mouseX,mouseY,cx,cy);
		pBullet.setVelocity(Math.atan2(mouseY - cy2, mouseX - cx2) * 180 / Math.PI); //sets angle to be (mouse - characterPos)
		pHandler.addProjectile(pBullet);
		thefloor.getChildren().add(pBullet.getBullet());

	}

	private void rotatePlayer() {
		double playerPosx = player.getLayoutX()+centerOffsetX;
		double playerPosy = player.getLayoutY()+centerOffsetY;
		double angle = Math.atan2(mouseY - playerPosy, mouseX - playerPosx) * 180 / Math.PI;
		player.setRotate(angle);
		weaponX = playerPosx + Math.cos(Math.toRadians(angle+40))*35;
		weaponY = playerPosy + Math.sin(Math.toRadians(angle+40))*35;
	}

	//TODO clean both methods up a bit
	private void moveBy(double dx, double dy) {
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

	private void moveMouse() {
		mouseCursor1.setX(mouseX+d-5);
		mouseCursor1.setY(mouseY+d-2);

		mouseCursor2.setX(mouseX+d-5);
		mouseCursor2.setY(mouseY-d-2);

		mouseCursor3.setX(mouseX-d-5);
		mouseCursor3.setY(mouseY-d-2);

		mouseCursor4.setX(mouseX-d-5);
		mouseCursor4.setY(mouseY+d-2);
	}
	
	private void setMouseCursorGap(int gap) {
		d = gap;
	}

	private void setMouseColor(Paint color) {
		mouseCursor1.setFill(color);

		mouseCursor2.setFill(color);

		mouseCursor3.setFill(color);

		mouseCursor4.setFill(color);
	}
}
