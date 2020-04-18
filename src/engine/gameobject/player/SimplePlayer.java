package engine.gameobject.player;

import engine.gameobject.GameObject;
import engine.gameobject.platform.HorizontalSlidingPlatform;
import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimplePlayer extends GameObject implements Player{

    public static final String EX_IMG_PATH = "example_player.png"; //TODO: make this more flexible
    public static final double DEFAULT_X_SPEED = 2; // for key press
    public static final double DEFAULT_Y_SPEED = -2; // for jumping
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private Map<KeyCode, Runnable> inputMap;

    public SimplePlayer(String imgPath, double xPos, double yPos, double xSpeed, double ySpeed) {
        super(imgPath, xPos, yPos, xSpeed, ySpeed);
        assignInputs();
    }

    public SimplePlayer(SimplePlayer copy) {
        this(copy.getImgPath(), copy.getX(), copy.getY(), copy.getXSpeed(), copy.getYSpeed());
    }

    public void assignInputs() {
        inputMap = new HashMap<>()
        {{
            put(KeyCode.LEFT, () -> move(LEFT));
            put(KeyCode.RIGHT, () -> move(RIGHT));
            put(KeyCode.SPACE, () -> jump());
        }};
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

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(getX(), getY(), getXSpeed(), getYSpeed());
    }



}