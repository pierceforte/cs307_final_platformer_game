package engine.gameobject.platform;

import engine.gameobject.GameObject;

public class StationaryPlatform extends GameObject implements Platform {

    public static final String EX_IMG_PATH = "example_platform.png"; //TODO: make this more flexible
    public static final double NEW_ENTITY_Y_SPEED = 0;
    public static final double X_SPEED = 0;
    public static final double Y_SPEED = 0;

    public StationaryPlatform(String imgPath, double xPos, double yPos) {
        super(imgPath, xPos, yPos, X_SPEED, Y_SPEED);
    }

    public StationaryPlatform(StationaryPlatform copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY());
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
