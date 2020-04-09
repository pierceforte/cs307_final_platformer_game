package engine.objectinteraction;

import engine.gameobject.GameObject;

/**
 * This interface will define how we will handle interactions, rules, and events
 */
public interface Interactions {

    /**
     * This method describes the result of a collision between two objects
     * @param o1: first object
     * @param o2: second object
     * @return: the method returns a double which will correspond to a specific interaction we define beforehand
     * (e.g. 1 might mean the two objects bounce off of one another)
     */
    double getCollisionEffect(GameObject o1, GameObject o2);

    /**
     * This method checks if the objects are actually overlapping
     * (e.g. it will check if a user can place an object in a location by looking if there is already something
     * in that location)
     * @return: true or false (true if the objects overlap, false otherwise)
     */
    boolean checkOverlap();


}
