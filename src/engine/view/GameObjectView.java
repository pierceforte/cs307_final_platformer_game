package engine.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//TODO: Decide whether to use inheritance or composition for ImageView
public class GameObjectView extends ImageView {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    //TODO: change params to Coords and Dimensions objects OR just pass the GameObject (probably don't want this dependency though)
    public GameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        updateImage(imgPath);
        updatePos(xPos, yPos);
        int xOrientation = xDirection < 0 ? LEFT : RIGHT;
        updateDimensions(xOrientation * width, height);
    }

    //TODO: change param to Coords object
    public void updatePos(double xPos, double yPos) {
        setX(xPos);
        setY(yPos);
    }

    //TODO: change param to Dimensions object
    public void updateDimensions(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }

    public void updateImage(String imgPath) {
        Image img = makeImage(imgPath);
        setImage(img);
    }

    protected Image makeImage(String imgPath) {
        return new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
    }
}
