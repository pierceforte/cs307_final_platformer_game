
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
    double getCollisionEffect(Object o1, Object o2);

    /**
     * This method checks if a user initiated event can happen or not
     * (e.g. it will check if a user can place an object in a location by looking if there is already something
     * in that location)
     * @param o1: first object
     * @param o2: second object
     * @return: true or false (true if it's a valid move, false otherwise)
     */
    boolean checkConstraints(Object o1, Object o2);


}
