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
     * used for retrieving builder stage bank items
     * @return bank items
     */
    Collection<BankItem> getBankItems();

    /**
     * used for retrieving a level's dimensions
     * @return dimensions
     */
    PaneDimensions getDimensions();

    /**
     * adds a GameObject to the level object
     * @param g: GameObject to be added
     */
    void addGameObject(GameObject g);
}
