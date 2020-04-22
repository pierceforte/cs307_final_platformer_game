package engine.gameobject.opponent;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;

public class Raccoon extends Opponent {

    private static final Double DEFAULT_Y_SPEED = 0d;
    public static final Double DEFAULT_X_SPEED = 20d;

    public Raccoon(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
    }

    public Raccoon(Raccoon copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(), copy.getY(), copy.getXSpeed());
    }

    public void updateLogic(GameObject target) {

        if (target instanceof MovingGameObject) {
        }

    }
}