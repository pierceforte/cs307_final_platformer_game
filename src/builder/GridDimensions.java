package builder;

import engine.view.GameObjectView;

import java.util.List;

public class GridDimensions {

    private double screenWidth;
    private double screenHeight;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public GridDimensions(double screenWidth, double screenHeight, double minX, double maxX, double minY, double maxY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public GridDimensions(double screenWidth, double screenHeight, List<GameObjectView> gameObjectViews) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.minX = getMinXInLevel(gameObjectViews);
        this.maxX = getMaxXInLevel(gameObjectViews);
        this.minY = getMinYInLevel(gameObjectViews);
        this.maxY = getMaxYInLevel(gameObjectViews);
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
}