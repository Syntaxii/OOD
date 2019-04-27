package game;

import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Grass extends Main implements Floor{
	@Override
	public BackgroundImage setFloor() throws IOException {
		String imgURL = this.getClass().getResource("/images/grass.jpg").toString();
		BackgroundImage myBI= new BackgroundImage(new Image(imgURL,512,512,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		return myBI;
	}
}
