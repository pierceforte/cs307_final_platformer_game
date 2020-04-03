public class SimplePlayer extends GameObject implements Player{

    public static final double X_SPEED = 10;
    public static final double Y_SPEED = 10; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    public SimplePlayer(double xPos, double yPos, double xSpeed, double ySpeed) {
    }

    public Map<KeyCode, Runnable> assignInputs() {
        Map<KeyCode, Runnable> inputMap = new HashMap<>();
        inputMap.put(
                KeyCode.L, () -> move(LEFT),
                KeyCode.R, () -> move(RIGHT),
                KeyCode.SPACE, () -> jump()
                );
        return inputMap;
    }

    public void move(int direction) {
        updateXPos(direction * X_SPEED);
    }

    public void jump() {
        updateYPos(direction * Y_SPEED);
    }
}