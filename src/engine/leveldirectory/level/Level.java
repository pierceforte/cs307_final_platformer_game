package engine.leveldirectory.level;


import java.util.ArrayList;
import java.util.List;

public class Level {

    // holds all the objects in the level
    private List<Object> objects; // needs to be updated to the type of the parent object
    private List<Object> interactableObjects;
    /*
    fields that might be added in the future

    private Background background;

    *** DOES NOT INCLUDE PLAYER'S CHARACTER ---> THIS CLASS ONLY LOADS THE DEFAULT LEVEL OBJECTS***
     */


    /**
     * Constructor for the basic level
     */
    public Level() {
        objects = new ArrayList<>();
        interactableObjects = new ArrayList<>();
    }

    public List<Object> getObjects() {
        return objects;
    }

    public List<Object> getInteractableObjects() {
        return interactableObjects;
    }

    /**
     * adds an immutable object to the level
     * @param o
     */
    public void addObject(Object o) {
        objects.add(o);
    }

    public void addObject(List<Object> listOfObjects) {
        objects.addAll(listOfObjects);
    }

    public void addInteractableObject(Object o) {
        interactableObjects.add(o);
    }

    public void addInteractableObject(List<Object> listofInteractableObjects) {
        interactableObjects.addAll(listofInteractableObjects);
    }

    /**
     * corresponding remove methods
     */
    public boolean removeObject(Object o) {
        return objects.remove(o);
    }

    public boolean removeObject(List<Object> o) {
        return objects.removeAll(o);
    }

    public boolean removeInteractableObject(Object o) {
        return interactableObjects.remove(o);
    }

    public boolean removeInteractableObject(List<Object> o) {
        return interactableObjects.removeAll(o);
    }


}
