package engine.gameobject;

public abstract class GameObject {

    public static final int DOWN_OR_LEFT = -1;
    public static final int UP_OR_RIGHT = 1;

    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;
    private int xDirection;
    private int yDirection;
    private String imgPath;

    public GameObject(double xPos, double yPos, double xSpeed, double ySpeed, String imgPath) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        xDirection = UP_OR_RIGHT;
        yDirection = UP_OR_RIGHT;
        this.imgPath = imgPath;
    }
    /**
     * Get the image path for this object
     * @return path to the image for this object
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Set x position of the object
     * @param xPos new x position of the object
     */
    public void setX(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Set y position of the object
     * @param yPos new y position of the object
     */
    public void setY(double yPos) {
        this.yPos = yPos;
    }

    /**
     * Get x position of the object
     * @return x position of the object
     */
    public double getX() {
        return xPos;
    }

    /**
     * Get y position of the object
     * @return y position of the object
     */
    public double getY() {
        return yPos;
    }

    /**
     * Get x speed of the object
     * @return x speed of object
     */
    public double getXSpeed() {
        return xSpeed;
    }

    /**
     * Set x speed of the object
     * @param xSpeed the x speed to set
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
        xDirection = setDirection(getXDirection(), xSpeed);
    }

    /**
     * Get y speed of the object
     * @return y speed of object
     */
    public double getYSpeed() {
        return ySpeed;
    }

    /**
     * Set y speed of the object
     * @param ySpeed the y speed to set
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
        yDirection = setDirection(getYDirection(), ySpeed);
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
        xDirection = setDirection(xDirection, speed);
    }

    /**
     * Update the y position of the object
     * @param speed distance to move in y direction
     */
    public void updateYPos(double speed) {
        this.setY(this.getY() + speed);
        yDirection = setDirection(yDirection, speed);
    }

    /**
     * Get the x direction
     * @return x direction
     */
    public int getXDirection() {
        return xDirection;
    }

    /**
     * Get the y direction
     * @return y direction
     */
    public int getYDirection() {
        return yDirection;
    }

    /**
     * Reverse the x direction
     */
    public void reverseXDirection() {
        this.xSpeed *= -1;
        this.xDirection *= -1;
    }

    /**
     * Reverse the y direction
     */
    public void reverseYDirection() {
        this.ySpeed *= -1;
        this.yDirection *= -1;
    }

    private int setDirection(int initDirection, double speed) {
        if (speed == 0) return initDirection;
        return speed < 0 ? DOWN_OR_LEFT : UP_OR_RIGHT;
    }

}