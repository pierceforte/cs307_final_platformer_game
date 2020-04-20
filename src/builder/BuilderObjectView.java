package builder;

import engine.gameobject.GameObject;
import engine.view.GameObjectView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create the frontend for build phase objects. These objects are GameObjectViews
 * that can be dragged around during the build phase. Beyond the build phase, they can simply act as
 * normal GameObjectViews by disabling the drag feature.
 *
 * The code used for the enableDrag method was modified from StackOverflow at
 * https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687
 *
 * @author Pierce Forte
 */
public class BuilderObjectView extends GameObjectView {

    public static final String PATH_TO_CHECK_IMG = "check_icon.png";
    public static final String PATH_TO_MOVE_IMG = "move_icon.png";
    public static final String PATH_TO_SELL_IMG = "dollar_icon.png";
    public static final double ACTION_ICON_SIZE = 15;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private boolean isSnapped;
    private boolean isReadyForSnap;
    private boolean isDraggable;
    private boolean isActive;
    private boolean areActionIconsActive;
    private List<Node> actionIcons;
    private Pane root;
    private GameObject gameObject;
    private BankItem bankItem;

    public BuilderObjectView(GameObject gameObject, BankItem bankItem, double xPos, double yPos, Pane root) {
        super(gameObject.getImgPath(), xPos, yPos, bankItem.getWidth(), bankItem.getHeight(), GameObjectView.RIGHT);
        this.gameObject = gameObject;
        this.bankItem = bankItem;
        this.root = root; // TODO: maybe eliminate this dependency
        enableDrag();
        isActive = true;
        isDraggable = true;
        isSnapped = false;
        actionIcons = new ArrayList<>();
        askUserToPlaceMe();
        setId("builderObjectView");
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public BankItem getBankItem() {
        return bankItem;
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

    public void setSnapped() {
        isSnapped = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean areActionIconsActive() {
        return areActionIconsActive;
    }

    public List<Node> getActionIcons() {
        return actionIcons;
    }

    //TODO: maybe replace this
    static class Delta { double x, y; }
    /**
     * Enable the drag functionality (this method is intended to be used when the build phase begins)
     */
    public void enableDrag() {
        isDraggable = true;
        final Delta dragDelta = new Delta();
        setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = this.getX() - mouseEvent.getX();
            dragDelta.y = this.getY() - mouseEvent.getY();
            this.getScene().setCursor(Cursor.MOVE);
            deactivateActionIcons();
        });
        setOnMouseReleased(mouseEvent -> {
            this.getScene().setCursor(Cursor.HAND);
            askUserToPlaceMe();
            isReadyForSnap = true;
        });
        setOnMouseDragged(mouseEvent -> {
            setX(mouseEvent.getX() + dragDelta.x);
            setY(mouseEvent.getY() + dragDelta.y);
            isReadyForSnap = false;
            isSnapped = false;
        });
        setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    /**
     * Disable the drag functionality (this method is intended to be used after the build phase when
     * build objects are no longer movable)
     */
    public void disableDrag() {
        isDraggable = false;
        deactivateActionIcons();
        setOnMousePressed(mouseEvent -> askUserToMoveMe());
        setOnMouseReleased(mouseEvent -> {return;});
        setOnMouseDragged(mouseEvent -> {return;});
        setOnMouseEntered(mouseEvent -> {return;});
        setOnMouseExited(mouseEvent -> {return;});
    }

    private void askUserToPlaceMe() {
        ImageView check = createActionIcon(PATH_TO_CHECK_IMG, RIGHT);
        ImageView sell = createActionIcon(PATH_TO_SELL_IMG, LEFT);

        check.setOnMouseClicked(mouseEvent -> disableDrag());
        sell.setOnMouseClicked(mouseEvent -> {
            isActive = false;
            deactivateActionIcons();
        });
        activateActionIcons();
    }

    private void askUserToMoveMe() {
        ImageView move = createActionIcon(PATH_TO_MOVE_IMG, LEFT);
        ImageView check = createActionIcon(PATH_TO_CHECK_IMG, RIGHT);

        move.setOnMouseClicked(mouseEvent -> {
                enableDrag();
                deactivateActionIcons();
                askUserToPlaceMe();

        });
        check.setOnMouseClicked(mouseEvent -> disableDrag());
        activateActionIcons();
    }

    private ImageView createActionIcon(String imgPath, int side) {
        Image img = makeImage(imgPath);
        ImageView imgView = new ImageView(img);
        imgView.setX(this.getCenterX() + ACTION_ICON_SIZE/1.5 * side);
        imgView.setY(this.getCenterY() + ACTION_ICON_SIZE * 2);
        imgView.setFitWidth(ACTION_ICON_SIZE);
        imgView.setFitHeight(ACTION_ICON_SIZE);
        imgView.setPickOnBounds(true);
        actionIcons.add(imgView);
        root.getChildren().add(imgView);
        return imgView;
    }

    private void activateActionIcons() {
        areActionIconsActive = true;
    }

    private void deactivateActionIcons() {
        root.getChildren().removeAll(actionIcons);
        actionIcons.clear();
        areActionIconsActive = false;
    }
}
