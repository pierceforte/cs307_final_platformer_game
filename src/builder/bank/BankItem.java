package builder.bank;

import data.ErrorLogger;
import engine.gameobject.GameObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * This class is used to create a item for the bank feature of the builder stage.
 *
 * BankItems assume that they will be given an associated GameObject. They require this GameObject because when the item has
 * been purchased, placed somewhere on the builder stage by the user, and added to the level itself, the level must have a way to
 * know what this item actually is and the rules that govern its actions in the game.
 *
 * @author Pierce Forte
 */
public class BankItem {

    private String imgPath;
    private int width;
    private int height;
    private int cost;
    private GameObject gameObject;

    /**
     * The constructor to create a BankItem.
     * @param gameObject the associated GameObject for the bank item
     * @param width the width of the bank item
     * @param height the height of the bank item
     * @param cost the cost of the bank item
     */
    public BankItem(GameObject gameObject, int width, int height, int cost) {
        setGameObject(gameObject);
        this.imgPath = gameObject.getImgPath();
        this.width = width;
        this.height = height;
        this.cost = cost;
    }

    /**
     * The constructor to create a BankItem based on an existing BankItem object.
     * @param item an existing BankItem object to be cloned
     **/
    public BankItem(BankItem item) {
        this(item.getGameObject(),
                item.getWidth(),
                item.getHeight(),
                item.getCost());
    }

    /**
     * Used to obtain the associated GameObject for this BankItem. This method is typically used when transitioning from
     * the builder stage to the game play stage, which is when the BankItem's must actually be given rules and actions.
     * @return the associated GameObject for this BankItem
     */
    public GameObject getGameObject() {
        return gameObject;
    }

    /**
     * Used to access the associated image path for the BankItems's image.
     * @return The image path for the button
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Gets the width of the BankItem.
     * @return the width of the BankItem
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the BankItem.
     * @return the height of the BankItem
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the cost of the BankItem.
     * @return the cost of the BankItem
     */
    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankItem bankItem = (BankItem) o;
        return width == bankItem.width &&
                height == bankItem.height &&
                cost == bankItem.cost &&
                imgPath.equals(bankItem.imgPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgPath, width, height, cost);
    }

    private void setGameObject(GameObject gameObject) {
        try {
            this.gameObject = createNewGameObjectInstance(gameObject);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            ErrorLogger.log(e);
        }
    }
    private GameObject createNewGameObjectInstance(GameObject gameObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = gameObject.getClass();
        return (GameObject) clazz.getDeclaredConstructor(clazz).newInstance(gameObject);
    }

}
