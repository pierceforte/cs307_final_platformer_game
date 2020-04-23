package engine.gameobject.opponent;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public class Mongoose extends Opponent {

    public static final Double DEFAULT_X_SPEED = 10d;
    public static final double DEFAULT_Y_SPEED = 0d;

    public Mongoose(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
    }

    public Mongoose(Mongoose copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY(), copy.getXSpeed());
    }

    public void updateLogic(GameObject target) {
    }

    public Double getYSpeedDefault() {
        return DEFAULT_Y_SPEED;
    }
}