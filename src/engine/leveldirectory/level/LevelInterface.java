package engine.leveldirectory.level;

import builder.bank.BankController;
import builder.bank.BankItem;
import builder.stage.PaneDimensions;
import engine.gameobject.GameObject;

import java.util.Collection;

/**
 * general interface for all levels in the game
 *
 * @author Jerry Huang, Pierce Forte
 */
public interface LevelInterface {

    /**
     * used for saving & transferring data
     * @return all objects in a level
     */
    Collection<GameObject> getGameObjects();

    /**
     * used for handling user purchases during builder stage
     * @return bank item for the level
     */
    BankController getBankController();

    /**
     * defines how to set dimensions
     * @return dimensions for a level
     */
    PaneDimensions getDimensions();

    /**
     * adds a GameObject to the level object
     * @param g: GameObject to be added
     */
    void addGameObject(GameObject g);
}
