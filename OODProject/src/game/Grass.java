package game;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Grass extends Main implements Floor{
	@Override
	public BackgroundImage setFloor() {
		BackgroundImage myBI= new BackgroundImage(new Image("file:src/images/grass.jpg",512,512,false,true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		return myBI;
	}
}
