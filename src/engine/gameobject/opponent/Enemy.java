package engine.gameobject.opponent;

import javafx.geometry.Point2D;

/**
 * This interface defines how a unique enemy will operate
 */
public interface Enemy {

    /**
     * Assigns how the enemy moves; eg. how do they attack the player?
     * @param targetPos where the target is located
     */
    void assignLogic(Point2D targetPos);

}