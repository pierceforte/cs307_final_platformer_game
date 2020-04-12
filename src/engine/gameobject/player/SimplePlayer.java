package engine.gameobject.player;

import engine.gameobject.GameObject;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimplePlayer extends GameObject implements Player{

    public static final String IMG_PATH = "example_player.png"; //TODO: make this more flexible
    public static final double DEFAULT_X_SPEED = 10;
    public static final double DEFAULT_Y_SPEED = 10; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private Map<KeyCode, Runnable> inputMap;

    public SimplePlayer(double xPos, double yPos, double xSpeed, double ySpeed) {
        super(xPos, yPos, xSpeed, ySpeed);
        inputMap = new HashMap<>()
        {{
            put(KeyCode.L, () -> move(LEFT));
            put(KeyCode.R, () -> move(RIGHT));
            put(KeyCode.SPACE, () -> jump());
        }};
    }

    @Override
    public String getImgPath() {
        return IMG_PATH;
    }

    public void handleInputs(List<KeyCode> codes) {
        for (KeyCode code : codes) {
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