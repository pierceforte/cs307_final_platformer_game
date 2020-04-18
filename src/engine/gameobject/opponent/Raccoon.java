package engine.gameobject.opponent;

import engine.gameobject.GameObject;

import java.util.Arrays;
import java.util.List;

public class Raccoon extends GameObject implements Enemy {

    public static final String EX_IMG_PATH = "raccoon.png"; //TODO: make this more flexible
    public static final Double DEFAULT_X_SPEED = 20d;
    public static final Double DEFAULT_Y_SPEED = 0d;

    private double initXSpeed;

    public Raccoon(String imgPath, Double xPos, Double yPos, Double xSpeed) {
        super(imgPath, xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
        initXSpeed = xSpeed;
    }

    public Raccoon(Raccoon copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY(), copy.getXSpeed());
    }

    // logic is to move toward target in x direction if target facing away
    public void updateLogic(GameObject target) {
        //System.out.println(target.getXDirection());
        if ((target.getXDirection() > 0) == (target.getX() - getX()) > 0) { // if target is facing away, I attack
            int direction = target.getXDirection();
            setXSpeed(direction * initXSpeed);
        }
        else {
            setXSpeed(0); // don't move if touching target or target facing me
        }
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY(), getXSpeed());
    }
    @Override
    public boolean isPlayer() {
        return false;
    }
}