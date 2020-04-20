package engine.gameobject.player;

import javafx.scene.input.KeyCode;

import java.util.List;
/**
 * This interface defines how a specific game's user-operated player will operate
 */

public interface Player {

    /**
     * Assign key inputs to their appropriate actions
     */
    void assignInputs();

    /**
     * Handle key inputs
     * @param keyCode List of inputs currently pressed
     */
    void handleInputs(KeyCode keyCode);
}