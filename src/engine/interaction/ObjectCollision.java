package engine.interaction;

import static engine.interaction.CollisionTypes.TEMP_COLLISION;

/**
 * This class returns the resulting physics when given two objects that have collided in the game
 *
 * @author Jerry Huang
 */
public class ObjectCollision implements Interactions {

    private Object firstObject;
    private Object secondObject;

    boolean flag;

    /**
     * Standard Constructor
     * @param one: first object
     * @param two: second object
     */
    public ObjectCollision(Object one, Object two) {
        firstObject = one;
        secondObject = two;
        flag = false;
    }

    public Object getFirstObject() {
        return firstObject;
    }

    public Object getSecondEntity() {
        return secondObject;
    }

    /**
     * looks up the effect of the collision from the CollisionTypes enum class
     * @param o1: first object
     * @param o2: second object
     * @return
     */
    @Override
    public double getCollisionEffect(Object o1, Object o2) {

        int[][] tempArray = TEMP_COLLISION.getTemp(); // finish after objects are created

        return 0;
    }

    @Override
    public boolean checkOverlap() {
        return false;
    }
}
