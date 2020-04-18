package engine.gameobject.opponent;

import engine.gameobject.GameObject;

/**
 * This interface defines how a unique enemy will operate
 */
public interface Enemy {

    /**
     * Assigns how the enemy moves; eg. how do they attack the player?
     * @param target the target is located
     */
    void updateLogic(GameObject target);

}