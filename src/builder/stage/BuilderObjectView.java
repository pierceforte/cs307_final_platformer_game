package builder.stage;

import builder.NodeDragger;
import builder.bank.BankItem;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create the frontend for build phase objects. These objects are GameObjectViews
 * that can be dragged around during the build phase. Beyond the build phase, they are added into game play
 * as GameObjectViews.
 *
 * @author Pierce Forte
 */
public class BuilderObjectView extends GameObjectView {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private NodeDragger nodeDragger;
    private boolean isOverlapped;
    private boolean isSnapped;
    private boolean isReadyForSnap;
    private boolean isDraggable;
    private boolean isActive;
    private boolean areActionIconsActive;
    private boolean hasNewActionItems;
    private List<Node> actionIcons;
    private GameObject gameObject;
    private BankItem bankItem;
    private ImageView leftIcon;
    private ImageView rightIcon;
    private ImageView moveIcon;
    private ImageView placeIcon;
    private ImageView sellIcon;

    public BuilderObjectView(GameObject gameObject, BankItem bankItem, double xPos, double yPos) {
        super(gameObject.getImgPath(), xPos, yPos, bankItem.getWidth(), bankItem.getHeight(), GameObjectView.RIGHT);
        this.gameObject = gameObject;
        this.bankItem = bankItem;
        initializeNodeDragger();
        enableDrag();
        isActive = true;
        isDraggable = true;
        isReadyForSnap = true;
        actionIcons = new ArrayList<>();
        createActionIcons();
        askUserToPlaceMe();
        setId("builderObjectView");
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public BankItem getBankItem() {
        return bankItem;
    }

    public ImageView getModifyIcon() {
        return leftIcon;
    }

    public ImageView getPlaceIcon() {
        return rightIcon;
    }

    public boolean isOverlapped() {
        return isOverlapped;
    }

    public void setIsOverlapped(boolean isOverlapped) {
        this.isOverlapped = isOverlapped;
    }

    public boolean areActionIconsActive() {
        return areActionIconsActive;
    }

    public boolean hasNewActionItems() {
        return hasNewActionItems;
    }

    public void setHasNewActionItems(boolean hasNewActionItems) {
        this.hasNewActionItems = hasNewActionItems;
    }

    public boolean isDraggable() {
        return isDraggable;
    }

    public boolean isReadyForSnap() {
        return isReadyForSnap;
    }

    public boolean isSnapped() {
        return isSnapped;
    }

    public void setIsSnapped(boolean isSnapped) {
        this.isSnapped = isSnapped;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Node> getActionIcons() {
        return actionIcons;
    }

    public void enableDrag() {
        isDraggable = true;
        nodeDragger.enableDrag(this);
    }

    public void disableDrag() {
        isDraggable = false;
        areActionIconsActive = false;
        nodeDragger.disableDrag(this);
    }

    public void askUserToPlaceMe() {
        rightIcon = placeIcon;
        leftIcon = sellIcon;
        recordNewActionIcons();
    }

    public void askUserToMoveMe() {
        leftIcon = moveIcon;
        rightIcon = placeIcon;
        recordNewActionIcons();
    }

    private void recordNewActionIcons() {
        areActionIconsActive = true;
        hasNewActionItems = true;
    }

    private void initializeNodeDragger() {
        nodeDragger = new NodeDragger(){
            @Override
            public void handleDraggableMousePress(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMousePress(mouseEvent, node);
                areActionIconsActive = true;
            }
            @Override
            public void handleDraggableMouseRelease(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMouseRelease(mouseEvent, node);
                askUserToPlaceMe();
                areActionIconsActive = true;
                isReadyForSnap = true;
            }
            @Override
            public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMouseDrag(mouseEvent, node);
                isReadyForSnap = false;
                isSnapped = false;
            }
            @Override
            public void handleStationaryMousePress(MouseEvent mouseEvent, Node node) {
                askUserToMoveMe();
            }
        };
    }

    private void createActionIcons() {
        placeIcon = new BuilderActionIcon(BuilderAction.PLACE, this);
        moveIcon = new BuilderActionIcon(BuilderAction.MOVE, this);
        sellIcon = new BuilderActionIcon(BuilderAction.SELL, this);
        actionIcons.addAll(List.of(placeIcon, moveIcon, sellIcon));
    }

}
