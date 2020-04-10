
package data.input;

import javafx.scene.input.KeyCode;

import java.util.List;

public interface Input {

    /**
     * Watches for a key to be pressed and then removes it from the list when it is released
     * @return the list of keys
     */
    List<KeyCode> keyPressed();
}