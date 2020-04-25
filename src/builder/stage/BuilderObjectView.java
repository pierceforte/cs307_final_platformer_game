package builder.stage;

import builder.bank.BankItem;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;

/**
 * This class is used to create the frontend for build phase objects. These objects are GameObjectViews
 * that can be dragged around during the build phase. Beyond the build phase, they are added into game play
 * as GameObjectViews.
 *
 * @author Pierce Forte
 */
public class BuilderObjectView extends ActionableGameObjectView {

    public static final String ID = "builderObjectView";
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private GameObject gameObject;
    private BankItem bankItem;

    public BuilderObjectView(GameObject gameObject, BankItem bankItem, double xPos, double yPos) {
        super(gameObject.getImgPath(), xPos, yPos, bankItem.getWidth(), bankItem.getHeight(), GameObjectView.RIGHT);
        this.gameObject = gameObject;
        this.bankItem = bankItem;
        setPickOnBounds(true);
        setId(ID);
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public BankItem getBankItem() {
        return bankItem;
    }
}