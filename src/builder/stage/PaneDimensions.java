package builder.stage;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 *
 * @author Pierce Forte
 */
public class PaneDimensions {

    public static final double DEFAULT_MIN_X = 0;
    public static final double DEFAULT_MIN_Y = 0;
    public static final double TILE_WIDTH_FACTOR = 30;
    public static final double TILE_HEIGHT_FACTOR = 25;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double screenWidth;
    private double screenHeight;
    private double tileWidth;
    private double tileHeight;

    public PaneDimensions(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        setScreenSize();
        setTileSize();
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
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