package engine.gameobject.opponent;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;

public class Raccoon extends Enemy {

    public static final Double DEFAULT_X_SPEED = 0.01d;
    public static final Double DEFAULT_Y_SPEED = 0.5d;

    public Raccoon(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
    }

    public Raccoon(Raccoon copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY(), copy.getXSpeed());
    }
}