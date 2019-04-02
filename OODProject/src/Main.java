
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Rectangle player = new Rectangle(8, 8);
		player.setFill(Color.BLUEVIOLET);
		player.setX(300);
		player.setY(250);
		
		Pane pane = new Pane();
		pane.getChildren().add(player);
		Scene scene = new Scene(pane, 600, 500);
		scene.setFill(Color.LIGHTGREEN);
		
		primaryStage.setTitle("ZombiLand");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
