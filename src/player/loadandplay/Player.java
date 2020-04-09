
import java.util.Map;
/**
 * This interface defines how a specific game's user-operated player will operate
 */
public interface Player {

    /**
     * Assigns how the player is affected by each input; eg. jump, move left/right, run, shoot fireball, etc.
     * @return Map of key codes and their actions when pressed
     */
    Map<KeyCode, Runnable> assignInputs();

}