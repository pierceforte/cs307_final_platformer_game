package builder.stage;

import builder.bank.BankItem;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;

/**
 * This class is used to create the frontend for builder stage objects. These objects are GameObjectViews
 * that can be dragged around and acted upon with action icons during the builder stage. Beyond this builder stage,
 * they are added into game play as GameObjects.
 *
 * This class inherits the ActionableGameObjectView class. As part of this inheritance hierarchy, this class is an
 * ImagView that has overlap detection and grid-based attributes, is draggable, and can be interacted with through action
 * icons.
 *
 * This class is dependent on two things. The first being its GameObject, which defines the backend functionality associated
 * with it, and the second being its BankItem, which defines how bank recognizes it and how it is handled upon purchase/sale.
 *
 * @author Pierce Forte
 */
public class BuilderObjectView extends ActionableGameObjectView {

    public static final String ID = "builderObjectView";
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private GameObject gameObject;
    private BankItem bankItem;

    /**
     * The constructor to create a BuilderObjectView.
     * @param gameObject the backend GameObject associated with this BuilderObjectView
     * @param bankItem the BankItem associated with this BuilderObjectView
     * @param xPos the initial x position of this BuilderObjectView
     * @param yPos the initial y position of this BuilderObjectView
     */
    public BuilderObjectView(GameObject gameObject, BankItem bankItem, double xPos, double yPos) {
        super(gameObject.getImgPath(), xPos, yPos, bankItem.getWidth(), bankItem.getHeight(), GameObjectView.RIGHT);
        this.gameObject = gameObject;
        this.bankItem = bankItem;
        setPickOnBounds(true);
        setId(ID + bankItem.getImgPath().split("\\.")[0]); // tests would not recognize node ids with a "."
    }

    /**
     * Returns the backend GameObject associated with this BuilderObjectView.
     * @return the backend GameObject associated with this BuilderObjectView
     */
    public GameObject getGameObject() {
        return gameObject;
    }

    /**
     * Returns the BankItem associated with this BuilderObjectView.
     * @return the BankItem associated with this BuilderObjectView
     */
    public BankItem getBankItem() {
        return bankItem;
    }
}