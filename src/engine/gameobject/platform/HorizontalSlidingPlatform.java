package engine.gameobject.platform;

import engine.gameobject.GameObject;

public class HorizontalSlidingPlatform extends GameObject implements Platform {

    public static final double DEFAULT_X_SPEED = 5;
    public static final double DEFAULT_Y_SPEED = 0;
    public static final double NEW_ENTITY_Y_SPEED = 0;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private double minX;
    private double maxX;

    public HorizontalSlidingPlatform(double xPos, double yPos, double xSpeed,
                                     double minX, double maxX, int direction, String imgPath) {
        super(xPos, yPos, direction * xSpeed, DEFAULT_Y_SPEED, imgPath);
        this.minX = minX;
        this.maxX = maxX;
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
}
