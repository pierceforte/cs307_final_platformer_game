package engine.gameobject.opponent;

import engine.gameobject.GameObject;

/**
 * This class is a basic implementation of the backend for an Opponent GameObject.
 *
 * @author Benjamin Burnett
 * @author Jerry Huang
 * @author Pierce Forte
 */
public class Enemy extends Opponent{

    public static final Double DEFAULT_X_SPEED = 0.01d;
    private final Double MIN_DIST = 8d;
    private double initialX;
    private double initialY;

    public Enemy(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, ySpeed);
        initialX = xPos;
        initialY = yPos;
    }

    public Enemy(Enemy copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(),
                copy.getX(), copy.getY(), copy.getXSpeed(), copy.getYSpeed());
    }

    /**
     * Assigns how the enemy moves; eg. how do they attack the player?
     * @param target the target that should be attacked
     */
    public void updateLogic(GameObject target) {
        if (target.getX() > this.getX() && Math.abs(target.getX() - this.getX()) <= MIN_DIST) {
            this.setX(this.getX() + DEFAULT_X_SPEED);
        }
        if (target.getX() < this.getX() && Math.abs(target.getX() - this.getX()) <= MIN_DIST) {
            this.setX(this.getX() - DEFAULT_X_SPEED);
        }
    }

    public void respawn() {
        this.setX(initialX);
        this.setY(initialY);
    }
}
