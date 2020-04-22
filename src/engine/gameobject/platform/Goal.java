package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;

import java.util.Arrays;
import java.util.List;

public class Goal extends GameObject{

    public static final String EX_IMG_PATH = "images/objects/example_platform.png"; //TODO: make this more flexible
    public static final Double NEW_ENTITY_Y_SPEED = 0d;
    public static final Double X_SPEED = 0d;
    public static final Double Y_SPEED = 0d;

    public Goal(String imgPath, Double xPos, Double yPos) {
        super(imgPath, xPos, yPos, X_SPEED, Y_SPEED);
    }

    public Goal(StationaryPlatform copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY());
    }

    public void handlePlayerInteraction(SimplePlayer player) {
        player.setWinner();
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY());
    }

    public boolean isPlayer() {
        return false;
    }
}
