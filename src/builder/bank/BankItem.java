package builder.bank;
import data.ErrorLogger;
import engine.gameobject.GameObject;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
/**
 * This class is used to create an item for the bank feature of the builder stage.
 *
 * This class is well designed due to its simplicity and encapsulation. It provides clear, specific functionality through its
 * public methods and offers a way to check for equality. Perhaps most importantly, however, it utilizes reflection effectively:
 * any backend GameObject in the game can be represented and instantiated by a BankItem.
 *
 * BankItems assume that they will be given an associated GameObject. They require this GameObject because when the item has
 * been purchased, placed somewhere on the builder stage by the user, and added to the level itself, the level must have a way to
 * know what this item actually is and the rules that govern its actions in the game. Further, reflection is used to create a new
 * GameObject for each BankItem because in the bank, a single BankItem is stored with an associated quantity: if we do not create a
 * new instance of its GameObject when we create a new BankItem, each of these equal (but difference instance) BankItems will have a
 * reference to the same GameObject.
 *
 * NOTE: The only refactoring done in this class after the deadline was to eliminate the "width" and "height" instance variables.
 * Because a BankItem's associated GameObject already records these values, having them recorded again here created the potential
 * for inconsistencies that could cause errors.
 * NOTE: This class is related to the other masterpiece class (BankModel) because BankItems are part of the BankModel's data structure.
 *
 * @author Pierce Forte
 */
public class BankItem {
    private String imgPath;
    private int cost;
    private GameObject gameObject;

    /**
     * The constructor to create a BankItem.
     * @param gameObject the associated GameObject for the bank item
     * @param cost the cost of the bank item
     */
    public BankItem(GameObject gameObject, int cost) {
        setGameObject(gameObject);
        this.imgPath = gameObject.getImgPath();
        this.cost = cost;
    }

    /**
     * The constructor to create a BankItem based on an existing BankItem object.
     * @param item an existing BankItem object to be cloned
     **/
    public BankItem(BankItem item) {
        this(item.getGameObject(), item.getCost());
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
    public double getWidth() {
        return gameObject.getWidth();
    }

    /**
     * Gets the height of the BankItem.
     * @return the height of the BankItem
     */
    public double getHeight() {
        return gameObject.getHeight();
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
        return gameObject.getWidth() == bankItem.getWidth() && gameObject.getHeight() == bankItem.getHeight() &&
                cost == bankItem.cost && imgPath.equals(bankItem.imgPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgPath, gameObject.getWidth(), gameObject.getHeight(), cost);
    }

    // sets the GameObject associated with this BankItem by attempting to create a new instance
    private void setGameObject(GameObject gameObject) {
        try {
            this.gameObject = createNewGameObjectInstance(gameObject);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            ErrorLogger.log(e);
        }
    }

    // creates a new instance of a GameObject
    private GameObject createNewGameObjectInstance(GameObject gameObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = gameObject.getClass();
        return (GameObject) clazz.getDeclaredConstructor(clazz).newInstance(gameObject);
    }
}