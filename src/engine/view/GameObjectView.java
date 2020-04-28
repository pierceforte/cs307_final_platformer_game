package engine.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a child of the ImageView class that provides frontend functionality needed by the backend
 * GameObjects. This functionality includes the ability to update position/dimension, check for overlap, and
 * convert attributes to those needed by a tile-based grid.
 *
 * This class is dependent on the image path, position, dimensions, and direction of its associated backend
 * GameObject (although this GameObject is not directly related to/dependent on it).
 *
 * This class is part of a large hierarchy of image views that have a multitude of flexible features allowing for
 * greater user interaction.
 *
 * @author Pierce Forte
 */
public class GameObjectView extends ImageView {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    /**
     * The constructor to create a GameObjectView.
     * @param imgPath the image path
     * @param xPos the x position
     * @param yPos the y position
     * @param width the width
     * @param height the height
     * @param xDirection the x direction
     */
    public GameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        setImage(imgPath);
        updatePos(xPos, yPos);
        int xOrientation = xDirection < 0 ? LEFT : RIGHT;
        updateDimensions(xOrientation * width, height);
    }

    /**
     * Sets the image.
     * @param imgPath the path to the image to be set
     */
    public void setImage(String imgPath) {
        Image image = ImageCreator.makeImage(imgPath);
        setImage(image);
    }

    /**
     * Updates the position.
     * @param xPos the new x position
     * @param yPos the new y position
     */
    public void updatePos(double xPos, double yPos) {
        setX(xPos);
        setY(yPos);
    }

    /**
     * Updates the dimensions.
     * @param width the new width
     * @param height the new height
     */
    public void updateDimensions(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }

    /**
     * Determines if this GameObjectView overlaps another ImageView.
     * @param b the other ImageView
     * @return whether this node overlaps the other
     */
    public boolean overlaps(ImageView b) {
        return  this.getX() < b.getX() + b.getFitWidth() &&
                this.getX() + this.getFitWidth() > b.getX() &&
                this.getY() < b.getY() +b.getFitHeight() &&
                this.getY() + this.getFitHeight() >b.getY();
    }

    /**
     * Converts this GameObjectView's attributes to ones with units in tiles.
     * @param tileWidth the absolute width of a tile unit
     * @param tileHeight the absolute height of a tile unit
     */
    public void convertAttributesToGridBased(double tileWidth, double tileHeight) {
        setX(getX() * tileWidth);
        setY(getY() * tileHeight);
        setFitWidth(tileWidth);
        setFitHeight(tileHeight);
    }
}
