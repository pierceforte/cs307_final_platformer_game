package engine.leveldirectory.level;

import builder.bank.BankController;
import builder.bank.BankItem;
import builder.stage.PaneDimensions;
import engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class defines the basic level object. It contains the mutable and immutable objects within a level and
 * supports add and remove methods.
 *
 * @author Jerry Huang
 */
public class Level implements LevelInterface {

    /**
     * holds all the objects in the level
     */
    private List<GameObject> gameObjects;
    private List<BankItem> bankItems;
    private PaneDimensions dimensions;

    public Level(List<GameObject> gameObjects, List<BankItem> bankItems, PaneDimensions dimensions) {
        this.gameObjects = gameObjects;
        this.bankItems = bankItems;
        this.dimensions = dimensions;
    }

    @Override
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    @Override
    public List<BankItem> getBankItems() {
        return bankItems;
    }

    @Override
    public PaneDimensions getDimensions() {
        return dimensions;
    }

    /**
     * adds an immutable object to the level
     * @param o
     */
    @Override
    public void addGameObject(GameObject o) {
        gameObjects.add(o);
    }

    public void addGameObject(List<GameObject> listOfObjects) {
        gameObjects.addAll(listOfObjects);
    }

    public List<GameObject> getAllGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * corresponding remove methods
     */
    public boolean removeObject(GameObject o) {
        return gameObjects.remove(o);
    }

    public boolean removeObject(List<GameObject> o) {
        return gameObjects.removeAll(o);
    }
}
