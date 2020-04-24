package engine.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class GameObjectView extends ImageView {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    public GameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        setImage(imgPath);
        updatePos(xPos, yPos);
        int xOrientation = xDirection < 0 ? LEFT : RIGHT;
        updateDimensions(xOrientation * width, height);
    }

    public void setImage(String imgPath) {
        // NOTE: we cannot use putIfAbsent because this requires you to make the Image object every time
        Image image = ImageCreator.makeImage(imgPath);
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

    public boolean overlaps(ImageView b) {
        return  this.getX() < b.getX() + b.getFitWidth() &&
                this.getX() + this.getFitWidth() > b.getX() &&
                this.getY() < b.getY() +b.getFitHeight() &&
                this.getY() + this.getFitHeight() >b.getY();
    }

    public void convertAttributesToGridBased(double tileWidth, double tileHeight) {
        setX(getX() * tileWidth);
        setY(getY() * tileHeight);
        setFitWidth(tileWidth);
        setFitHeight(tileHeight);
    }
}
