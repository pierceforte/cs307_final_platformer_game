package engine.leveldirectory.gameeventhandler;

import engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Abstract class used for all Game Events.
 *
 * @author Jerry Huang
 */
public abstract class GameEvent {
    private List<GameObject> gameObjects;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/");

    public GameEvent() {
        gameObjects = new ArrayList<>();
    }

    public abstract void update();

    public void attach(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void detach(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        if (gameObjects.contains(gameObject))
            gameObjects.remove(gameObject);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }
}
