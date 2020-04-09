package engine.gameobject.platform;

import engine.gameobject.GameObject;

/**
 * This interface defines how a platform will operate
 */
public interface Platform {

    /**
     * Define how platform moves
     */
    void move(int direction);

    /**
     * Define how entities are affected when on top of platform
     * @param entity Entity that is on top of platform
     */
    void handleEntityInteraction(GameObject entity);
}