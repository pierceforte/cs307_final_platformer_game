package engine.gameobject.platform;

import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.player.Player;
import engine.gameobject.player.SimplePlayer;

/**
 * This interface defines how a platform will operate
 *
 * @author Pierce Forte
 */
public interface Platform {

    /**
     * Define how entities are affected when on top of platform
     * @param entity Entity that is on top of platform
     */
    void handleEntityInteraction(GameObject entity);

    /**
     * Define how players are affected when on top of platform
     * @param player Player that is on top of platform
     */
    void handlePlayerInteraction(SimplePlayer player);
}