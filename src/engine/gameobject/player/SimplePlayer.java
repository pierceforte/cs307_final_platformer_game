package engine.gameobject.player;

import engine.gameobject.MovingGameObject;
import javafx.scene.input.KeyCode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimplePlayer extends MovingGameObject implements Player{

    public static final Double DEFAULT_X_SPEED = 2d; // for key press
    public static final Double DEFAULT_Y_SPEED = -2d; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private Map<KeyCode, Runnable> inputMap;
    private boolean hasWon = false;
    private boolean hasLost = false;

    public SimplePlayer(String imgPath, Double width, Double height, Double xPos, Double yPos, Double xSpeed, Double ySpeed) {
        super(imgPath, width, height, xPos, yPos, xSpeed, ySpeed);
        assignInputs();
    }

    public SimplePlayer(SimplePlayer copy) {
        this(copy.getImgPath(), copy.getWidth(), copy.getHeight(), copy.getX(),
                copy.getY(), copy.getXSpeed(), copy.getYSpeed());
    }

    public void setWinner() {
        hasWon = true;
    }

    public void setLoser() {
        hasLost = true;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public boolean isOpponent() {
        return false;
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
            put(KeyCode.A, () -> move(LEFT));
            put(KeyCode.D, () -> move(RIGHT));
            put(KeyCode.W, () -> jump());
        }};
    }

    public void handleInput(KeyCode code) {
        if (inputMap.containsKey(code)) {
            inputMap.get(code).run();
        }
    }

    public void handleInputs(List<KeyCode> keyInputs) {
        for (KeyCode code : keyInputs) {
            handleInput(code);
        }
    }

    public void move(int direction) {
        updateXPos(direction * DEFAULT_X_SPEED);
    }

    public void jump() {
        updateYPos(DEFAULT_Y_SPEED);
    }
}