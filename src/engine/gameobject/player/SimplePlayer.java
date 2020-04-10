package engine.gameobject.player;

import engine.gameobject.GameObject;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

/*
public class SimplePlayer extends GameObject implements Player{

    public static final double DEFAULT_X_SPEED = 10;
    public static final double DEFAULT_Y_SPEED = 10; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    public SimplePlayer(double xPos, double yPos, double xSpeed, double ySpeed) {
        super(xPos, yPos, xSpeed, ySpeed);

    }

    public Map<KeyCode, Runnable> assignInputs() {
        Map<KeyCode, Runnable> inputMap = new HashMap<>()
        {{
            put(KeyCode.L, () -> move(LEFT));
            put(KeyCode.R, () -> move(RIGHT));
            put(KeyCode.SPACE, () -> jump());
        }};
        return inputMap;
    }

    public void move(int direction) {
        updateXPos(direction * DEFAULT_X_SPEED);
    }

    public void jump() {
        updateYPos(DEFAULT_Y_SPEED);
    }
} */