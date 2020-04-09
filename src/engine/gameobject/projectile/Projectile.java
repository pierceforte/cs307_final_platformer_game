package engine.gameobject.projectile;

import engine.gameobject.GameObject;
import engine.gameobject.platform.Platform;

/**
 * This is an interface used to define how projectiles interact with other game objects.
 *
 * @author Pierce Forte
 */

public interface Projectile {
    /**
     * Define how entities are affected when touched by projectile
     * @param entity Entity that projectile is touching
     */
    void handleEntityInteraction(GameObject entity);

    /**
     * Define how projectile reacts when touching platform
     * @param platform Platform that projectile is touching
     */
    void handlePlatformInteraction(Platform platform);

}