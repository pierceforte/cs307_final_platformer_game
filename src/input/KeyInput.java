package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyInput {
    Set<KeyCode> pressedKeys = new HashSet<>();
    Scene myScene;

    public KeyInput(Scene scene) {
        myScene = scene;
        myScene.setOnKeyPressed(k -> handlePressedKeyInput(k.getCode()));
        myScene.setOnKeyReleased(k -> handleReleasedKeyInput(k.getCode()));
    }

    public List<KeyCode> getPressedKeys() {
        return new ArrayList<>(pressedKeys);
    }

    public void releaseKeys() {
        pressedKeys.clear();
    }

    private void handleReleasedKeyInput(KeyCode code) {
        pressedKeys.remove(code);
    }

    private void handlePressedKeyInput(KeyCode key) {
        pressedKeys.add(key);
    }
}
