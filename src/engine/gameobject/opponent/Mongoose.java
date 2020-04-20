package engine.gameobject.opponent;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public class Mongoose extends Opponent {

    public static final Double DEFAULT_X_SPEED = 10d;
    public static final double DEFAULT_Y_SPEED = 0d;

    public Mongoose(String imgPath, Double xPos, Double yPos, Double xSpeed) {
        super(imgPath, xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
    }

    public Mongoose(Mongoose copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY(), copy.getXSpeed());
    }

    public void updateLogic(GameObject target) {
        if (target.getX() == this.getX()) {
            setXSpeed(0); // don't move if touching target
        }
        int direction = target.getX() < this.getX() ? GameObject.DOWN_OR_LEFT : GameObject.UP_OR_RIGHT;
        setXSpeed(direction * getInitialX());
    }

    public Double getYSpeedDefault() {
        return DEFAULT_Y_SPEED;
    }
}