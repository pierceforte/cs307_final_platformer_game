package engine.gameobject.opponent;

import engine.gameobject.GameObject;

public class Raccoon extends GameObject implements Enemy {

    public static final String IMG_PATH = "raccoon.png"; //TODO: make this more flexible
    public static final double DEFAULT_X_SPEED = 20;
    public static final double DEFAULT_Y_SPEED = 0;

    private double initXSpeed;

    public Raccoon(double xPos, double yPos, double xSpeed) {
        super(xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
        initXSpeed = xSpeed;
    }

    // logic is to simply move toward target in x direction
    public void updateLogic(GameObject target) {
        if (target.getX() == this.getX()) {
            setXSpeed(0); // don't move if touching target
        }
        if ((target.getXDirection() > 0) == (target.getX() - getX()) > 0) { // if target is facing away, I attack
            int direction = target.getXDirection();
            setXSpeed(direction * initXSpeed);
        }
        int direction = target.getX() < this.getX() ? -1 : 1;
        setXSpeed(direction * initXSpeed);
    }

    @Override
    public String getImgPath() {
        return IMG_PATH;
    }
}