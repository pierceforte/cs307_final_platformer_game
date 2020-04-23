package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.StationaryGameObject;
import engine.gameobject.player.SimplePlayer;
import java.util.Arrays;
import java.util.List;

public class StationaryPlatform extends StationaryGameObject implements Platform {

    public static final Double NEW_ENTITY_Y_SPEED = 0d;

    public StationaryPlatform(String imgPath, Double width, Double height, Double xPos, Double yPos) {
        super(imgPath, width, height, xPos, yPos);
    }

    public StationaryPlatform(StationaryPlatform copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY());
    }

    @Override
    public void handleEntityInteraction(GameObject entity) {
        if (entity instanceof MovingGameObject) ((MovingGameObject)entity).setYSpeed(NEW_ENTITY_Y_SPEED);
    }

    public void handlePlayerInteraction(SimplePlayer player) {
        handleEntityInteraction(player);
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getWidth(), getHeight(), getX(), getY());
    }

    public boolean isPlayer() {
        return false;
    }
}
