
/**
 * This interface defines how a specific game's user-operated player will operate
 */
public interface Player {

    /**
     * Assigns how the player is affected by each input
     * @param inputMap A map from each key code and its resulting action
     */
    void assignInputs(Map<KeyCode, Runnable> inputMap) {
        // jump, move left/right, run, shoot fireball, etc.
    }

}