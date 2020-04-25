package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.player.SimplePlayer;

import java.util.Arrays;
import java.util.List;

public class HorizontalSlidingPlatform extends MovingPlatform implements Platform {

    public static final double NEW_ENTITY_Y_SPEED = 0;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private Double minX;
    private Double maxX;

    public HorizontalSlidingPlatform(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed,
                                     Double minX, Double maxX) {
        super(imgPath, width, height, xPos, yPos, xSpeed, 0d, minX, maxX, yPos, yPos);
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getWidth(), getHeight(), getX(), getY(), getXSpeed(), minX, maxX);
    }

    public HorizontalSlidingPlatform(HorizontalSlidingPlatform copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY(),
                copy.getXSpeed(), copy.getMinX(), copy.getMaxX());
    }

    public void move(int direction) {
        if (getX() >= maxX || getX() <= minX) reverseXDirection();
        else updateXPos(getXSpeed());
    }

    public void handleEntityInteraction(GameObject entity) {
        if (!(entity instanceof MovingGameObject)) {
            reverseXDirection();
            reverseYDirection();
            return;
        }
        MovingGameObject mover = (MovingGameObject) entity;
        mover.reverseXDirection();
        mover.reverseYDirection();
    }

    @Override
    public void handlePlayerInteraction(SimplePlayer player) {
        handleEntityInteraction(player);
    }

}
