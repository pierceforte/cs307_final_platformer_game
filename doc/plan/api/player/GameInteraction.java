
/**
 * This interface will define how we will handle user interactions with the game state
 */
public interface GameInteraction {

    /**
     * Defines how the game is paused
     */
    void pause() {

    }

    /**
     * Create and display a GUI for when the game is paused
     */
    void createPauseMenu() {

    }

    /**
     * Defines how the game is restarted
     */
    void restart() {

    }

    /**
     * Defines how the game is saved
     */
    void save() {

    }

    /**
     * Defines how the game is exited
     */
    void exit() {

    }

    /**
     * Defines how the user can switch to a different game
     */
    void switchGame() {

    }

    /*
     * Maybe this stuff should be in GameState
     */

    /**
     * Create and display a GUI for when the game is being played
     */
    void displayHUD() {

    }

    /**
     * Update this GUI when necessary
     */
    void updateHUD() {

    }

}