package engine.gameobject.opponent;

import engine.gameobject.GameObject;

public class Mongoose extends GameObject implements Enemy {

    public static final String EX_IMG_PATH = "mongoose.png"; //TODO: make this more flexible
    public static final double DEFAULT_X_SPEED = 10;
    public static final double DEFAULT_Y_SPEED = 0;

    private double initXSpeed;

    public Mongoose(String imgPath, double xPos, double yPos, double xSpeed) {
        super(imgPath, xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
        initXSpeed = xSpeed;
    }

    public Mongoose(Mongoose copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY(), copy.getXSpeed());
    }

    // logic is to simply move toward target in x direction
    public void updateLogic(GameObject target) {
        if (target.getX() == this.getX()) {
            setXSpeed(0); // don't move if touching target
        }
        int direction = target.getX() < this.getX() ? GameObject.DOWN_OR_LEFT : GameObject.UP_OR_RIGHT;
        setXSpeed(direction * initXSpeed);
    }
}