package engine.leveldirectory.level;

import builder.bank.BankController;
import builder.stage.TilePaneDimensions;
import engine.gameobject.GameObject;

import java.util.Collection;

/**
 * This is a general interface that requires the classes that implement it to define how to access and return
 * necessary game running elements.
 *
 * @author Jerry Huang
 * @author Pierce Forte
 */
public interface LevelInterface {

    /**
     * Used for saving & transferring data
     * @return all objects in a level
     */
    Collection<GameObject> getGameObjects();

    /**
     * Used for retrieving builder stage bank controller
     * @return bank controller
     */
    BankController getBankController();

    /**
     * Used for retrieving a level's dimensions
     * @return dimensions
     */
    TilePaneDimensions getDimensions();

    /**
     * Adds a GameObject to the level object
     * @param g GameObject to be added
     */
    void addGameObject(GameObject g);
}
