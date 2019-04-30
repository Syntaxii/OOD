//TODO's
//	1. Add other weapons
//	2. Add other powerups
//	3. Add Highscore (maybe link it up to database)
//	4. 
//
//
//
//
//
//
//
//

package game;

import player.*;
import powerups.Powerup;
import powerups.PowerupFactory;
import powerups.PowerupHandling;
import powerups.PowerupType;
import projectile.*;
import enemy.*;

import java.io.IOException;
import java.net.URISyntaxException;
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
	static final double width = 1920, height = 1080;
	//static final double width = 1650, height = 1050;
	static final double newWidth = 750, newHeight = 750;
	static String path;
	static String imgURL;
	private Player player;
	private Pane root;
	private Pane floor, obstacles, projectiles;
	private ArrayList<ImageView> obstacleCollision;
	private Rectangle mouseCursor1, mouseCursor2, mouseCursor3, mouseCursor4;
	boolean goUp, goDown, goRight, goLeft;
	private ProjectileHandling pHandler;
	private EnemyHandling eHandler;
	private PowerupHandling powerupHandler;
	private Enemy bz;
	private double mouseX, mouseY, NewmouseX, NewmouseY;
	private double weaponX, weaponY, angle;
	private double cx, cy; //character coordinate x, character coordinate 
	private UI uiElements;
	private boolean started = false;
	private int frameCount = 0;
	private boolean isVulnerable = true;
	private int invulnerableTime = 0;
	private int weapon1CD = 15;//, weapon2CD = 0, weapon3CD = 0; //weapon cooldown (firerate)
	private int weapon1CDTime = 0; //, weapon2CDTime = 0, weapon3CDTime = 0; //current frameCount + weaponCD = weaponCDTime
	private int weapon1CDRemaining = 0;//, weapon2CDRemaining = 0, weapon3CDRemaining = 0; 
	private int d = 10; //pixel gap between mouseCursor elements
	private int enemyLimit = 16; // amount of enemies allowed on screen
	private boolean moving = false;
	private boolean leaderboardIsShowing = false;
	private int currentInitialsAmount = 0;
	private boolean submitted = false;

	public static void main(String[] args) throws IOException, URISyntaxException{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//handle bullets, etc.
		pHandler = new ProjectileHandling();
		eHandler = new EnemyHandling();
		powerupHandler = new PowerupHandling();

		mouseX = 0.0;
		mouseY = 0.0;

		player = Player.getInstance(); //Singleton instantiation of player
		player.getPic().setScaleX(.4);
		player.getPic().setScaleY(.4);

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

		uiElements.changeWeaponFocus(1); //Default

		moveTo(width/2, height/2);

		obstacles.getChildren().addAll(obstacleCollision);

		Scene scene = new Scene(root, width, height);
		scene.setCursor(Cursor.NONE);

		setBackGround(); //Facade floor

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
				else if(leaderboardIsShowing == true && submitted != true) {
					if (currentInitialsAmount<3) {
						if (event.getCode().isLetterKey()) {
							switch(currentInitialsAmount) {
							case 0: 
								uiElements.addInitial1(event.getText());
								currentInitialsAmount++;
								break;
							case 1:	
								uiElements.addInitial2(event.getText());
								currentInitialsAmount++;
								break;
							case 2:	
								uiElements.addInitial3(event.getText());
								currentInitialsAmount++;
								break;
							}

						}
					}
					else if (event.getCode() == KeyCode.ENTER) {
						uiElements.submitScore();
						submitted = true;
						uiElements.connectLeaderboards();
					}
					
					if (event.getCode() == KeyCode.BACK_SPACE) {
						uiElements.addInitial1("_");
						uiElements.addInitial2("_");
						uiElements.addInitial3("_");
						currentInitialsAmount = 0;
					}
				}
				switch (event.getCode()) {

				//Spawn Zombies
//				case NUMPAD1: 
//					spawnZombie(EnemyType.BASIC);
//					break;
//
//				case NUMPAD2:
//					spawnZombie(EnemyType.FAST);
//					break;
//
//				case NUMPAD3:
//					spawnZombie(EnemyType.LETHAL);
//					break;

					//Spawn Powerups
//				case NUMPAD4:
//					spawnPowerup(PowerupType.MAXDAMAGE, 0, 0);
//					break;
//				case NUMPAD5:
//					break;
//				case NUMPAD6:
//					break;

//				case NUMPAD7: uiElements.setDebug(); break;

				case NUMPAD0: Reset(); break;

				//				case MINUS: 	stage.setFullScreen(true); 
				//				uiElements.changeUIPositions((width/2)-200, height-110);
				//				stage.setFullScreenExitHint("");
				//				break;

				case ESCAPE: 	System.exit(0); break;

				//				case ENTER:                
				//					stage.setHeight(newHeight);
				//					stage.setWidth(newWidth);
				//					stage.setFullScreen(false);
				//					stage.setMaximized(false);
				//					moveTo(newWidth/2, newHeight/2);
				//					uiElements.changeUIPositions((newWidth/2)-200, newHeight-110);
				//					break;
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
					int wep = uiElements.getCurrentWeaponSelection();
					switch(wep) {
					case 1:
						if (weapon1CDRemaining == 0) {
							setMouseCursorGap(8);
							setMouseColor(Color.SALMON);
							cx = player.getPic().getLayoutX() + player.getPic().getBoundsInLocal().getWidth() / 2;
							cy = player.getPic().getLayoutY() + player.getPic().getBoundsInLocal().getHeight() / 2;

							createBullet(mouseX, mouseY, cx, cy, projectiles);
							weapon1CDTime = frameCount + weapon1CD;
						}
						break;
					}
				}
				moveMouse();

			}
		});

		stage.setScene(scene);
		stage.setTitle("ZombiLand");
		String imgURL2 = this.getClass().getResource("/images/images/Briefcase.png").toString();
		stage.getIcons().add(new Image(imgURL2)); //we mean business :^)
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
				if (started==true) { //if unpaused
					pHandler.cycleProjectiles();
					eHandler.cycleEnemies(cx, cy); //passes player coordinates as arguments
					powerupHandler.cyclePowerups();
					player.cycleStatuses();
					ZombieSpawn();
					PowerupSpawn();
					PlayerChecks();
					WeaponChecks();
					ScoreChecks();
					frameCount++;
				}
				DebugInfo();
				NextFrame();
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

	private void PowerupSpawn(){
		Enemy temp = eHandler.getLastKilled();
		if(temp != null) {
			double enemyX = temp.getEnemyX()+95;
			double enemyY = temp.getEnemyY()+85;

			//TODO implement other types of Powerups and fix this
			if (Math.random() <= 1/15) {
				//			double tempChance = Math.random();
				//			if (tempChance <= .33)
				spawnPowerup(PowerupType.MAXDAMAGE, enemyX, enemyY);
			}
		}
	}

	private void NextFrame() {
		try { //very janky way of setting a framerate limit
			Thread.sleep(1000/60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void DebugInfo() {
		if(uiElements.isDebug()) {
			uiElements.showInfo(cx, cy, mouseX, mouseY);
		}
	}

	private void ZombieSpawn() {
		if (eHandler.getAmount() <= enemyLimit) { //Before spawning a new enemy, make sure the amount on screen is not higher than the limit
			try {
				if(frameCount%120==0) tryZombieSpawn(); //spawn zombie every 2 seconds
				if(frameCount>=1800 && frameCount%120==0) tryZombieSpawn(); //after 30 seconds, spawns 2 at a time
				if(frameCount>=3600 && frameCount%60==0) tryZombieSpawn(); //after 60 seconds, spawns 4 at a time
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	private void PlayerChecks() {
		if (player.isAlive()) {
			handleMovement();
			rotatePlayer();
			checkCollision();
		}

		if(player.getFlashStatusMaxDamage() && frameCount % 15 ==0) uiElements.Flash(PowerupType.MAXDAMAGE);
		else if(!player.getStatusMaxDamage()) uiElements.setStatus(PowerupType.MAXDAMAGE, false);

		if(!player.isAlive()) {
			uiElements.deadHP(); //if no health, tells the player he is dead
			if (leaderboardIsShowing == false) {
				uiElements.showLeaderboards();
				leaderboardIsShowing = true;
			}
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
	}

	private void WeaponChecks() {
		weapon1CDRemaining = weapon1CDTime - frameCount;
		if (weapon1CDRemaining <0) weapon1CDRemaining = 0;
		uiElements.updateWeaponCD(1, weapon1CDRemaining);
	}

	private void ScoreChecks() {
		if (eHandler.getScore() > uiElements.getScore()) uiElements.setScore(eHandler.getScore());
	}

	private void setBackGround() throws IOException {
		FloorMaker floorMaker = new FloorMaker();
		root.setBackground(new Background(floorMaker.setGrass()));
	}
	private void handleMovement() { 
		double[] offsetAmount = {0, 0};
		double currentAngle = 0; //Proper diagonal movement
		moving = false;
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

	private void checkCollision() { //Check collision between player and obstacles, enemies and projectiles, enemies and player

		double pw = player.getPic().getBoundsInParent().getWidth();
		double ph = player.getPic().getBoundsInParent().getHeight();
		double pminx = player.getPic().getBoundsInParent().getMinX()+(pw*1/4);
		double pminy = player.getPic().getBoundsInParent().getMinY()+(ph*1/4);
		pw = pw*1/2;
		ph = ph*1/2;
		Rectangle pl = new Rectangle(pminx, pminy, pw, ph); //player hitbox; smaller than actual player hitbox, but used for collision detection

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
						if (player.getStatusMaxDamage()) {
							e.receiveDamage(100000);
						}
						else {
							e.receiveDamage(p.getDamage());
						}

					}


				}
			}
			if(pl.intersects(e.getEnemy().getBoundsInParent().getMinX()+(e.getEnemy().getBoundsInParent().getWidth()*1/8)
					,e.getEnemy().getBoundsInParent().getMinY()+(e.getEnemy().getBoundsInParent().getHeight()*1/8)
					,e.getEnemy().getBoundsInParent().getWidth()*3/4
					,e.getEnemy().getBoundsInParent().getHeight()*3/4)) {

				if(isVulnerable==true) {
					player.setHealth(player.getHealth()-e.getDamage());
					uiElements.ChangeHP(player.getHealth()*4);
					invulnerableTime = frameCount + 60;
					isVulnerable=false;
				}
			}
		}

		for (Powerup po : powerupHandler.getPowerups()) {
			if (po.getPup().getBoundsInParent().intersects(pl.getBoundsInParent())){
				player.setStatus(po.getType());
				uiElements.setStatus(po.getType(), true);
				po.delete();
				powerupHandler.removePowerup(po);
				break;
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

	private void createBullet(double mouseX, double mouseY, double cx2, double cy2, Pane pane) {
		getWeaponXandY();
		pistolBullet pBullet = new pistolBullet(mouseX,mouseY,weaponX,weaponY);
		pBullet.setVelocity(Math.atan2(mouseY - cy2, mouseX - cx2) * 180 / Math.PI); //sets angle to be (mouse - characterPos)
		pHandler.addProjectile(pBullet);
		pane.getChildren().add(pBullet.getProjectile());

	}

	private void rotatePlayer() {
		cx = player.getPic().getLayoutX() + player.getPic().getBoundsInLocal().getWidth() / 2;
		cy = player.getPic().getLayoutY() + player.getPic().getBoundsInLocal().getHeight() / 2;
		angle = Math.atan2(mouseY - cy, mouseX - cx) * 180 / Math.PI;
		player.getPic().setRotate(angle);
	}

	private void getWeaponXandY() { 
		weaponX = cx + Math.cos(Math.toRadians(angle+40))*35;
		weaponY = cy + Math.sin(Math.toRadians(angle+40))*35;
	}

	private void moveBy(double dx, double dy) {
		cx = player.getPic().getLayoutX() + player.getPic().getBoundsInLocal().getWidth() / 2;
		cy = player.getPic().getLayoutY() + player.getPic().getBoundsInLocal().getHeight() / 2;
		double x = cx + dx;
		double y = cy - dy;
		moveTo(x, y);
	}

	private void moveTo(double x, double y) {
		double tempx = player.getPic().getBoundsInLocal().getWidth() / 2;
		double tempy = player.getPic().getBoundsInLocal().getHeight() / 2;
		if (x - tempx >= 0 && x + tempx <= width &&
				y - tempy >= 0 && y + tempy <= height) {
			player.getPic().relocate(x - tempx, y - tempy);
		}

	}

	private double[] getXandY(double angle) { //for correct diagonal speed
		double radianAngle = Math.toRadians(angle);
		double newX = Math.cos(radianAngle);
		double newY = Math.sin(radianAngle);
		return new double[] {newX, newY};
	}

	private void moveMouse() {
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
		player.reset();
		uiElements.reset();
		pHandler.clearProjectiles();
		eHandler.clearEnemies();
		powerupHandler.clearPowerups();
		frameCount=0;
		weapon1CDTime = frameCount;
		leaderboardIsShowing = false;
		uiElements.hideLeaderboards();
		currentInitialsAmount = 0;
		submitted = false;
	}

	private void tryZombieSpawn() throws IOException {
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

		try {
			bz = ZombieFactory.createEnemy(type, rx, ry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //FACTORY 
		eHandler.addEnemy(bz);
		floor.getChildren().add(bz.getEnemy());
		bz.spawn();
	}

	private void spawnPowerup(PowerupType type, double xx, double yy) { //spawn within bounds
		double locationX;
		double locationY;
		if (xx == 0 && yy == 0) {
			locationX = (Math.random()*(width-400))+150;
			locationY = (Math.random()*(height-400))+130;
		}
		else {
			locationX = xx;
			locationY = yy;
		}
		try {
			Powerup pu = PowerupFactory.createPowerup(type, locationX, locationY);
			powerupHandler.addPowerup(pu);
			floor.getChildren().addAll(pu.getPupBackground());
			floor.getChildren().add(pu.getPup());
			pu.spawn();
		} catch (IOException e) {
			e.printStackTrace();
		} //FACTORY 

	}
}
