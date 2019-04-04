import javafx.stage.*;

import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.event.*;
import javafx.scene.image.*;

public class Main extends Application{
	static final double width = 750, height = 700;
	static final String imgURL = "https://i.imgur.com/7Ul9t7I.gif";
	private Image playerImage;
	private Node  player;
	private Rectangle mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4;
	boolean goUp, goDown, goRight, goLeft;
	private bulletHandling bHandler;
	private double centerOffsetX, centerOffsetY;
	private Circle shadow;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		bHandler = new bulletHandling();

		playerImage = new Image(imgURL);
		playerImage.getWidth();
		playerImage.getHeight();
		player = new ImageView(playerImage);
		player.setScaleX(.5);
		player.setScaleY(.5);
		centerOffsetX = (playerImage.getWidth())/2;
		centerOffsetY = (playerImage.getHeight())/2;
		
		
		//TODO condense
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
		
		BorderPane root = new BorderPane();
		
		Pane floor = new Pane(player);
		
		root.getChildren().add(floor);
		floor.getChildren().addAll(mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4);
		
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
				double MouseX = event.getX();
				double MouseY = event.getY();
				moveMouse(MouseX, MouseY);
				if(MouseX < player.getBoundsInParent().getMinX() + (player.getBoundsInParent().getMaxX()-player.getBoundsInParent().getMinX())/2) {
					player.setScaleX(Math.abs(player.getScaleX()) * -1);
				}
				else {
					player.setScaleX(Math.abs(player.getScaleX()));
				}
				
				System.out.println("PlayerX: " + player.getBoundsInParent().getMinX() + (player.getBoundsInParent().getMaxX()-player.getBoundsInParent().getMinX())/2 + " MouseX: " + event.getX());
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				double MouseX = e.getX();
				double MouseY = e.getY();
				moveMouse(MouseX, MouseY);
				if(MouseX < player.getBoundsInParent().getMinX() + (player.getBoundsInParent().getMaxX()-player.getBoundsInParent().getMinX())/2) {
					player.setScaleX(Math.abs(player.getScaleX()) * -1);
				}
				else {
					player.setScaleX(Math.abs(player.getScaleX()));
				}
			}
		});
		
		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				setMouseColor(Color.FLORALWHITE);
			}
		});
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				double MouseX = e.getX();
				double MouseY = e.getY();		
				setMouseColor(Color.SALMON);

		//		double cx = player.getBoundsInLocal().getWidth()  / 2;
				double cx = player.getLayoutX();

		//		double cy = player.getBoundsInLocal().getHeight() / 2;
				double cy = player.getLayoutY();
				
				System.out.println(MouseX + " MouseX\n " + MouseY + " MouseY\n " + cx + " cx\n " + cy + " cy\n");
				createBullet(MouseX, MouseY, cx+centerOffsetX, cy+centerOffsetY, floor);

			}
		});
		stage.setScene(scene);
		stage.setTitle("ZombiLand");
		stage.getIcons().add(new Image("https://cdn4.iconfinder.com/data/icons/pretty-office-part-2-simple-style/256/Briefcase.png"));
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
				
				bHandler.cycleProjectiles();
			
			}
		};
		timer.start();
		
		
	}
	
	private void createBullet(double mouseX, double mouseY, double cx, double cy, Pane floor) {
		Bullet pBullet = new Bullet(mouseX,mouseY,cx,cy);
		bHandler.addProjectile(pBullet);
		floor.getChildren().add(pBullet.getBullet());
		
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
	
	private void moveMouse(double x, double y) {
		int d = 10; //displacement. Easier to change with a single variable
		mouseCursor1.setX(x+d-5);
		mouseCursor1.setY(y+d-2);
		
		mouseCursor2.setX(x+d-5);
		mouseCursor2.setY(y-d-2);
		
		mouseCursor3.setX(x-d-5);
		mouseCursor3.setY(y-d-2);
		
		mouseCursor4.setX(x-d-5);
		mouseCursor4.setY(y+d-2);
	}
	
	private void setMouseColor(Paint color) {
		mouseCursor1.setFill(color);
		
		mouseCursor2.setFill(color);
		
		mouseCursor3.setFill(color);
		
		mouseCursor4.setFill(color);
	}
}
