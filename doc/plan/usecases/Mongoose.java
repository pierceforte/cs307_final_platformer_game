

public class Mongoose extends GameObject implements Enemy {

    public static final double X_SPEED = 10;

    public Mongoose(double xPos, double yPos, double xSpeed, double ySpeed);

    // logic is to simply move toward target in x direction
    public void assignLogic(Point2D targetPos) {
        if (targetPos.getX() == this.getX()) {
            setXSpeed(0); // don't move if touching target
        }
        int direction = targetPos.getX() < this.getX() ? -1 : 1;
        setXSpeed(direction * X_SPEED);
    }
}