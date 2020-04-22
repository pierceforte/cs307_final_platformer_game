package builder;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class DraggableGridStage extends GridStage {

    private NodeDragger nodeDragger;
    private boolean isDraggable;

    public DraggableGridStage(double width, double height) {
        super(width, height);
        nodeDragger = new NodeDragger() {
            @Override
            public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
                double newX = mouseEvent.getX() + getDeltaX();
                double newY = mouseEvent.getY() + getDeltaY();
                newX = newX >= 0 ? 0 : newX;
                newY = newY >= 0 ? 0 : newY;
                node.setTranslateX(newX);
                node.setTranslateY(newY);
            }
        };
    }

    public abstract void update();

    protected void enableDrag() {
        if (!isDraggable) {
            nodeDragger.enableDrag(this);
            isDraggable = true;
        }
    }

    protected void disableDrag() {
        if (isDraggable) {
            nodeDragger.disableDrag(this);
            isDraggable = false;
        }
    }

    protected void snap(double maxX, double maxY) {
        if (getTranslateX() >= maxX) {
            setTranslateX(maxX);
        }
        if (getTranslateY() >= maxY) {
            setTranslateY(maxY);
        }
    }
}
