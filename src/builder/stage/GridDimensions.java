package builder.stage;

import engine.view.GameObjectView;

import java.util.List;

public class GridDimensions {

    public static final double TILE_WIDTH_FACTOR = 30;
    public static final double TILE_HEIGHT_FACTOR = 25;

    private double screenWidth;
    private double screenHeight;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double tileWidth;
    private double tileHeight;

    public GridDimensions(double screenWidth, double screenHeight, double minX, double maxX, double minY, double maxY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        setTileSize();
    }

    public GridDimensions(double screenWidth, double screenHeight, List<GameObjectView> gameObjectViews) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.minX = getMinXInLevel(gameObjectViews);
        this.maxX = getMaxXInLevel(gameObjectViews);
        this.minY = getMinYInLevel(gameObjectViews);
        this.maxY = getMaxYInLevel(gameObjectViews);
        setTileSize();
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
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

    public double getTileWidth() {
        return tileWidth;
    }

    public double getTileHeight() {
        return tileHeight;
    }

    private double getMinXInLevel(List<GameObjectView> gameObjectViews) {
        double minX = Double.MAX_VALUE;
        for (GameObjectView gameObjectView : gameObjectViews) {
            minX = gameObjectView.getX() < minX ? gameObjectView.getX() : minX;
        }
        return minX;
    }

    private double getMaxXInLevel(List<GameObjectView> gameObjectViews) {
        double maxX = Double.MIN_VALUE;
        for (GameObjectView gameObjectView : gameObjectViews) {
            maxX = gameObjectView.getX() > maxX ? gameObjectView.getX() : maxX;
        }
        return maxX;
    }

    private double getMinYInLevel(List<GameObjectView> gameObjectViews) {
        double minY = Double.MAX_VALUE;
        for (GameObjectView gameObjectView : gameObjectViews) {
            minY = gameObjectView.getY() < minY ? gameObjectView.getY() : minY;
        }
        return minY;
    }

    private double getMaxYInLevel(List<GameObjectView> gameObjectViews) {
        double maxY = Double.MIN_VALUE;
        for (GameObjectView gameObjectView : gameObjectViews) {
            maxY = gameObjectView.getY() > maxY ? gameObjectView.getY() : maxY;
        }
        return maxY;
    }

    private void setTileSize() {
        tileWidth = screenWidth/TILE_WIDTH_FACTOR;
        tileHeight = screenHeight/TILE_HEIGHT_FACTOR;
    }
}