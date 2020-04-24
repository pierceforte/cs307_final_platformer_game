package builder.stage;

import engine.view.DraggableGameObjectView;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pierce Forte
 */
public class ActionableGameObjectView extends DraggableGameObjectView {

    private boolean areActionIconsActive;
    private boolean hasNewActionItems;
    private boolean isActive;
    private List<Node> actionIcons;
    private ImageView leftIcon;
    private ImageView rightIcon;
    private ImageView moveIcon;
    private ImageView placeIcon;
    private ImageView sellIcon;

    public ActionableGameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        super(imgPath, xPos, yPos, width, height, xDirection);
        isActive = true;
        actionIcons = new ArrayList<>();
        createActionIcons();
        askUserToPlaceMe();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public ImageView getModifyIcon() {
        return leftIcon;
    }

    public ImageView getPlaceIcon() {
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

    public List<Node> getActionIcons() {
        return actionIcons;
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

    @Override
    public void disableDrag() {
        super.disableDrag();
        areActionIconsActive = false;
    }

    @Override
    protected void handleDraggableMousePress() {
        super.handleDraggableMousePress();
        areActionIconsActive = true;
    }

    @Override
    protected void handleDraggableMouseRelease() {
        super.handleDraggableMouseRelease();
        askUserToPlaceMe();
        areActionIconsActive = true;
    }

    @Override
    protected void handleStationaryMousePress() {
        super.handleStationaryMousePress();
        askUserToMoveMe();
    }

    private void recordNewActionIcons() {
        areActionIconsActive = true;
        hasNewActionItems = true;
    }

    private void createActionIcons() {
        placeIcon = new BuilderActionIcon(BuilderAction.PLACE, this);
        moveIcon = new BuilderActionIcon(BuilderAction.MOVE, this);
        sellIcon = new BuilderActionIcon(BuilderAction.SELL, this);
        actionIcons.addAll(List.of(placeIcon, moveIcon, sellIcon));
    }
}