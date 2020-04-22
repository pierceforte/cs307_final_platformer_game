package engine.gameobject;

import java.util.Arrays;
import java.util.List;

public abstract class StationaryGameObject extends GameObject {

    public StationaryGameObject(String imgPath, Double width, Double height, Double yPos, Double xPos) {
        super(imgPath, width, height, yPos, xPos);
    }

    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getWidth(), getHeight(), getX(), getY());
    }

    public boolean isStationary() {
        return true;
    }

    public boolean isPlayer() {
        return false;
    }

    public boolean isOpponent() {
        return false;
    }
}
