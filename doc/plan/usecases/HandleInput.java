
public class HandleInput {

    Scene myScene;
    List<Key> pressedKeys = new ArrayList<>();

    public HandleInput(Scene scene) {
        myScene = scene;
    }

    /**
     * Watches for a key to be pressed and then removes it from the list when it is released
     * @return the list of keys
     */
    public List<Key> keysPressed() {
        myScene.setOnKeyPressed(y -> pressedKeys.add(y.getCode()));
        myScene.setOnKeyReleased(x -> pressedKeys.remove(y.getCode()));
        return pressedKeys;
    }

    /**
     * Watches for the mouse to be clicked and then returns the coordinates for the click
     */
    public Point mouseClicked() {
        double clickX = event.getX();
        double clickY = event.getY();
        return new Point(clickX, clickY);
    }
}