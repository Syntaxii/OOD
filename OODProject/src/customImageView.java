import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class customImageView extends ImageView{
	private volatile static ImageView object;
	static final String imgURL = "https://i.imgur.com/7Ul9t7I.gif";
	private static Image playerImage = new Image(imgURL);
	
	private customImageView() {
	
	}
	public static ImageView getInstance() {
		if(object != null) {
			System.out.println("Testing 2nd instantiation denied");
		}
		if (object == null) {
			synchronized (customImageView.class) {
				if (object == null) {
					object = new ImageView(playerImage);
				}
			}
		}
		return object;
	}
}
