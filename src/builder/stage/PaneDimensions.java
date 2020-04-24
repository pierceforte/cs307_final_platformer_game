package builder.stage;

import engine.gameobject.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.util.List;

/**
 *
 * @author Pierce Forte
 */
public class PaneDimensions {

    public static final double DEFAULT_MIN_X = 0;
    public static final double DEFAULT_MIN_Y = 0;
    public static final double TILE_WIDTH_FACTOR = 30;
    public static final double TILE_HEIGHT_FACTOR = 25;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private double screenWidth;
    private double screenHeight;
    private double tileWidth;
    private double tileHeight;

    public PaneDimensions(Integer minX, Integer maxX, Integer minY, Integer maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        setScreenSize();
        setTileSize();
    }

    public PaneDimensions(List<GameObject> gameObjects) {
        this.minX = getMinXInLevel(gameObjects);
        this.maxX = getMaxXInLevel(gameObjects);
        this.minY = getMinYInLevel(gameObjects);
        this.maxY = getMaxYInLevel(gameObjects);
        setScreenSize();
        setTileSize();
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public double getTileHeight() {
        return tileHeight;
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

    private void setTileSize() {
        tileWidth = screenWidth/TILE_WIDTH_FACTOR;
        tileHeight = screenHeight/TILE_HEIGHT_FACTOR;
    }
}