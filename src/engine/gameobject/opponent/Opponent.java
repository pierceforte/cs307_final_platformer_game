package engine.gameobject.opponent;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;

import java.util.Arrays;
import java.util.List;

public abstract class Opponent extends MovingGameObject {

    private Double initXSpeed;

    public Opponent(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, ySpeed);
        initXSpeed = xSpeed;
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getWidth(), getHeight(), getX(), getY(), getXSpeed(), getYSpeed());
    }

    public boolean isPlayer() {
        return false;
    }

    public boolean isOpponent() {
        return true;
    }

    protected Double getInitialX() {
        return initXSpeed;
    }
}
