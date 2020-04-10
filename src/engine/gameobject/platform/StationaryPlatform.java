package engine.gameobject.platform;

import engine.gameobject.GameObject;

public class StationaryPlatform extends GameObject implements Platform {

    public static final double NEW_ENTITY_Y_SPEED = 0;

    public StationaryPlatform(double xPos, double yPos, double xSpeed, double ySpeed) {
        super(xPos, yPos, xSpeed, ySpeed);
    }

    @Override
    public String getImgPath() {
        return null;
    }

    @Override
    public void move(int direction) {
        return;
    }

    @Override
    public void handleEntityInteraction(GameObject entity) {
        entity.setYSpeed(NEW_ENTITY_Y_SPEED);
    }
}
