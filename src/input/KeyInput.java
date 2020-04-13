package data.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class KeyInput {
    List<KeyCode> pressedKeys = new ArrayList<>();
    Scene myScene;

    public KeyInput(Scene scene) {
        myScene = scene;
        myScene.setOnKeyPressed(k -> handlePressedKeyInput(k.getCode()));
        myScene.setOnKeyReleased(k -> handleReleasedKeyInput(k.getCode()));
    }

    private void handleReleasedKeyInput(KeyCode code) {
        pressedKeys.remove(code);
    }

    private void handlePressedKeyInput(KeyCode key) {
        pressedKeys.add(key);
    }

    public List<KeyCode> getPressedKeys() {
        return pressedKeys;
    }
}
