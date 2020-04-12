package engine.gameobject.player;

import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Map;
/**
 * This interface defines how a specific game's user-operated player will operate
 */

public interface Player {

    /**
     * Handle key inputs
     * @param codes List of inputs currently pressed
     */
    void handleInputs(List<KeyCode> codes);
}