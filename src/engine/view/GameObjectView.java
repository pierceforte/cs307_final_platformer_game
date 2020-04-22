package engine.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

//TODO: Decide whether to use inheritance or composition for ImageView
public class GameObjectView extends ImageView {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    private static Map<String, Image> imageMap = new HashMap<>();

    public GameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        setImage(imgPath);
        updatePos(xPos, yPos);
        int xOrientation = xDirection < 0 ? LEFT : RIGHT;
        updateDimensions(xOrientation * width, height);
    }

    public void setImage(String imgPath) {
        // NOTE: we cannot use putIfAbsent because this requires you to make the Image object every time
        Image image;
        if (imageMap.containsKey(imgPath)) {
            image = imageMap.get(imgPath);
        }
        else {
            image = makeImage(imgPath);
            imageMap.put(imgPath, image);
        }
        setImage(image);
    }

    public void updatePos(double xPos, double yPos) {
        setX(xPos);
        setY(yPos);
    }

    public void updateDimensions(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }

    public double getCenterX() {
        return getX() + getFitWidth()/2;
    }

    public double getCenterY() {
        return getY() + getFitHeight()/2;
    }

    public void convertAttributesToGridBased(double tileWidth, double tileHeight) {
        setX(getX() * tileWidth);
        setY(getY() * tileHeight);
        setFitWidth(tileWidth);
        setFitHeight(tileHeight);
    }

    protected Image makeImage(String imgPath) {
        return new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
    }

}
