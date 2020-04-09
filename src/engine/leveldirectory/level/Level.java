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

    // holds all the objects in the level
    private List<GameObject> gameObjects; // needs to be updated to the type of the parent object
    private List<GameObject> interactableObjects;
    /*
    fields that might be added in the future

    private Background background;

    *** DOES NOT INCLUDE PLAYER'S CHARACTER ---> THIS CLASS ONLY LOADS THE DEFAULT LEVEL OBJECTS***
     */


    /**
     * Constructor for the basic level
     */
    public Level() {
        gameObjects = new ArrayList<>();
        interactableObjects = new ArrayList<>();
    }

    @Override
    public List<GameObject> getAllGameObjects() {
        return gameObjects;
    }

    public List<GameObject> getInteractableObjects() {
        return interactableObjects;
    }

    /**
     * adds an immutable object to the level
     * @param o
     */
    @Override
    public void addGameObject(GameObject o) {
        gameObjects.add(o);
    }

    public void addObject(List<GameObject> listOfObjects) {
        gameObjects.addAll(listOfObjects);
    }

    public void addInteractableObject(GameObject o) {
        interactableObjects.add(o);
    }

    public void addInteractableObject(List<GameObject> listofInteractableObjects) {
        interactableObjects.addAll(listofInteractableObjects);
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

    public boolean removeInteractableObject(GameObject o) {
        return interactableObjects.remove(o);
    }

    public boolean removeInteractableObject(List<GameObject> o) {
        return interactableObjects.removeAll(o);
    }

}
