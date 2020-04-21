package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.player.SimplePlayer;
import java.util.Arrays;
import java.util.List;

public class StationaryPlatform extends GameObject implements Platform {

    public static final Double NEW_ENTITY_Y_SPEED = 0d;

    public StationaryPlatform(String imgPath, Double xPos, Double yPos) {
        super(imgPath, xPos, yPos);
    }

    public StationaryPlatform(StationaryPlatform copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY());
    }

    @Override
    public void handleEntityInteraction(GameObject entity) {
        entity.setYSpeed(NEW_ENTITY_Y_SPEED);
    }

    public void handlePlayerInteraction(SimplePlayer player) {
        handleEntityInteraction(player);
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY());
    }
    public boolean isPlayer() {
        return false;
    }
}
