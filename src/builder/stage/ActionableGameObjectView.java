package builder.stage;

import engine.view.DraggableGameObjectView;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a child of the DraggableGameObjectView class, providing an ImageView that can be dragged and
 * interacted with through action icons.
 *
 * This class is dependent on the image path, position, dimensions, and direction of its associated backend
 * GameObject (although this GameObject is not directly related to/dependent on it).
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

    /**
     * The constructor to create an ActionableGameObjectView.
     * @param imgPath the image path
     * @param xPos the x position
     * @param yPos the y position
     * @param width the width
     * @param height the height
     * @param xDirection the x direction
     */
    public ActionableGameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        super(imgPath, xPos, yPos, width, height, xDirection);
        isActive = true;
        actionIcons = new ArrayList<>();
        createActionIcons();
        askUserToPlaceMe();
    }

    /**
     * Returns whether this ActionableGameObjectView is active, which is typically true while
     * it can be interacted with by the user.
     * @return whether this ActionableGameObjectView is active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets whether this ActionableGameObjectView is active, which is typically true while
     * it can be interacted with by the user.
     * @param isActive whether this ActionableGameObjectView is active
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the action icon associated with modifying the ActionableGameObjectView.
     * @return the action icon associated with modifying the ActionableGameObjectView
     */
    public ImageView getModifyIcon() {
        return leftIcon;
    }

    /**
     * Gets the action icon associated with placing the ActionableGameObjectView.
     * @return the action icon associated with placing the ActionableGameObjectView
     */
    public ImageView getPlaceIcon() {
        return rightIcon;
    }

    /**
     * Returns whether the action icons are active, which is typically true when they are
     * visible to the user and available for interaction.
     * @return whether the action icons are active
     */
    public boolean areActionIconsActive() {
        return areActionIconsActive;
    }

    /**
     * Returns whether the action icons are new, which is typically true when they have been
     * changed or presented for the first time.
     * @return whether the action icons are new
     */
    public boolean hasNewActionItems() {
        return hasNewActionItems;
    }

    /**
     * Set whether the action icons are new, which is typically true when they have been
     * changed or presented for the first time.
     * @param hasNewActionItems  whether the action icons are new
     */
    public void setHasNewActionItems(boolean hasNewActionItems) {
        this.hasNewActionItems = hasNewActionItems;
    }

    /**
     * Returns the action icons for this ActionableGameObjectView.
     * @return the action icons for this ActionableGameObjectView
     */
    public List<Node> getActionIcons() {
        return actionIcons;
    }

    /**
     * Sets the action icons to be the ones where the user must choose to place this ActionableGameObjectView.
     */
    public void askUserToPlaceMe() {
        rightIcon = placeIcon;
        leftIcon = sellIcon;
        recordNewActionIcons();
    }

    /**
     * Sets the action icons to be the ones where the user must choose to move this ActionableGameObjectView.
     */
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