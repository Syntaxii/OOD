import javafx.stage.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.event.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application{
	//TODO change aspect ratio for width and height
	static final double width = 1920, height = 1080;
	static final double newWidth = 750, newHeight = 750;
	static final String imgURL = "https://i.imgur.com/7Ul9t7I.gif";
	private Image playerImage;
	private Node player, player2; //testing use

	private Rectangle mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4;
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
		
		createMouseCursor();
		
		Pane root = new Pane();
		//BorderPane root = new BorderPane();
		Pane floor = new Pane(player);
		Pane projectiles = new Pane();
		
		UI uiElements = UI.getUI();
		uiElements.changeUIPositions((width/2)-200, height-110);
		
		root.getChildren().add(floor);
		floor.getChildren().add(projectiles);
		floor.getChildren().addAll(mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4);
		floor.getChildren().addAll(uiElements.getUIElements());
		
		uiElements.changeWeaponFocus(1);

		moveTo(width/2, height/2);

		Scene scene = new Scene(root, width, height);
		scene.setCursor(Cursor.NONE);

		BackgroundImage myBI= new BackgroundImage(new Image("https://i.imgur.com/g4B0JMe.jpg",512,512,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		root.setBackground(new Background(myBI));
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case W:    		goUp = true; break;
					case S:  		goDown = true; break;
					case A:  		goLeft  = true; break;
					case D: 		goRight  = true; break;
					case UP:    	goUp = true; break;
					case DOWN:  	goDown = true; break;
					case LEFT:  	goLeft = true; break;
					case RIGHT: 	goRight = true; break;
					case DIGIT1: 	uiElements.changeWeaponFocus(1); break;
					case DIGIT2: 	uiElements.changeWeaponFocus(2); break;
					case DIGIT3: 	uiElements.changeWeaponFocus(3); break;
					
					case SHIFT: 	stage.setFullScreen(true); 
									uiElements.changeUIPositions((width/2)-200, height-110);
									stage.setFullScreenExitHint("");
									break;
					
					case ESCAPE: 	System.exit(0); break;
					
					//TODO fix boundaries and UI position for windowed mode; get rid of maximization when going windowed after first key press
					case ENTER:                
									stage.setHeight(newHeight);
									stage.setWidth(newWidth);
									stage.setFullScreen(false);
									stage.setMaximized(false);
									moveTo(newWidth/2, newHeight/2);
									uiElements.changeUIPositions((newWidth/2)-200, newHeight-110);
									break;
					default:
									break;
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case W:    		goUp = false; break;
					case S:  		goDown = false; break;
					case A:  		goLeft  = false; break;
					case D: 		goRight  = false; break;
					case UP:    	goUp = false; break;
					case DOWN:  	goDown = false; break;
					case LEFT: 	 	goLeft = false; break;
					case RIGHT:	 	goRight = false; break;
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
				createBullet(MouseX, MouseY, weaponX, weaponY, cx, cy, projectiles);

			}
		});
		
		stage.setScene(scene);
		stage.setTitle("ZombiLand");
		stage.getIcons().add(new Image("https://cdn4.iconfinder.com/data/icons/pretty-office-part-2-simple-style/256/Briefcase.png")); //we mean business :^)
		stage.show();
		stage.setFullScreenExitHint("");
		stage.setFullScreen(true);
		
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

	private void createBullet(double mouseX, double mouseY, double cx, double cy, double cx2, double cy2, Pane pane) {
		Bullet pBullet = new Bullet(mouseX,mouseY,cx,cy);
		pBullet.setVelocity(Math.atan2(mouseY - cy2, mouseX - cx2) * 180 / Math.PI); //sets angle to be (mouse - characterPos)
		pHandler.addProjectile(pBullet);
		pane.getChildren().add(pBullet.getBullet());

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
		double cx = player.getBoundsInLocal().getWidth() / 2;
		double cy = player.getBoundsInLocal().getHeight() / 2;
		double x = cx + player.getLayoutX() + dx;
		double y = cy + player.getLayoutY() - dy;
		moveTo(x, y);
	}
	
	private void moveTo(double x, double y) {
		double cx = player.getBoundsInLocal().getWidth() / 2;
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
