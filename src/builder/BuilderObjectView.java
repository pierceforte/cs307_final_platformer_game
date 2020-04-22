package builder;

import builder.bank.BankItem;
import engine.gameobject.GameObject;
import engine.view.GameObjectView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create the frontend for build phase objects. These objects are GameObjectViews
 * that can be dragged around during the build phase. Beyond the build phase, they can simply act as
 * normal GameObjectViews by disabling the drag feature.
 *
 * @author Pierce Forte
 */
public class BuilderObjectView extends GameObjectView {

    public static final String PATH_TO_CHECK_IMG = "images/icons/check_icon.png";
    public static final String PATH_TO_MOVE_IMG = "images/icons/move_icon.png";
    public static final String PATH_TO_SELL_IMG = "images/icons/dollar_icon.png";
    public static final double ACTION_ICON_SIZE = 15;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private NodeDragger nodeDragger;
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
    private ImageView checkIcon;
    private ImageView sellIcon;

    public BuilderObjectView(GameObject gameObject, BankItem bankItem, double xPos, double yPos) {
        super(gameObject.getImgPath(), xPos, yPos, bankItem.getWidth(), bankItem.getHeight(), GameObjectView.RIGHT);
        this.gameObject = gameObject;
        this.bankItem = bankItem;
        initializeNodeDragger();
        enableDrag();
        isActive = true;
        isDraggable = true;
        isSnapped = false;
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

    public ImageView getLeftIcon() {
        return leftIcon;
    }

    public ImageView getRightIcon() {
        return rightIcon;
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
                System.out.println("Release");
            }
            @Override
            public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMouseDrag(mouseEvent, node);
                isReadyForSnap = false;
                isSnapped = false;
                System.out.println("Drag");
            }
            @Override
            public void handleStationaryMousePress(MouseEvent mouseEvent, Node node) {
                askUserToMoveMe();
            }
        };
    }

    private void askUserToPlaceMe() {
        rightIcon = checkIcon;
        leftIcon = sellIcon;

        checkIcon.setOnMouseClicked(mouseEvent -> disableDrag());
        sellIcon.setOnMouseClicked(mouseEvent -> isActive = false);
        areActionIconsActive = true;
        hasNewActionItems = true;
    }

    private void askUserToMoveMe() {
        leftIcon = moveIcon;
        rightIcon = checkIcon;

        moveIcon.setOnMouseClicked(mouseEvent -> {
                enableDrag();
                askUserToPlaceMe();
        });
        checkIcon.setOnMouseClicked(mouseEvent -> disableDrag());
        areActionIconsActive = true;
        hasNewActionItems = true;
    }

    private void createActionIcons() {
        checkIcon = createActionIcon(PATH_TO_CHECK_IMG,  "checkIcon");
        sellIcon = createActionIcon(PATH_TO_SELL_IMG, "sellIcon");
        moveIcon = createActionIcon(PATH_TO_MOVE_IMG, "moveIcon");
        actionIcons.addAll(List.of(checkIcon, sellIcon, moveIcon));
    }

    private ImageView createActionIcon(String imgPath, String idPrefix) {
        Image img = makeImage(imgPath);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(ACTION_ICON_SIZE);
        imgView.setFitHeight(ACTION_ICON_SIZE);
        imgView.setPickOnBounds(true);
        imgView.setId(idPrefix + this.hashCode());
        actionIcons.add(imgView);
        return imgView;
    }
}
