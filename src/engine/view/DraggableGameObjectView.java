package engine.view;

import builder.NodeDragger;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This class is a child of the GameObjectView class, providing an ImageView that can be dragged. This drag
 * feature is maintained through composition with a NodeDragger object.
 *
 * This class is dependent on the image path, position, dimensions, and direction of its associated backend
 * GameObject (although this GameObject is not directly related to/dependent on it).
 *
 * @author Pierce Forte
 */
public class DraggableGameObjectView extends GameObjectView {

    private NodeDragger nodeDragger;
    private boolean isSnapped;
    private boolean isReadyForSnap;
    private boolean isDraggable;
    private boolean isOverlapped;

    /**
     * The constructor to create a DraggableGameObjectView.
     * @param imgPath the image path
     * @param xPos the x position
     * @param yPos the y position
     * @param width the width
     * @param height the height
     * @param xDirection the x direction
     */
    public DraggableGameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        super(imgPath, xPos, yPos, width, height, xDirection);
        isDraggable = true;
        isReadyForSnap = true;
        initializeNodeDragger();
        enableDrag();
    }

    /**
     * Returns whether this ImageView is currently overlapping another.
     * @return whether this ImageView is overlapped
     */
    public boolean isOverlapped() {
        return isOverlapped;
    }

    /**
     * Sets whether this ImageView is currently overlapping another.
     * @param isOverlapped whether this ImageView is overlapped
     */
    public void setIsOverlapped(boolean isOverlapped) {
        this.isOverlapped = isOverlapped;
    }

    /**
     * Returns whether the DraggableGameObjectView is currently draggable.
     * @return whether the DraggableGameObjectView is currently draggable
     */
    public boolean isDraggable() {
        return isDraggable;
    }

    /**
     * Returns whether the DraggableGameObjectView is ready to be snapped to a grid, typically
     * true when it is not being interacted with but has not yet been snapped.
     * @return whether the DraggableGameObjectView is ready to be snapped to a grid
     */
    public boolean isReadyForSnap() {
        return isReadyForSnap;
    }

    /**
     * Returns whether the DraggableGameObjectView is snapped to a grid.
     * @return whether the DraggableGameObjectView is snapped to a grid
     */
    public boolean isSnapped() {
        return isSnapped;
    }

    /**
     * Sets whether the DraggableGameObjectView is snapped to a grid.
     * @param isSnapped whether the DraggableGameObjectView is snapped to a grid
     */
    public void setIsSnapped(boolean isSnapped) {
        this.isSnapped = isSnapped;
    }

    /**
     * Enables the draggable feature.
     */
    public void enableDrag() {
        isDraggable = true;
        nodeDragger.enableDrag(this);
    }

    /**
     * Disables the draggable feature, effectively causing the DraggableGameObjectView to function entirely like
     * its superclass, a GameObjectView.
     */
    public void disableDrag() {
        isDraggable = false;
        nodeDragger.disableDrag(this);
    }

    protected void initializeNodeDragger() {
        nodeDragger = new NodeDragger(){
            @Override
            public void handleDraggableMousePress(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMousePress(mouseEvent, node);
                DraggableGameObjectView.this.handleDraggableMousePress();
            }
            @Override
            public void handleDraggableMouseRelease(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMouseRelease(mouseEvent, node);
                DraggableGameObjectView.this.handleDraggableMouseRelease();
            }
            @Override
            public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
                super.handleDraggableMouseDrag(mouseEvent, node);
                DraggableGameObjectView.this.handleDraggableMouseDrag();
                DraggableGameObjectView.this.toFront();
            }
            @Override
            public void handleStationaryMousePress(MouseEvent mouseEvent, Node node) {
                DraggableGameObjectView.this.handleStationaryMousePress();
            }
        };
    }

    protected void handleDraggableMousePress() {
        return;
    }

    protected void handleDraggableMouseRelease() {
        isReadyForSnap = true;
    }

    protected void handleDraggableMouseDrag() {
        isReadyForSnap = false;
        isSnapped = false;
    }

    protected void handleStationaryMousePress() {
        return;
    }
}