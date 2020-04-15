package engine.gameobject.opponent;

import engine.gameobject.GameObject;

public class Raccoon extends GameObject implements Enemy {

    public static final String EX_IMG_PATH = "raccoon.png"; //TODO: make this more flexible
    public static final double DEFAULT_X_SPEED = 20;
    public static final double DEFAULT_Y_SPEED = 0;

    private double initXSpeed;

    public Raccoon(double xPos, double yPos, double xSpeed, String imgPath) {
        super(xPos, yPos, xSpeed, DEFAULT_Y_SPEED, imgPath);
        initXSpeed = xSpeed;
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
}