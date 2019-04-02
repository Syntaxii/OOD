import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ZombiLand");
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 300, 250, Color.GREEN);
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
