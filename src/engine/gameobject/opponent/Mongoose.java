package engine.gameobject.opponent;

import engine.gameobject.GameObject;

public class Mongoose extends GameObject implements Enemy {

    public static final double DEFAULT_X_SPEED = 10;
    public static final double DEFAULT_Y_SPEED = 0;

    private double initXSpeed;

    public Mongoose(double xPos, double yPos, double xSpeed) {
        super(xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
        initXSpeed = xSpeed;
    }

    // logic is to simply move toward target in x direction
    public void assignLogic(GameObject target) {
        if (target.getX() == this.getX()) {
            setXSpeed(0); // don't move if touching target
        }
        int direction = target.getX() < this.getX() ? GameObject.DOWN_OR_LEFT : GameObject.UP_OR_RIGHT;
        setXSpeed(direction * initXSpeed);
    }
}