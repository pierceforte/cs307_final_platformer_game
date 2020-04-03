public class SlidingHorizontalPlatform extends GameObject implements Platform{

    public static final double DEFAULT_X_SPEED = 5;
    public static final double DEFAULT_Y_SPEED = 0; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private double minX;
    private double maxX;

    public SlidingHorizontalPlatform(double xPos, double yPos, double xSpeed,
                           double minX, double maxX, int direction) {
        super(xPos, yPos, xSpeed, DEFAULT_Y_SPEED);
        this.minX = minX;
        this.maxX = maxX;
    }

    public void move(int direction) {
        if (getX() >= maxX || getX() <= minX) {
            reverseXDirection();
        }
        else {
            updateXPos(getXSpeed());
        }
    }

    public void handleEntityInteraction(GameObject entity) {
        entity.setXSpeed(getXSpeed());
    }
}