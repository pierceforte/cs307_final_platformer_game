package builder.stage;

import engine.gameobject.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.util.List;

/**
 * This class stores and provides access to the dimensions for any Pane. This class can be used to specify the size
 * of any Pane as well as maintain the size of the screen that the the Pane is presented on.
 *
 * This class is dependent on the screen on which it is portrayed, for it must access the sizing information of this screen.
 *
 * This class is typically used by Panes that must maintain a certain boundary for their nodes.
 *
 * @author Pierce Forte
 */
public class PaneDimensions {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private double screenWidth;
    private double screenHeight;

    /**
     * The constructor used to create a PaneDimensions object.
     * @param minX the minimum x position of the Pane
     * @param maxX the maximum x position of the Pane
     * @param minY the minimum y position of the Pane
     * @param maxY the maximum y position of the Pane
     */
    public PaneDimensions(Integer minX, Integer maxX, Integer minY, Integer maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        setScreenSize();
    }

    /**
     * The constructor used to create a PaneDimensions object based on a list of GameObjects.
     * This list of GameObjects, which is typically used to populate a level for a game, can be used
     * to determine the boundaries for a Pane based on the positioning of these GameObjects.
     * @param gameObjects a list of GameObjects
     */
    public PaneDimensions(List<GameObject> gameObjects) {
        this.minX = getMinXInLevel(gameObjects);
        this.maxX = getMaxXInLevel(gameObjects);
        this.minY = getMinYInLevel(gameObjects);
        this.maxY = getMaxYInLevel(gameObjects);
        setScreenSize();
    }

    /**
     * Get the minimum x position for the Pane.
     * @return the minimum x position for the Pane
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Get the maximum x position for the Pane.
     * @return the maximum x position for the Pane
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Get the minimum y position for the Pane.
     * @return the minimum y position for the Pane
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Get the maximum y position for the Pane.
     * @return the maximum y position for the Pane
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Get the width of the screen within which the Pane is located.
     * @return the screen width
     */
    public double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Get the height of the screen within which the Pane is located.
     * @return the screen height
     */
    public double getScreenHeight() {
        return screenHeight;
    }

    private int getMinXInLevel(List<GameObject> gameObjects) {
        int minX = Integer.MAX_VALUE;
        for (GameObject gameObject : gameObjects) {
            minX = (int) (gameObject.getX() < minX ? gameObject.getX() : minX);
        }
        return minX;
    }

    private int getMaxXInLevel(List<GameObject> gameObjects) {
        int maxX = Integer.MIN_VALUE;
        for (GameObject gameObject : gameObjects) {
            maxX = (int) (gameObject.getX() > maxX ? gameObject.getX() : maxX);
        }
        return maxX;
    }

    private int getMinYInLevel(List<GameObject> gameObjects) {
        int minY = Integer.MAX_VALUE;
        for (GameObject gameObject : gameObjects) {
            minY = (int) (gameObject.getY() < minY ? gameObject.getY() : minY);
        }
        return minY;
    }

    private int getMaxYInLevel(List<GameObject> gameObjects) {
        int maxY = Integer.MIN_VALUE;
        for (GameObject gameObject : gameObjects) {
            maxY = (int) (gameObject.getY() > maxY ? gameObject.getY() : maxY);
        }
        return maxY;
    }

    private void setScreenSize() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = primaryScreenBounds.getWidth();
        screenHeight = primaryScreenBounds.getHeight();
    }
}