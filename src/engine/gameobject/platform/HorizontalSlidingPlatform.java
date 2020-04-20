package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;

import java.util.Arrays;
import java.util.List;

public class HorizontalSlidingPlatform extends GameObject implements Platform {

    public static final double DEFAULT_X_SPEED = 5;
    public static final double DEFAULT_Y_SPEED = 0;
    public static final double NEW_ENTITY_Y_SPEED = 0;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private double minX;
    private double maxX;

    public HorizontalSlidingPlatform(String imgPath, Double xPos, Double yPos, Double xSpeed,
                                     Double minX, Double maxX, Integer xDirection) {
        super(imgPath, xPos, yPos, xDirection * xSpeed, DEFAULT_Y_SPEED);
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY(), getXSpeed(), minX, maxX, getXDirection());
    }

    public HorizontalSlidingPlatform(HorizontalSlidingPlatform copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY(), copy.getXSpeed(), copy.getMinX(), copy.getMaxX(), copy.getXDirection());
    }

    public void move(int direction) {
        if (getX() >= maxX || getX() <= minX) {
            reverseXDirection();
        }
        else {
            updateXPos(getXSpeed());
        }
    }

    public void handleEntityInteraction(GameObject entity) {
        entity.setXSpeed(getXSpeed());
        entity.setYSpeed(NEW_ENTITY_Y_SPEED);
    }

    @Override
    public void handlePlayerInteraction(SimplePlayer player) {
        handleEntityInteraction(player);
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}
