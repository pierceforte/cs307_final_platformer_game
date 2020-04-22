package builder;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * The code used for this class was inspired from StackOverflow at
 * https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687
 *
 * @author Pierce Forte
 */
public class NodeDragger {

    static class Delta { double x, y; }
    private Delta dragDelta;


    public NodeDragger() {
        dragDelta = new Delta();
    }

    public double getDeltaX() {
        return dragDelta.x;
    }

    public double getDeltaY() {
        return dragDelta.y;
    }

    public void enableDrag(Node node) {
        dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> handleDraggableMousePress(mouseEvent, node));
        node.setOnMouseReleased(mouseEvent -> handleDraggableMouseRelease(mouseEvent, node));
        node.setOnMouseDragged(mouseEvent -> handleDraggableMouseDrag(mouseEvent, node));
        node.setOnMouseEntered(mouseEvent -> handleDraggableMouseEnter(mouseEvent, node));
        node.setOnMouseExited(mouseEvent -> handleDraggableMouseExit(mouseEvent, node));
    }

    public void disableDrag(Node node) {
        node.setOnMousePressed(mouseEvent -> handleStationaryMousePress(mouseEvent, node));
        node.setOnMouseReleased(mouseEvent -> handleStationaryMouseRelease(mouseEvent, node));
        node.setOnMouseDragged(mouseEvent -> handleStationaryMouseDrag(mouseEvent, node));
        node.setOnMouseEntered(mouseEvent -> handleStationaryMouseEnter(mouseEvent, node));
        node.setOnMouseExited(mouseEvent -> handleStationaryMouseExit(mouseEvent, node));
    }

    public void handleDraggableMousePress(MouseEvent mouseEvent, Node node) {
        // record a delta distance for the drag and drop operation.
        // TODO: fix this
        if (node instanceof BuilderObjectView) {
            dragDelta.x = ((BuilderObjectView) node).getX() - mouseEvent.getX();
            dragDelta.y = ((BuilderObjectView) node).getY() - mouseEvent.getY();
        }
        else {
            dragDelta.x = node.getTranslateX() - mouseEvent.getX();
            dragDelta.y = node.getTranslateY() - mouseEvent.getY();
        }
        node.getScene().setCursor(Cursor.MOVE);
    }

    public void handleDraggableMouseRelease(MouseEvent mouseEvent, Node node) {
        node.getScene().setCursor(Cursor.HAND);
    }

    public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
        double newX = mouseEvent.getX() + dragDelta.x;
        double newY = mouseEvent.getY() + dragDelta.y;
        // TODO: fix this
        if (node instanceof BuilderObjectView) {
            ((BuilderObjectView) node).setX(newX);
            ((BuilderObjectView) node).setY(newY);
        }
        else {
            node.setTranslateX(newX);
            node.setTranslateY(newY);
        }
    }

    public void handleDraggableMouseEnter(MouseEvent mouseEvent, Node node) {
        if (!mouseEvent.isPrimaryButtonDown()) {
            node.getScene().setCursor(Cursor.HAND);
        }
    }

    public void handleDraggableMouseExit(MouseEvent mouseEvent, Node node) {
        if (!mouseEvent.isPrimaryButtonDown()) {
            node.getScene().setCursor(Cursor.DEFAULT);
        }
    }

    public void handleStationaryMousePress(MouseEvent mouseEvent, Node node) {
        return;
    }

    public void handleStationaryMouseRelease(MouseEvent mouseEvent, Node node) {
        return;
    }

    public void handleStationaryMouseDrag(MouseEvent mouseEvent, Node node) {
        return;
    }

    public void handleStationaryMouseEnter(MouseEvent mouseEvent, Node node) {
        return;
    }

    public void handleStationaryMouseExit(MouseEvent mouseEvent, Node node) {
        return;
    }
}
