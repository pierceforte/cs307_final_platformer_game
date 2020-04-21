package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;

import java.util.Arrays;
import java.util.List;

public class Goal extends GameObject {

    public static final Double X_SPEED = 0d;
    public static final Double Y_SPEED = 0d;

    public Goal(String imgPath, Double width, Double height, Double xPos, Double yPos) {
        super(imgPath, width, height, xPos, yPos);
    }

    public Goal(StationaryPlatform copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY());
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
