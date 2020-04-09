
public interface Input {

    /**
     * Watches for a key to be pressed and then removes it from the list when it is released
     * @return the list of keys
     */
    List<Key> keyPressed();

    /**
     * Watches for the mouse to be clicked and then returns the coordinates for the click
     */
    Point mouseClicked();
}