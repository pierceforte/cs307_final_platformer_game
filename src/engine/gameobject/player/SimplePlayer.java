package engine.gameobject.player;

import engine.gameobject.GameObject;
import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimplePlayer extends GameObject implements Player{

    public static final String EX_IMG_PATH = "images/avatars/example_player.png"; //TODO: make this more flexible
    public static final Double DEFAULT_X_SPEED = 2d; // for key press
    public static final Double DEFAULT_Y_SPEED = -2d; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private Map<KeyCode, Runnable> inputMap;
    private boolean hasWon;
    private boolean hasLost;

    public SimplePlayer(String imgPath, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, xPos, yPos, xSpeed, ySpeed);
        assignInputs();
    }

    public SimplePlayer(SimplePlayer copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY(), copy.getXSpeed(), copy.getYSpeed());
    }

    public void setWinner() {
        hasWon = true;
    }

    public void setLoser() {
        hasLost = true;
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getImgPath(), getX(), getY(), getXSpeed(), getYSpeed());
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void assignInputs() {
        inputMap = new HashMap<>()
        {{
            put(KeyCode.LEFT, () -> move(LEFT));
            put(KeyCode.RIGHT, () -> move(RIGHT));
            put(KeyCode.SPACE, () -> jump());
        }};
    }

    public void handleInputs(List<KeyCode> keyInputs) {
        for (KeyCode code : keyInputs) {
            if (inputMap.containsKey(code)) {
                inputMap.get(code).run();
            }
        }
    }

    public void move(int direction) {
        updateXPos(direction * DEFAULT_X_SPEED);
    }

    public void jump() {
        updateYPos(DEFAULT_Y_SPEED);
    }
}