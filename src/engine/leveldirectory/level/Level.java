package engine.leveldirectory.level;

import engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the basic level object. It contains the mutable and immutable objects within a level and
 * supports add and remove methods.
 *
 * @author Jerry Huang
 */
public class Level implements LevelInterface {

    /**
     * holds all the objects in the level
     */
    private List<GameObject> gameObjects;

    public Level(List<GameObject> objects) {
        gameObjects = objects;
    }

    @Override
    public List<GameObject> getAllGameObjects() {
        return gameObjects;
    }

    /**
     * adds an immutable object to the level
     * @param o
     */
    @Override
    public void addGameObject(GameObject o) {
        gameObjects.add(o);
    }

    public void addGameObject(List<GameObject> listOfObjects) {
        gameObjects.addAll(listOfObjects);
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * corresponding remove methods
     */
    public boolean removeObject(GameObject o) {
        return gameObjects.remove(o);
    }

    public boolean removeObject(List<GameObject> o) {
        return gameObjects.removeAll(o);
    }
}
