
/**
 * This interface defines how a unique enemy will operate
 */
public interface Enemy {

    /**
     * Assigns how the enemy moves
     * @param targetPos where the target is located
     */
    void assignLogic(Point2D targetPos) {
        // how do they attack the player?
    }

}