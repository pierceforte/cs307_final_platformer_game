package builder.bank;

import data.ErrorLogger;
import engine.gameobject.GameObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 *
 *
 * @author Pierce Forte
 */
public class BankItem {

    private String imgPath;
    private int width;
    private int height;
    private int cost;
    private GameObject gameObject;

    public BankItem(GameObject gameObject, int width, int height, int cost) {
        setGameObject(gameObject);
        this.imgPath = gameObject.getImgPath();
        this.width = width;
        this.height = height;
        this.cost = cost;
    }

    public BankItem(BankItem item) {
        this(item.getGameObject(),
                item.getWidth(),
                item.getHeight(),
                item.getCost());
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public String getImgPath() {
        return imgPath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

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
