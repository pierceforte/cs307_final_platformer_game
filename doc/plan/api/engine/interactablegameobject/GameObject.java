
public abstract class GameObject {

    private double xPos;
    private double yPos;
    private double xSpeed;
    private double ySpeed;

    public GameObject(double xPos, double yPos, double xSpeed, double ySpeed);

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
    }

    /**
     * Update the y position of the object
     * @param speed distance to move in y direction
     */
    public void updateYPos(double speed) {
        this.setY(this.getY() + speed);
    }

    /**
     * Reverse the x direction
     */
    public void reverseXDirection() {
        this.xSpeed *= -1;
    }

    /**
     * Reverse the y direction
     */
    public void reverseYDirection() {
        this.ySpeed *= -1;
    }
}