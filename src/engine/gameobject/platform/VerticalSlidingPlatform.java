package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.player.SimplePlayer;

import java.util.Arrays;
import java.util.List;

public class VerticalSlidingPlatform extends MovingPlatform implements Platform{

    public VerticalSlidingPlatform(String imgPath, Double width, Double height, Double xPos, Double yPos, Double ySpeed,
                                     Double minY, Double maxY) {
        super(imgPath, width, height, xPos, yPos, 0d, ySpeed, xPos, xPos, minY, maxY);
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getWidth(), getHeight(), getX(), getY(), getYSpeed(), getMinY(), getMaxY());
    }

    public VerticalSlidingPlatform(VerticalSlidingPlatform copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY(),
                copy.getYSpeed(), copy.getMinX(), copy.getMaxX());
    }

    public void move(int direction) {
        if (getY() >= getMaxY() || getY() <= getMaxY()) reverseXDirection();
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
