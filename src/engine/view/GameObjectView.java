package engine.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//TODO: Decide whether to use inheritance or composition for ImageView
public class GameObjectView extends ImageView {

    //TODO: change params to Coords and Dimensions objects OR just pass the GameObject (probably don't want this dependency though)
    public GameObjectView(String imgName, double xPos, double yPos, double width, double height) {
        Image img = new Image(this.getClass().getClassLoader().getResource(imgName).toExternalForm());
        setImage(img);
        setX(xPos);
        setY(yPos);
        setFitWidth(width);
        setFitHeight(height);
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

    public void updateImage(String imgName) {
        Image img = makeImage(imgName);
        setImage(img);
    }

    private Image makeImage(String imgName) {
        return new Image(this.getClass().getClassLoader().getResource(imgName).toExternalForm());
    }
}
