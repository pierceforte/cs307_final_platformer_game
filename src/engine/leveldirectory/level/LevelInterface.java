package engine.leveldirectory.level;

import engine.gameobject.GameObject;

import java.util.Collection;

/**
 * general interface for all levels in the game
 *
 * @author Jerry Huang
 */
public interface LevelInterface {

    /**
     * used for saving & transferring data
     * @return all objects in a level
     */
    Collection<GameObject> getAllGameObjects();

    /**
     * adds a GameObject to the level object
     * @param g: GameObject to be added
     */
    void addGameObject(GameObject g);
}
