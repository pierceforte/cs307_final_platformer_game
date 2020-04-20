package engine.gameobject.platform;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public class StationaryPlatform extends GameObject implements Platform {

    public static final String EX_IMG_PATH = "example_platform.png"; //TODO: make this more flexible
    public static final Double NEW_ENTITY_Y_SPEED = 0d;
    public static final Double X_SPEED = 0d;
    public static final Double Y_SPEED = 0d;

    public StationaryPlatform(String imgPath, Double xPos, Double yPos) {
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

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY());
    }
    public boolean isPlayer() {
        return false;
    }
}
