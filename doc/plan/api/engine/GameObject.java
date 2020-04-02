
public interface GameObject {

    /**
     * Set x position of the object
     * @param xPos new x position of the object
     */
    void setX(double xPos) {

    }

    /**
     * Set y position of the object
     * @param yPos new y position of the object
     */
    void setY(double yPos) {

    }

    /**
     * Get x position of the object
     * @return x position of the object
     */
    double getX() {

    }

    /**
     * Get y position of the object
     * @return y position of the object
     */
    double getY() {

    }

    /**
     * Get x speed of the object
     * @return x speed of object
     */
    double getXSpeed() {

    }

    /**
     * Set x speed of the object
     * @param xSpeed the x speed to set
     */
    void setXSpeed(double xSpeed) {

    }

    /**
     * Get y speed of the object
     * @return y speed of object
     */
    double getYSpeed() {

    }

    /**
     * Set y speed of the object
     * @param ySpeed the y speed to set
     */
    void setYSpeed(double ySpeed) {

    }

    /**
     * Update the position of the object for each step
     * @param elapsedTime the time that is elapsed after a single step
     */
    void updatePositionOnStep(double elapsedTime) {

    }

    /**
     * Check if the object is out of bounds in the x direction
     * @return Boolean value whether the moving object is out of bounds in the x direction
     */
    boolean isOutOfXBounds() {

    }

    /**
     * Check if the object is out of bounds in the y direction
     * @return Boolean value whether the moving object is out of bounds in the y direction
     */
    boolean isOutOfYBounds() {

    }

    /**
     * Check whether the two nodes on the screen intersects with each other
     * @param node The node that is being checked for a collision with this object
     * @return whether node (the parameter) is colliding with this object
     */
    public boolean intersects(Node node) {

    }
}