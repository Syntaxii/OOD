package game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import powerups.PowerupType;

public class UI {
	private static UI theUI = null;
	private ArrayList<Node> UIParts;
	private Rectangle weaponsUI, weapon1, weapon1CDbox, weapon2, weapon2CDbox, weapon3, weapon3CDbox,
	HealthBar, hurtScreen, HealthBarBG, debugBox, pauseScreen, scoreBox, timeBox, leaderboardsBox;
	private Label weapon1ammo, weapon2ammo, weapon3ammo, HealthWarning, debuginfo1, debuginfo2, debuginfo3, debuginfo4,
	debugLabel, pauseScreenText, instruction, score, time, surviveText, leaderboards;
	private String weapon1URL, weapon2URL, weapon3URL;
	private ImageView weapon1Image, weapon2Image, weapon3Image;
	private String maxDamageIconURL, RegenerationIconURL;
	private ImageView maxDamageIcon, RegenerationIcon;
	private int current; //current weapon selection
	private double spaceDifference = 133.33;
	private boolean debugMode;
	private double x, y; //coordinates that UI is based off of
	private int totalKilled = 0, timeAlive = 0;
	private Label initials;
	private Label leaderboardsTitle;
	private String initial1 = "_", initial2 = "_", initial3 = "_";
	private int leaderboardStart = 1;
	private ArrayList<String[]> leaderboardsStrings; 
	private String connectionURL;

	private UI(){
		declareUI();
		try {
			FileReader fr = new FileReader("SQLLOGIN");
			BufferedReader br = new BufferedReader(fr);
			connectionURL = br.readLine();
			br.close();
		}
		catch (Exception e){
		}
		
	}

	public ArrayList<Node> getUIElements(){
		return UIParts;
	}

	public void changeWeaponFocus(int i) {

		switch (current){
		case 1: 
			changeColorToNormal(weapon1);
			break;
		case 2:
			changeColorToNormal(weapon2);
			break;
		case 3:
			changeColorToNormal(weapon3);
			break;
		}

		current = i;

		switch (i){
		case 1: 
			changeColorToFocus(weapon1);
			break;
		case 2:
			changeColorToFocus(weapon2);
			break;
		case 3:
			changeColorToFocus(weapon3);
			break;
		}
	}

	public void changeUIPositions(double newx, double newy) {
		x = newx;
		y = newy;
		scoreBox.relocate(x-100, y-930);
		score.relocate(x-95, y-930);
		timeBox.relocate(x+300, y-930);
		time.relocate(x+305, y-930);
		maxDamageIcon.relocate(x+91, y-143);
		RegenerationIcon.relocate(x+12, y-360);
		weaponsUI.relocate(x, y);
		weapon1.relocate(x, y);
		weapon1CDbox.relocate(x, y);
		weapon1Image.relocate(x-30, y-50);
		weapon1ammo.relocate(x+78, y+40);
		weapon2.relocate(x+spaceDifference, y);
		weapon2CDbox.relocate(x+spaceDifference, y);
		weapon2Image.relocate(x+2, y+5);
		weapon2ammo.relocate(x+216, y+57);
		weapon3.relocate(x+spaceDifference*2, y);
		weapon3CDbox.relocate(x+spaceDifference*2, y);
		weapon3Image.relocate(x-1227, y-517);
		weapon3ammo.relocate(x+349, y+57);
		HealthBar.relocate(x, y-60);
		HealthWarning.relocate(x+179, y-63);
		HealthBarBG.relocate(x, y-60);
		pauseScreen.relocate(x-50, y-850);
		pauseScreenText.relocate(x+80, y-845);
		instruction.relocate(x+50, y-750);
		surviveText.relocate(x+68, y-455);
		leaderboardsBox.relocate(x-50,  y-700);
		leaderboardsTitle.relocate(x+6, y-675);
		leaderboards.relocate(x+30, y-580);
		initials.relocate(x+60, y-370);
	}

	private void changeColorToNormal(Rectangle rec) {
		rec.setStroke(Color.rgb(200, 200, 200, 0.5));
		rec.setFill(Color.rgb(200, 200, 200, 0.5));
		rec.setStrokeWidth(2);
	}

	private void changeColorToFocus(Rectangle rec) {
		rec.setStroke(Color.rgb(25, 240, 240, 0.7));
		rec.setFill(Color.rgb(25, 240, 240, 0.7));
		rec.setStrokeWidth(3);
	}

	public static synchronized UI getUI() {
		if (theUI == null) {
			theUI = new UI();
		}
		return theUI;
	}

	private void declareUI() {
		UIParts = new ArrayList<Node>();

		weapon1URL = this.getClass().getResource("/images/images/gun.jpg").toString();
		weapon1Image = new ImageView(weapon1URL);

		weapon2URL = this.getClass().getResource("/images/images/shotgun.png").toString();
		weapon2Image = new ImageView(weapon2URL);

		weapon3URL = this.getClass().getResource("/images/images/rifle.png").toString();
		weapon3Image = new ImageView(weapon3URL);

		maxDamageIconURL = this.getClass().getResource("/images/images/maxDamageIcon.png").toString();
		maxDamageIcon = new ImageView(maxDamageIconURL);
		maxDamageIcon.setVisible(false);

		RegenerationIconURL = this.getClass().getResource("/images/images/RegenerationIcon.png").toString();
		RegenerationIcon = new ImageView(RegenerationIconURL);
		RegenerationIcon.setVisible(false);
		RegenerationIcon.setScaleX(.15);
		RegenerationIcon.setScaleY(.15);


		scoreBox = new Rectangle(200, 40);
		scoreBox.setX(800);
		scoreBox.setY(600);
		scoreBox.setFill(Color.rgb(50, 50, 200, 0.7));
		scoreBox.setStroke(Color.rgb(200, 200, 200, 0.8));
		scoreBox.setStrokeWidth(3);

		score = new Label("Score: 0");
		score.setTextFill(Color.YELLOW);
		score.setFont(new Font("Arial", 36));

		timeBox = new Rectangle(350, 40);
		timeBox.setX(800);
		timeBox.setY(600);
		timeBox.setFill(Color.rgb(50, 50, 200, 0.7));
		timeBox.setStroke(Color.rgb(200, 200, 200, 0.8));
		timeBox.setStrokeWidth(3);

		time = new Label("Seconds Lasted: 0");
		time.setTextFill(Color.YELLOW);
		time.setFont(new Font("Arial", 36));

		weaponsUI = new Rectangle(400, 100);
		weaponsUI.setX(175);
		weaponsUI.setY(600);
		weaponsUI.setFill(Color.rgb(50, 50, 200, 0.5));
		weaponsUI.setStroke(Color.rgb(200, 200, 200, 0.8));
		weaponsUI.setStrokeWidth(3);

		weapon1 = new Rectangle(133.33, 100);
		weapon1.setX(175);
		weapon1.setY(600);
		weapon1.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon1.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon1.setStrokeWidth(2);

		weapon1CDbox = new Rectangle(133.33, 100);
		weapon1CDbox.setX(175);
		weapon1CDbox.setY(600);
		weapon1CDbox.setFill(Color.rgb(50, 50, 200, 0.5));
		weapon1CDbox.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon1CDbox.setStrokeWidth(2);
		weapon1CDbox.setHeight(100);

		weapon1Image.setX(180);
		weapon1Image.setY(650);
		weapon1Image.setScaleX(-0.4);
		weapon1Image.setScaleY(.4);
		weapon1Image.setRotate(335);

		weapon1ammo = new Label("\u221e");
		weapon1ammo.setTextFill(Color.DARKRED);
		weapon1ammo.setFont(new Font("Arial", 72));

		weapon2 = new Rectangle(133.33, 100);
		weapon2.setX(175+spaceDifference);
		weapon2.setY(600);
		weapon2.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon2.setStrokeWidth(2);

		weapon2ammo = new Label("00");
		weapon2ammo.setTextFill(Color.DARKRED);
		weapon2ammo.setFont(new Font("Arial", 40));

		weapon2CDbox = new Rectangle(133.33, 100);
		weapon2CDbox.setX(175+spaceDifference);
		weapon2CDbox.setY(600);
		weapon2CDbox.setFill(Color.rgb(50, 50, 200, 0.5));
		weapon2CDbox.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon2CDbox.setStrokeWidth(2);
		weapon2CDbox.setHeight(100);

		weapon2Image.setScaleX(.34);
		weapon2Image.setScaleY(.34);
		weapon2Image.setRotate(335);

		weapon3 = new Rectangle(133.33, 100);
		weapon3.setX(175+spaceDifference*2);
		weapon3.setY(600);
		weapon3.setFill(Color.rgb(200, 200, 200, 0.5));
		weapon3.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon3.setStrokeWidth(2);

		weapon3ammo = new Label("00");
		weapon3ammo.setTextFill(Color.DARKRED);
		weapon3ammo.setFont(new Font("Arial", 40));

		weapon3CDbox = new Rectangle(133.33, 100);
		weapon3CDbox.setX(175+spaceDifference*2);
		weapon3CDbox.setY(600);
		weapon3CDbox.setFill(Color.rgb(50, 50, 200, 0.5));
		weapon3CDbox.setStroke(Color.rgb(200, 200, 200, 0.5));
		weapon3CDbox.setStrokeWidth(2);
		weapon3CDbox.setHeight(100);

		weapon3Image.setScaleX(.042);
		weapon3Image.setScaleY(.042);
		weapon3Image.setRotate(340);

		HealthBar = new Rectangle(400, 50);
		HealthBar.setX(175);
		HealthBar.setY(540);
		HealthBar.setFill(Color.rgb(250, 15, 15, 0.9));

		hurtScreen = new Rectangle(3000, 3000);
		hurtScreen.setFill(Color.rgb(230, 50, 50, 0.2));
		hurtScreen.setVisible(false);
		hurtScreen.setX(-400);
		hurtScreen.setY(-400);

		HealthWarning = new Label("!!!");
		HealthWarning.setFont(new Font("Arial", 50));
		HealthWarning.setTextFill(Color.DARKRED);
		HealthWarning.setVisible(false);

		HealthBarBG = new Rectangle(400, 50);
		HealthBarBG.setX(175);
		HealthBarBG.setY(540);
		HealthBarBG.setFill(Color.rgb(250, 15, 15, 0.2));
		HealthBarBG.setStroke(Color.rgb(250, 250, 250, 1));
		HealthBarBG.setStrokeWidth(3);

		//debug stuff
		debugLabel = new Label("   Debug Mode");
		debugLabel.setVisible(false);
		debugLabel.setFont(new Font("Arial", 20));
		debugLabel.relocate(30, 20);
		debugLabel.setTextFill(Color.YELLOW);
		debuginfo1 = new Label();
		debuginfo1.setVisible(false);
		debuginfo1.setFont(new Font("Arial", 20));
		debuginfo1.relocate(30, 50);
		debuginfo1.setTextFill(Color.YELLOW);
		debuginfo2 = new Label();
		debuginfo2.setVisible(false);
		debuginfo2.setFont(new Font("Arial", 20));
		debuginfo2.relocate(30, 80);
		debuginfo2.setTextFill(Color.YELLOW);
		debuginfo3 = new Label();
		debuginfo3.setVisible(false);
		debuginfo3.setFont(new Font("Arial", 20));
		debuginfo3.relocate(30, 110);
		debuginfo3.setTextFill(Color.YELLOW);
		debuginfo4 = new Label();
		debuginfo4.setVisible(false);
		debuginfo4.setFont(new Font("Arial", 20));
		debuginfo4.relocate(30, 140);
		debuginfo4.setTextFill(Color.YELLOW);
		debugBox = new Rectangle(175, 155);
		debugBox.setVisible(false);
		debugBox.setX(20);
		debugBox.setY(15);
		debugBox.setFill(Color.rgb(50, 50, 50, 0.8));
		debugBox.setStroke(Color.rgb(200, 200, 200, 0.8));
		debugBox.setStrokeWidth(4);

		//pause screen
		pauseScreen = new Rectangle(500, 750);
		pauseScreen.setX(600);
		pauseScreen.setY(300);
		pauseScreen.setFill(Color.rgb(80, 80, 80, 0.9));
		pauseScreen.setStroke(Color.rgb(200, 200, 200, 0.8));
		pauseScreen.setStrokeWidth(3);

		pauseScreenText = new Label("PAUSED");
		pauseScreenText.setFont(new Font("Arial", 60));
		pauseScreenText.setTextFill(Color.rgb(200, 200, 60));
		pauseScreenText.relocate(x, y);

		surviveText = new Label("SURVIVE");
		surviveText.setFont(new Font("Arial", 60));
		surviveText.setTextFill(Color.rgb(255, 0, 0, 1));

		instruction = new Label("                    Controls\n"
				+ 				"---------------------------------------------\n"
				+ 				"  WASD/Arrow Keys = Movement\n"
				+ 				"           P = Pause/Unpause\n"
				+ 				"            ESC = Close Game\n"
				+ 				"       Numpad 0 = Reset Game\n"
				+ "\n\n\n"
				+ 				"                  Instructions\n"
				+ 				"---------------------------------------------\n");
		instruction.setFont(new Font("Arial", 20));
		instruction.setTextFill(Color.rgb(200, 200, 60));
		instruction.relocate(x, y);

		//leaderboards
		leaderboards = new Label("  #\t\tInitials\t\tScore\n"
				+ "---------------------------------------------------\n"
				+ "  Loading........\n\n\n\n\n"
				+ "---------------------------------------------------\n"
				+"    \u2191 PG_UP    \u21BB TAB    \u2193 PG_DOWN    \n");
		leaderboards.setFont(new Font("Arial", 20));
		leaderboards.setTextFill(Color.rgb(200, 200, 60));
		leaderboards.relocate(x, y);

		leaderboardsTitle = new Label("Leaderboards");
		leaderboardsTitle.setFont(new Font("Arial", 64));
		leaderboardsTitle.setTextFill(Color.rgb(200, 200, 60));
		leaderboardsTitle.relocate(x, y);

		leaderboardsBox = new Rectangle(500, 550);
		leaderboardsBox.setX(600);
		leaderboardsBox.setY(300);
		leaderboardsBox.setFill(Color.rgb(80, 80, 80, 0.9));
		leaderboardsBox.setStroke(Color.rgb(200, 200, 200, 0.8));
		leaderboardsBox.setStrokeWidth(3);

		initials = new Label("  Enter Initials: "+initial1+" "+initial2+" "+initial3 + "\nPress Enter to Submit");
		initials.setFont(new Font("Arial", 26));
		initials.setTextFill(Color.rgb(200, 200, 60));
		initials.relocate(x, y);

		leaderboards.setVisible(false);
		leaderboardsBox.setVisible(false);
		leaderboardsTitle.setVisible(false);
		initials.setVisible(false);

		//add everything to UIParts
		UIParts.add(maxDamageIcon);
		UIParts.add(RegenerationIcon);

		UIParts.add(weaponsUI);
		UIParts.add(weapon1);
		UIParts.add(weapon1CDbox);
		UIParts.add(weapon1Image);
		UIParts.add(weapon1ammo);
		UIParts.add(weapon2);
		UIParts.add(weapon2CDbox);
		UIParts.add(weapon2Image);
		UIParts.add(weapon2ammo);
		UIParts.add(weapon3);
		UIParts.add(weapon3CDbox);
		UIParts.add(weapon3Image);
		UIParts.add(weapon3ammo);
		UIParts.add(HealthBar);
		UIParts.add(HealthWarning);
		UIParts.add(HealthBarBG);

		UIParts.add(debugBox);
		UIParts.add(debuginfo1);
		UIParts.add(debuginfo2);
		UIParts.add(debuginfo3);
		UIParts.add(debuginfo4);
		UIParts.add(debugLabel);

		UIParts.add(hurtScreen);

		UIParts.add(pauseScreen);
		UIParts.add(pauseScreenText);
		UIParts.add(instruction);
		UIParts.add(surviveText);

		UIParts.add(leaderboardsBox);
		UIParts.add(leaderboards);
		UIParts.add(leaderboardsTitle);
		UIParts.add(initials);

		UIParts.add(scoreBox);
		UIParts.add(score);

		UIParts.add(timeBox);
		UIParts.add(time);
	}

	public void ChangeHP(double d) {
		HealthBar.setWidth(d);
	}

	public void warnHP() {
		if (HealthWarning.isVisible() == true) HealthWarning.setVisible(false);
		else HealthWarning.setVisible(true);
	}
	
	public boolean checkwarnHP() {
		return HealthWarning.isVisible();
	}
	
	public void turnOffwarnHP() {
		HealthWarning.setVisible(false);
	}

	public void deadHP() {
		HealthWarning.setVisible(true);
		HealthWarning.setText("Dead!");
		HealthWarning.relocate(x+135, y-63);

		scoreBox.relocate(x, y-300);
		score.relocate(x+5, y-300);
		timeBox.relocate(x, y-250);
		time.relocate(x+5, y-250);
	}

	public void showLeaderboards() {

		leaderboards.setVisible(true);	
		leaderboardsBox.setVisible(true);
		leaderboardsTitle.setVisible(true);
		initials.setVisible(true);


		connectLeaderboards();
	}

	public void connectLeaderboards() {

		leaderboards.setText("  #\t\tInitials\t\tScore\n"
				+ "---------------------------------------------------\n"
				+ "  Loading........\n\n\n\n\n"
				+ "---------------------------------------------------\n"
				+"    \u2191 PG_UP    \u21BB TAB    \u2193 PG_DOWN    \n");

		
		try {
			Connection con=DriverManager.getConnection(connectionURL);
			Statement statement = con.createStatement();
			String sqlstatement = "SELECT initials, highscore FROM highscores ORDER BY highscore DESC";
			//			String sqlstatement = "SELECT TOP 5 initials, highscore\r\n" + 
			//					"FROM highscores\r\n" + 
			//					"WHERE highscore_id NOT IN\r\n" + 
			//					"	(SELECT TOP "+(leaderboardStart-1)+" highscore_id\r\n" + 
			//					"		FROM highscores\r\n" + 
			//					"		ORDER BY highscore DESC)\r\n" + 
			//					"ORDER BY highscore DESC";
			ResultSet resultSet = statement.executeQuery(sqlstatement);
			leaderboardsStrings = new ArrayList<String[]>();

			int index = 0;
			while (resultSet.next()) {
				leaderboardsStrings.add(new String[3]);
				leaderboardsStrings.get(index)[0] = ""+(index+1);
				leaderboardsStrings.get(index)[1] = resultSet.getString(1);
				leaderboardsStrings.get(index)[2] = resultSet.getString(2);
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		changeleaderboardView();
	}

	public void changeLeaderboardPosition(int k) {
		if (leaderboardStart + k < 1) leaderboardStart = 1;
		else if (k == 0) leaderboardStart = 1;
		else leaderboardStart += k;
		changeleaderboardView();
	}

	private void changeleaderboardView() {
		String results = "  #\t\tInitials\t\tScore\n"
				+ "------------------------------------------------------\n";
		int currentindex = leaderboardStart;
			while (currentindex < leaderboardStart+5) {
				if (currentindex > leaderboardsStrings.size()) {
					results += "   "+ currentindex + "\t\t...     \t\t.\n";
				}
				else {
					results += "   "+ leaderboardsStrings.get(currentindex-1)[0] + "\t\t" + leaderboardsStrings.get(currentindex-1)[1] + "     \t\t" + leaderboardsStrings.get(currentindex-1)[2] +"\n";
				}
				
				currentindex++;
			}
			
			

		results += "------------------------------------------------------\n"
				+"    \u2191 PG_UP    \u21BB TAB    \u2193 PG_DOWN    \n";

		leaderboards.setText(results);
	}

	public void hideLeaderboards() {
		leaderboards.setVisible(false);	
		leaderboardsBox.setVisible(false);
		leaderboardsTitle.setVisible(false);
		initials.setVisible(false);

		leaderboards.setText("  #\t\tInitials\t\tScore\n"
				+ "       \u2191 PG_UP                 \u2193 PG_DOWN    \n"
				+ "------------------------------------------------------\n"
				+ "  Loading........");
	}

	public int getCurrentWeaponSelection() {
		return current;
	}

	public void updateWeaponCD(int weapon, int timeLeft, int cooldown) {
		switch(weapon) {
		case 1:	weapon1CDbox.setHeight(100-(timeLeft%cooldown)*(100/cooldown)); break;
		case 2: weapon2CDbox.setHeight(100-(timeLeft%cooldown)*(100/cooldown)); break;
		case 3: weapon3CDbox.setHeight(100-(timeLeft%cooldown)*(100/cooldown)); break;
		default: break;
		}
	}

	public void pauseChange() {
		if (pauseScreen.isVisible()) {
			pauseScreen.setVisible(false);
			pauseScreenText.setVisible(false);
			instruction.setVisible(false);
			surviveText.setVisible(false);
		}
		else {
			pauseScreen.setVisible(true);
			pauseScreenText.setVisible(true);
			instruction.setVisible(true);
			surviveText.setVisible(true);
		}
	}

	public void setDebug() {
		if(debugMode == false) {
			debugMode = true;
			debugBox.setVisible(true);
			debuginfo1.setVisible(true);
			debuginfo2.setVisible(true);
			debuginfo3.setVisible(true);
			debuginfo4.setVisible(true);
			debugLabel.setVisible(true);
		}
		else {
			debugMode = false;
			debugBox.setVisible(false);
			debuginfo1.setVisible(false);
			debuginfo2.setVisible(false);
			debuginfo3.setVisible(false);
			debuginfo4.setVisible(false);
			debugLabel.setVisible(false);
		}
	}

	public boolean isDebug() {
		return debugMode;
	}

	public void showInfo(double playerX, double playerY, double MouseX, double MouseY) {
		debuginfo1.setText("player x = " + Math.floor(playerX));
		debuginfo2.setText("player y = " + Math.floor(playerY));
		debuginfo3.setText("mouse x = " + Math.floor(MouseX));
		debuginfo4.setText("mouse y = " + Math.floor(MouseY));
	}

	public void showHurtScreen(boolean i) {
		hurtScreen.setVisible(i);
	}

	public int getScore() {
		return totalKilled;
	}

	public void setScore(int i) {
		totalKilled = i;
		score.setText("Score: " + i);
	}

	public void updateTime(int T) {
		timeAlive = T;
		time.setText("Seconds Lasted: " + timeAlive);
	}

	public void setStatus(PowerupType type, boolean bool) {
		switch(type) {
		case MAXDAMAGE:
			if(bool == true) {
				maxDamageIcon.setVisible(true);
			}
			else maxDamageIcon.setVisible(false);
			break;
		case REGENERATION:
			if(bool == true) {
				RegenerationIcon.setVisible(true);
			}
			else RegenerationIcon.setVisible(false);
			break;
		default:
			break;
		}
	}
	public void setAmmo(PowerupType type, int value) {
		switch (type) {
		case AMMO2:
			if(value < 10)
				weapon2ammo.setText("0"+value);
			else weapon2ammo.setText(""+value);
			break;
		case AMMO3:
			if(value < 10)
				weapon3ammo.setText("0"+value);
			else weapon3ammo.setText(""+value);
			break;
		default:
			break;
		}
	}

	public void reset() {
		HealthWarning.setVisible(false);
		HealthWarning.setText("!!!");
		HealthWarning.relocate(x+179, y-63);
		HealthBar.setWidth(400);

		showHurtScreen(false);

		changeUIPositions(x, y);

		initial1 = "_";
		initial2 = "_";
		initial3 = "_";
		refreshInitials();

		timeAlive = 0;
		updateTime(timeAlive);

		setScore(0);
		maxDamageIcon.setVisible(false);
		RegenerationIcon.setVisible(false);
	}

	public void Flash(PowerupType type) {
		switch(type) {
		case MAXDAMAGE:
			if(maxDamageIcon.isVisible()) {
				maxDamageIcon.setVisible(false);
			}
			else maxDamageIcon.setVisible(true);
			break;
		case REGENERATION:
			if(RegenerationIcon.isVisible()) {
				RegenerationIcon.setVisible(false);
			}
			else RegenerationIcon.setVisible(true);
			break;
		default:
			break;

		}
	}

	public void refreshInitials() {
		initials.setText("  Enter Initials: "+initial1+" "+initial2+" "+initial3 + "\nPress Enter to Submit");
	}

	public void addInitial1(String string) {
		initial1 = string.toUpperCase();
		refreshInitials();
	}
	public void addInitial2(String string) {
		initial2 = string.toUpperCase();
		refreshInitials();
	}
	public void addInitial3(String string) {
		initial3 = string.toUpperCase();
		refreshInitials();
	}

	public void submitScore() {


		try {
			Connection con=DriverManager.getConnection(connectionURL);
			Statement statement = con.createStatement();
			String sqlstatement = "INSERT INTO Highscores (initials, highscore) VALUES ('" + initial1 + initial2 + initial3 +"', '" + getScore() + "');";
			statement.executeUpdate(sqlstatement);
			initials.setVisible(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}



}
