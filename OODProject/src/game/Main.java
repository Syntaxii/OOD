package game;

import player.*;
import projectile.*;
import enemy.*;

import java.util.ArrayList;
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
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application{
	//TODO change aspect ratio for width and height
	static final double width = 1920, height = 1080;
	//static final double width = 1650, height = 1050;
	static final double newWidth = 750, newHeight = 750;
	static final String imgURL = "file:src/images/player.gif";
	private Image playerImage;
	private Player player;
	private Pane root, floor, obstacles, projectiles;
	private ArrayList<ImageView> obstacleCollision;
	private Rectangle mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4;
	boolean goUp, goDown, goRight, goLeft;
	private ProjectileHandling pHandler;
	private ZombieFactory zf;
	private EnemyHandling eHandler;
	private Enemy bz;
	private double centerOffsetX, centerOffsetY, mouseX, mouseY, NewmouseX, NewmouseY;
	private double weaponX, weaponY, angle;
	private double cx, cy; //character coordinate x, character coordinate 
	private UI uiElements;
	private boolean started = false;
	private int frameCount = 0;
	private boolean isVulnerable = true;
	private int invulnerableTime = 0;
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
		eHandler = new EnemyHandling();


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


		root = new Pane();

		floor = new Pane();
		obstacles = new Pane();
		floor.getChildren().add(obstacles);
		projectiles = new Pane();



		uiElements = UI.getUI();
		uiElements.changeUIPositions((width/2)-200, height-110);

		//collisions
		Collision col = new Collision();
		obstacleCollision = col.getObstacles();

		root.getChildren().add(floor);
		root.getChildren().add(player.getPic()); //collision boxes WIP
		root.getChildren().addAll(uiElements.getUIElements());
		root.getChildren().addAll(mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4);

		floor.getChildren().add(projectiles);


		uiElements.changeWeaponFocus(1);

		moveTo(width/2, height/2);

		obstacles.getChildren().addAll(obstacleCollision);

		Scene scene = new Scene(root, width, height);
		scene.setCursor(Cursor.NONE);

		BackgroundImage myBI= new BackgroundImage(new Image("file:src/images/grass.jpg",512,512,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		root.setBackground(new Background(myBI));
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (player.isAlive()) {
					switch (event.getCode()) {
					case P:			
						if (started == true) {
							started = false;
							uiElements.pauseChange();
						}
						else {
							started = true;
							uiElements.pauseChange();
						}
						break;
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

				//TODO FOR TESTING; CLEAN LATER
				case J: 
					spawnZombie(EnemyType.BASIC);
					break;

				case K:
					spawnZombie(EnemyType.FAST);
					break;

				case L:
					spawnZombie(EnemyType.LETHAL);
					break;

				case DIGIT0:
					System.out.println("enemy bounds: " + eHandler.getEnemies().get(1).getEnemy().getBoundsInParent());
					break;



				case M: uiElements.setDebug(); break;

				case BACK_SPACE: Reset(); break;

				case MINUS: 	stage.setFullScreen(true); 
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
				if (player.isAlive() && started==true) {
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
				if (player.isAlive() && started==true) {
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

				if (player.isAlive() && started==true) {

					//TODO change this to all weapons, and check ammo, etc.
					if(uiElements.getCurrentWeaponSelection() == 1) {
						if (weapon1CDRemaining == 0) {
							setMouseCursorGap(8);
							setMouseColor(Color.SALMON);
							cx = player.getPic().getLayoutX()+centerOffsetX;
							cy = player.getPic().getLayoutY()+centerOffsetY;

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
		stage.getIcons().add(new Image("file:src/images/Briefcase.png")); //we mean business :^)
		stage.show();
		stage.setFullScreenExitHint("");
		stage.setFullScreen(true);

		//		
		//
		//		
		//		
		//		
		//		Game Loop Section Begins
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (started==true) {
					if (player.isAlive()) {
						handleMovement();
						rotatePlayer();
						checkCollision();
					}

					//handle projectiles and enemies
					pHandler.cycleProjectiles();
					eHandler.cycleEnemies(cx, cy); //passes player coordinates as arguments

					if(frameCount%120==0) tryZombieSpawn(); //spawn zombie every 2 seconds
					if(frameCount>=1800 && frameCount%120==0) tryZombieSpawn(); //after 30 seconds, spawns 2 at a time
					if(frameCount>=3600 && frameCount%60==0) tryZombieSpawn(); //after 60 seconds, spawns 4 at a time

					if(!player.isAlive()) {
						uiElements.deadHP(); //if no health, tells the player he is dead
					}
					else if (player.checkHPWarn() && frameCount%10==0) {
						uiElements.warnHP(); ///if health is low, flash a warning for the player
					}

					if(player.isAlive() && frameCount%60==0) uiElements.updateTime(frameCount/60);


					if(invulnerableTime - frameCount <=45) {
						uiElements.showHurtScreen(false);
						if(invulnerableTime<frameCount) {
							isVulnerable = true;
						}
					}
					else {
						uiElements.showHurtScreen(true);
					}

					if(isVulnerable) player.setInvulnerable(false);
					else player.setInvulnerable(true);

					weapon1CDRemaining = weapon1CD - frameCount;
					if (weapon1CDRemaining <0) weapon1CDRemaining = 0;
					uiElements.updateWeaponCD(1, weapon1CDRemaining);

					if (eHandler.getScore() > uiElements.getScore()) uiElements.setScore(eHandler.getScore());

					frameCount++;
				}

				if(uiElements.isDebug()) {
					uiElements.showInfo(cx, cy, mouseX, mouseY);
				}
				try { //very janky way of setting a framerate limit
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		timer.start();
		//		
		//
		//		
		//		
		//		
		//		Game Loop Section Ends
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//		
		//
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
		double pw = player.getPic().getBoundsInParent().getWidth();
		double ph = player.getPic().getBoundsInParent().getHeight();
		double pminx = player.getPic().getBoundsInParent().getMinX()+(pw*2/5);
		double pminy = player.getPic().getBoundsInParent().getMinY()+(ph*2/5);
		pw = pw*1/5;
		ph = ph*1/5;
		Rectangle pl = new Rectangle(pminx, pminy, pw, ph);

		for(ImageView i : obstacleCollision) {
			if (pl.intersects(i.getBoundsInParent())){
				if(isVulnerable==true) {
					player.setHealth(player.getHealth()-10);
					uiElements.ChangeHP(player.getHealth()*4);
					invulnerableTime = frameCount + 60;
					isVulnerable=false;
				}
			}
		} 

		for (Enemy e : eHandler.getEnemies()) {
			for (Projectile p : pHandler.getProjectiles()) {
				if (p.getProjectile().getBoundsInParent().intersects(e.getEnemy().getBoundsInParent().getMinX()+(e.getEnemy().getBoundsInParent().getWidth()*1/4)
					,e.getEnemy().getBoundsInParent().getMinY()+(e.getEnemy().getBoundsInParent().getHeight()*1/4)
					,e.getEnemy().getBoundsInParent().getWidth()*1/2
					,e.getEnemy().getBoundsInParent().getHeight()*1/2)) {
					if (!e.isInVulnerable()) {
					e.receiveDamage(p.getDamage());
					}
				}
			}
			if(pl.intersects(e.getEnemy().getBoundsInParent().getMinX()+(e.getEnemy().getBoundsInParent().getWidth()*1/8)
					,e.getEnemy().getBoundsInParent().getMinY()+(e.getEnemy().getBoundsInParent().getHeight()*1/8)
					,e.getEnemy().getBoundsInParent().getWidth()*3/4
					,e.getEnemy().getBoundsInParent().getHeight()*3/4)) {

				//if(pl.intersects(e.getEnemy().getBoundsInParent())) {
				if(isVulnerable==true) {
					player.setHealth(player.getHealth()-e.getDamage());
					uiElements.ChangeHP(player.getHealth()*4);
					invulnerableTime = frameCount + 60;
					isVulnerable=false;
				}
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
		pistolBullet pBullet = new pistolBullet(mouseX,mouseY,cx,cy);
		pBullet.setVelocity(Math.atan2(mouseY - cy2, mouseX - cx2) * 180 / Math.PI); //sets angle to be (mouse - characterPos)
		pHandler.addProjectile(pBullet);
		pane.getChildren().add(pBullet.getProjectile());

	}

	private void rotatePlayer() {
		cx = player.getPic().getLayoutX()+centerOffsetX;
		cy = player.getPic().getLayoutY()+centerOffsetY;
		angle = Math.atan2(mouseY - cy, mouseX - cx) * 180 / Math.PI;
		player.getPic().setRotate(angle);
		weaponX = cx + Math.cos(Math.toRadians(angle+40))*35;
		weaponY = cy + Math.sin(Math.toRadians(angle+40))*35;
	}

	//TODO clean both methods up a bit
	private void moveBy(double dx, double dy) {
		cx = player.getPic().getBoundsInLocal().getWidth() / 2;
		cy = player.getPic().getBoundsInLocal().getHeight() / 2;
		double x = cx + player.getPic().getLayoutX() + dx;
		double y = cy + player.getPic().getLayoutY() - dy;
		moveTo(x, y);
	}

	private void moveTo(double x, double y) {
		cx = player.getPic().getBoundsInLocal().getWidth() / 2;
		cy = player.getPic().getBoundsInLocal().getHeight() / 2;
		if (x - cx >= 0 && x + cx <= width &&
				y - cy >= 0 && y + cy <= height) {
			player.getPic().relocate(x - cx, y - cy);
		}

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
		isVulnerable = true;
		invulnerableTime = 0;
		moveTo(width/2, height/2);
		player.setHealth(100);
		player.setAlive();
		uiElements.resetHP();
		uiElements.setScore(0);
		pHandler.clearProjectiles();
		pHandler = new ProjectileHandling();
		eHandler.clearEnemies();
		eHandler = new EnemyHandling();
		frameCount=0;
		weapon1CD = frameCount;
	}

	private void tryZombieSpawn() {
		double  rngForZombieSpawn = Math.random();
		if (rngForZombieSpawn <= .6) spawnZombie(EnemyType.BASIC);
		else if (rngForZombieSpawn > .6 && rngForZombieSpawn <=.9)spawnZombie(EnemyType.FAST);
		else spawnZombie(EnemyType.LETHAL);

	}

	private void spawnZombie(EnemyType type) { //spawn offscreen
		double location = (Math.random());
		double rx, ry;
		if (location <.25) {//left
			rx = -400;
			ry = (Math.random()*(height+500))-250;
		}
		else if(location <.5) {//up
			rx = (Math.random()*(width+500))-250;
			ry = -400;
		} 
		else if(location <.75) {//right
			rx = width;
			ry = (Math.random()*(height+500))-250;
		}
		else {//down
			rx = (Math.random()*(width+500))-250;
			ry = height;
		}

		bz = ZombieFactory.createEnemy(type, rx, ry); //FACTORY 
		eHandler.addEnemy(bz);
		floor.getChildren().add(bz.getEnemy());
		bz.spawn();
	}
}
