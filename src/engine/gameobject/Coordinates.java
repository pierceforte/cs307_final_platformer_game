package engine.gameobject;

/**
 * This class maintains two doubles representing an x coordinate and a y coordinate. This class's purpose is
 * to eliminate the need to individually pass in an x and a y coordinate everytime they are needed. Likewise,
 * it allows methods to return both a modified x coordinate and a modified y coordinate at the same time.
 *
 * @author Pierce Forte
 */
public class Coordinates {

    private double x;
    private double y;

    /**
     * The constructor to create a Coordinates object.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate.
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y coordinate.
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x coordinate.
     * @param x the new x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate.
     * @param y the new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
}