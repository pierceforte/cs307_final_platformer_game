package engine.objectinteraction;

/**
 * This class stores the different types of collisions and their effects
 *
 * @author Jerry Huang
 */
public enum CollisionTypes {

    TEMP_COLLISION(new int[][]{{0},
            {0}});


    private final int[][] temp;

    CollisionTypes(int[][] temp) {
        this.temp = temp;
    }

    public int[][] getTemp() {
        return this.temp;
    }
}
