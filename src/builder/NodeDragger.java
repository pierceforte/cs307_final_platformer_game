package builder;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The code used for this class was inspired from StackOverflow at
 * https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687.
 * I modified this code and improved it significantly by creating this class, which can be used to provide
 * a draggable interface for any node. Further, methods can be overridden by classes that use a NodeDragger
 * through composition, which makes this code significantly more flexible.
 *
 * @author Pierce Forte
 */
public class NodeDragger {

    static class Delta { double x, y; }
    private Delta dragDelta;

    /**
     * The constructor to create a NodeDragger.
     */
    public NodeDragger() {
        dragDelta = new Delta();
    }

    /**
     * Returns the change in x position upon drag.
     * @return the change in x position upon drag
     */
    public double getDeltaX() {
        return dragDelta.x;
    }

    /**
     * Returns the change in y position upon drag.
     * @return the change in y position upon drag
     */
    public double getDeltaY() {
        return dragDelta.y;
    }

    /**
     * Enables the draggable functionality for a node.
     * @param node the node to be given the draggable functionality
     */
    public void enableDrag(Node node) {
        dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> handleDraggableMousePress(mouseEvent, node));
        node.setOnMouseReleased(mouseEvent -> handleDraggableMouseRelease(mouseEvent, node));
        node.setOnMouseDragged(mouseEvent -> handleDraggableMouseDrag(mouseEvent, node));
        node.setOnMouseEntered(mouseEvent -> handleDraggableMouseEnter(mouseEvent, node));
        node.setOnMouseExited(mouseEvent -> handleDraggableMouseExit(mouseEvent, node));
    }

    /**
     * Disables the draggable functionality for a node.
     * @param node the node to have its draggable functionality removed
     */
    public void disableDrag(Node node) {
        node.setOnMousePressed(mouseEvent -> handleStationaryMousePress(mouseEvent, node));
        node.setOnMouseReleased(mouseEvent -> handleStationaryMouseRelease(mouseEvent, node));
        node.setOnMouseDragged(mouseEvent -> handleStationaryMouseDrag(mouseEvent, node));
        node.setOnMouseEntered(mouseEvent -> handleStationaryMouseEnter(mouseEvent, node));
        node.setOnMouseExited(mouseEvent -> handleStationaryMouseExit(mouseEvent, node));
    }

    /**
     * Handles the mouse press on a node that is draggable.
     * @param mouseEvent the mouse event
     * @param node the draggable node
     */
    public void handleDraggableMousePress(MouseEvent mouseEvent, Node node) {
        // our Game handles ImageView positions with absolute X, not translate X (as is used for panes and such)
        if (node instanceof ImageView) {
            dragDelta.x = ((ImageView) node).getX() - mouseEvent.getX();
            dragDelta.y = ((ImageView) node).getY() - mouseEvent.getY();
        }
        else {
            dragDelta.x = node.getTranslateX() - mouseEvent.getX();
            dragDelta.y = node.getTranslateY() - mouseEvent.getY();
        }
        node.getScene().setCursor(Cursor.MOVE);
    }

    /**
     * Handles the mouse release on a node that is draggable.
     * @param mouseEvent the mouse event
     * @param node the draggable node
     */
    public void handleDraggableMouseRelease(MouseEvent mouseEvent, Node node) {
        node.getScene().setCursor(Cursor.HAND);
    }

    /**
     * Handles the mouse drag on a node that is draggable.
     * @param mouseEvent the mouse event
     * @param node the draggable node
     */
    public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
        double newX = mouseEvent.getX() + dragDelta.x;
        double newY = mouseEvent.getY() + dragDelta.y;
        // our Game handles ImageView positions with absolute X, not translate X (as is used for panes and such)
        if (node instanceof ImageView) {
            ((ImageView) node).setX(newX);
            ((ImageView) node).setY(newY);
        }
        else {
            node.setTranslateX(newX);
            node.setTranslateY(newY);
        }
    }

    /**
     * Handles the mouse enter on a node that is draggable.
     * @param mouseEvent the mouse event
     * @param node the draggable node
     */
    public void handleDraggableMouseEnter(MouseEvent mouseEvent, Node node) {
        if (!mouseEvent.isPrimaryButtonDown()) {
            node.getScene().setCursor(Cursor.HAND);
        }
    }

    /**
     * Handles the mouse exit on a node that is draggable.
     * @param mouseEvent the mouse event
     * @param node the draggable node
     */
    public void handleDraggableMouseExit(MouseEvent mouseEvent, Node node) {
        if (!mouseEvent.isPrimaryButtonDown()) {
            node.getScene().setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Handles the mouse press on a node that is non-draggable.
     * @param mouseEvent the mouse event
     * @param node the non-draggable node
     */
    public void handleStationaryMousePress(MouseEvent mouseEvent, Node node) {
        return;
    }

    /**
     * Handles the mouse release on a node that is non-draggable.
     * @param mouseEvent the mouse event
     * @param node the non-draggable node
     */
    public void handleStationaryMouseRelease(MouseEvent mouseEvent, Node node) {
        return;
    }

    /**
     * Handles the mouse drag on a node that is non-draggable.
     * @param mouseEvent the mouse event
     * @param node the non-draggable node
     */
    public void handleStationaryMouseDrag(MouseEvent mouseEvent, Node node) {
        return;
    }

    /**
     * Handles the mouse enter on a node that is non-draggable.
     * @param mouseEvent the mouse event
     * @param node the non-draggable node
     */
    public void handleStationaryMouseEnter(MouseEvent mouseEvent, Node node) {
        return;
    }

    /**
     * Handles the mouse exit on a node that is non-draggable.
     * @param mouseEvent the mouse event
     * @param node the non-draggable node
     */
    public void handleStationaryMouseExit(MouseEvent mouseEvent, Node node) {
        return;
    }
}