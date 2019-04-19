import javafx.stage.*;
import java.util.ArrayList;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.event.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application{
	//TODO change aspect ratio for width and height
	static final double width = 1920, height = 1080;
	static final double newWidth = 750, newHeight = 750;
	static final String imgURL = "https://i.imgur.com/7Ul9t7I.gif";
	private Image playerImage;
	private Player player;
	private Rectangle playerCollision;
	private ArrayList<Rectangle> obstacleCollision;
	private Rectangle mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4;
	boolean goUp, goDown, goRight, goLeft;
	private ProjectileHandling pHandler;
	private double centerOffsetX, centerOffsetY, mouseX, mouseY, NewmouseX, NewmouseY;
	private double weaponX, weaponY, angle;
	private UI uiElements;
	private int frameCount = 0;
	private int weapon1CD = 0, weapon2CD = 0, weapon3CD = 0; //weapon cooldown (firerate)
	private int weapon1CDRemaining = 0, weapon2CDRemaining = 0, weapon3CDRemaining = 0;
	private int d = 10; //pixel gap between mouseCursor elements

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		//handle bullets, etc.
		pHandler = new ProjectileHandling();

		mouseX = 0.0;
		mouseY = 0.0;

		playerImage = new Image(imgURL);

		player = Player.getInstance(); //Singleton instantiation of player
		player.getPic().setScaleX(.4);
		player.getPic().setScaleY(.4);

		//center of player
		centerOffsetX = (playerImage.getWidth())/2;
		centerOffsetY = (playerImage.getHeight())/2;

		createMouseCursor();


		Pane root = new Pane();
		//BorderPane root = new BorderPane();
		Pane floor = new Pane(player.getPic());
		Pane projectiles = new Pane();



		uiElements = UI.getUI();
		uiElements.changeUIPositions((width/2)-200, height-110);

		//collisions
		Collision col = new Collision();
		playerCollision = col.getPlayercollision();
		obstacleCollision = col.getObstacles();


		root.getChildren().add(floor);
		floor.getChildren().addAll(obstacleCollision); //collision boxes WIP
		floor.getChildren().add(playerCollision);
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

				if (player.isAlive()) {
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
					default:
						break;
					}
				}
				switch (event.getCode()) {

				case BACK_SPACE: Reset(); break;

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
				if (player.isAlive()) {
					rotatePlayer();
				}

				mouseX = event.getX();
				mouseY = event.getY();
				moveMouse();
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				moveMouse();
				if (player.isAlive()) {
					rotatePlayer();
				}



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
				mouseX = e.getX();
				mouseY = e.getY();

				if (player.isAlive()) {

					//TODO change this to all weapons, and check ammo, etc.
					if(uiElements.getCurrentWeaponSelection() == 1) {
						if (weapon1CDRemaining == 0) {
							setMouseCursorGap(8);
							setMouseColor(Color.SALMON);
							double cx = player.getPic().getLayoutX()+centerOffsetX;
							double cy = player.getPic().getLayoutY()+centerOffsetY;
							System.out.println(mouseX + " MouseX\n " + mouseY + " MouseY\n " + cx + " cx\n " + cy + " cy\n");
							createBullet(mouseX, mouseY, weaponX, weaponY, cx, cy, projectiles);
							weapon1CD = frameCount + 15; //the "+15" is the cooldown
						}
					}
				}
				moveMouse();

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
				if (player.isAlive()) {
					handleMovement();
					rotatePlayer();
					checkCollision();
				}

				pHandler.cycleProjectiles();

				if(!player.isAlive()) {
					uiElements.deadHP();
				}
				else if (player.checkHPWarn() && frameCount%10==0) {
					uiElements.warnHP();
				}


				frameCount++;
				weapon1CDRemaining = weapon1CD - frameCount;
				if (weapon1CDRemaining <0) weapon1CDRemaining = 0;
				uiElements.updateWeaponCD(1, weapon1CDRemaining);

				try {
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		timer.start();
	}

	private void handleMovement() {
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
	}

	private void checkCollision() {
		for(int i = 0; i < obstacleCollision.size(); i++) {
			if(playerCollision.getLayoutX() > 150 && playerCollision.getLayoutX() < 250 && playerCollision.getLayoutY() > 200 && playerCollision.getLayoutY() < 300) {
				//				System.out.println("COLLISSION");
				player.setHealth(player.getHealth()-1);
				uiElements.ChangeHP(player.getHealth()*4);
				System.out.println(player.getHealth());
			} else {
				//				System.out.println("No Collission");
			}
		} 
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
		double playerPosx = player.getPic().getLayoutX()+centerOffsetX;
		double playerPosy = player.getPic().getLayoutY()+centerOffsetY;
		angle = Math.atan2(mouseY - playerPosy, mouseX - playerPosx) * 180 / Math.PI;
		player.getPic().setRotate(angle);
		weaponX = playerPosx + Math.cos(Math.toRadians(angle+40))*35;
		weaponY = playerPosy + Math.sin(Math.toRadians(angle+40))*35;
	}

	//TODO clean both methods up a bit
	private void moveBy(double dx, double dy) {
		double cx = player.getPic().getBoundsInLocal().getWidth() / 2;
		double cy = player.getPic().getBoundsInLocal().getHeight() / 2;
		double x = cx + player.getPic().getLayoutX() + dx;
		double y = cy + player.getPic().getLayoutY() - dy;
		moveTo(x, y);
	}

	private void moveTo(double x, double y) {
		double cx = player.getPic().getBoundsInLocal().getWidth() / 2;
		double cy = player.getPic().getBoundsInLocal().getHeight() / 2;
		if (x - cx >= 0 && x + cx <= width &&
				y - cy >= 0 && y + cy <= height) {
			player.getPic().relocate(x - cx, y - cy);
		}

		//Have player's collision box repeat movements
		double cx2 = playerCollision.getBoundsInLocal().getWidth() / 2;
		double cy2 = playerCollision.getBoundsInLocal().getHeight() / 2;
		if (x - cx2 >= 0 && x + cx2 <= width &&
				y - cy2 >= 0 && y + cy2 <= height) {
			playerCollision.relocate(x - cx2, y - cy2);
		}
		//	checkCollision();

	}

	private double[] getXandY(double angle) { //for correct diagonal speed
		double radianAngle = Math.toRadians(angle);
		double newX = Math.cos(radianAngle);
		double newY = Math.sin(radianAngle);
		return new double[] {newX, newY};
	}

	private void moveMouse() {
		//TODO decide if the offset is worth it. Right now, the mouse position changes to get where the gun is aiming.
		NewmouseX = mouseX + Math.cos(Math.toRadians(angle+40))*35;
		NewmouseY = mouseY + Math.sin(Math.toRadians(angle+40))*35;

		mouseCursor1.setX(NewmouseX+d-5);
		mouseCursor1.setY(NewmouseY+d-2);

		mouseCursor2.setX(NewmouseX+d-5);
		mouseCursor2.setY(NewmouseY-d-2);

		mouseCursor3.setX(NewmouseX-d-5);
		mouseCursor3.setY(NewmouseY-d-2);

		mouseCursor4.setX(NewmouseX-d-5);
		mouseCursor4.setY(NewmouseY+d-2);
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

	private void Reset() {
		moveTo(width/2, height/2);
		player.setHealth(100);
		player.setAlive();
		uiElements.resetHP();
		pHandler.clearProjectiles();
	}
}
