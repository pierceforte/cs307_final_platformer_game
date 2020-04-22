package engine.gameobject;

import java.util.Arrays;
import java.util.List;

public abstract class MovingGameObject extends GameObject{

    public static final int DOWN_OR_LEFT = -1;
    public static final int UP_OR_RIGHT = 1;

    private Double xSpeed;
    private Double ySpeed;
    private Integer xDir;
    private Integer yDir;

    public MovingGameObject(String imgPath, Double width, Double height, Double xPos,
                            Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, width, height, yPos, xPos);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        if (xSpeed != 0) xDir = ((Double)(xSpeed / Math.abs(xSpeed))).intValue();
        else xDir = UP_OR_RIGHT;
        if (ySpeed != 0) yDir = ((Double)(ySpeed / Math.abs(ySpeed))).intValue();
        else yDir = UP_OR_RIGHT;
    }

    /**
     * Get x speed of the object
     * @return x speed of object
     */
    public Double getXSpeed() {
        return xSpeed;
    }

    /**
     * Set x speed of the object
     * @param xSpeed the x speed to set
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
        xDir = setDirection(getXDirection(), xSpeed);
    }

    /**
     * Get y speed of the object
     * @return y speed of object
     */
    public Double getYSpeed() {
        return ySpeed;
    }

    /**
     * Set y speed of the object
     * @param ySpeed the y speed to set
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
        yDir = setDirection(getYDirection(), ySpeed);
    }

    /**
     * Update the position of the object for each step
     * @param elapsedTime the time that is elapsed after a single step
     */
    public void updatePositionOnStep(double elapsedTime) {
        this.setX(this.getX() + this.getXSpeed() * elapsedTime);
        this.setY(this.getY() - this.getYSpeed() * elapsedTime);
    }

    /**
     * Update the x position of the object
     * @param speed distance to move in x direction
     */
    public void updateXPos(double speed) {
        this.setX(this.getX() + speed);
        xDir = setDirection(xDir, speed);
    }

    /**
     * Update the y position of the object
     * @param speed distance to move in y direction
     */
    public void updateYPos(double speed) {
        this.setY(this.getY() + speed);
        yDir = setDirection(yDir, speed);
    }

    private Integer setDirection(Integer initDirection, Double speed) {
        if (speed == 0) return initDirection;
        return speed < 0 ? DOWN_OR_LEFT : UP_OR_RIGHT;
    }

    /**
     * Get the x direction
     * @return x direction
     */
    public Integer getXDirection() {
        return xDir;
    }

    /**
     * Get the y direction
     * @return y direction
     */
    public Integer getYDirection() {
        return yDir;
    }

    /**
     * Reverse the x direction
     */
    public void reverseXDirection() {
        xSpeed *= -1;
        xDir = ((Double)(xSpeed / Math.abs(xSpeed))).intValue();
    }

    /**
     * Reverse the y direction
     */
    public void reverseYDirection() {
        ySpeed *= -1;
        yDir = ((Double)(ySpeed / Math.abs(ySpeed))).intValue();;
    }

    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getWidth(), getHeight(), getX(), getY(), getXSpeed(), getYSpeed());
    }

    public boolean isStationary() {
        return false;
    }
}
