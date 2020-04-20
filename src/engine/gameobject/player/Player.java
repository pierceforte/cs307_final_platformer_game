package engine.gameobject.player;

import javafx.scene.input.KeyCode;

import java.util.List;
/**
 * This interface defines how a specific game's user-operated player will operate
 */

public interface Player {

    /**
     * True when the win condition is achieved.
     */
    boolean hasWon();

    /**
     * True when the player has died.
     */
    boolean hasLost();
    /**
     * Assign key inputs to their appropriate actions
     */
    void assignInputs();

    /**
     * Handle key inputs
     * @param codes List of inputs currently pressed
     */
    void handleInputs(List<KeyCode> codes);
}