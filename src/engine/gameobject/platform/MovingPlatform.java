package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.player.SimplePlayer;

public abstract class MovingPlatform extends MovingGameObject implements Platform{

    private Double minX;
    private Double maxX;
    private Double minY;
    private Double maxY;

    public MovingPlatform(String imgPath, Double width, Double height, Double xPos,
                          Double yPos, Double xSpeed, Double ySpeed, Double minX, Double maxX, Double minY, Double maxY) {
        super(imgPath, width, height, xPos, yPos, xSpeed, ySpeed);
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
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
