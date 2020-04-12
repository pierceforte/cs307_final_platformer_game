package engine.gameobject.platform;

import engine.gameobject.GameObject;

public class StationaryPlatform extends GameObject implements Platform {

    public static final String IMG_PATH = "example_platform.png"; //TODO: make this more flexible
    public static final double NEW_ENTITY_Y_SPEED = 0;
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 0;

    public StationaryPlatform(double xPos, double yPos) {
        super(xPos, yPos, X_SPEED, Y_SPEED);
    }

    @Override
    public String getImgPath() {
        return IMG_PATH;
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
