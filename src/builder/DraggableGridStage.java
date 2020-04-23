package builder;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class DraggableGridStage extends GridStage {

    private NodeDragger nodeDragger;
    private boolean isDraggable;

    public DraggableGridStage(GridDimensions dimensions) {
        super(dimensions);
        double width = getWidth();
        double height = getHeight();
        nodeDragger = new NodeDragger() {
            @Override
            public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
                double dragPosX = mouseEvent.getX() + getDeltaX();
                double dragPosY = mouseEvent.getY() + getDeltaY();
                node.setTranslateX(getNewXOnDrag(dragPosX, dimensions, width));
                node.setTranslateY(getNewYOnDrag(dragPosY, dimensions, height));
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

    protected double getNewXOnDrag(double dragPosX, GridDimensions dimensions, double width) {
        double newX = dragPosX;
        if (newX >= dimensions.getMinX()) {
            newX = dimensions.getMinX();
        }
        else if (newX <= dimensions.getScreenWidth() - width) {
            newX = dimensions.getScreenWidth() - width;
            if (width < dimensions.getScreenWidth()) {
                newX = dimensions.getMinX();
            }
        }
        return newX;
    }

    protected double getNewYOnDrag(double dragPosY, GridDimensions dimensions, double height) {
        double newY = dragPosY;
        if (newY >= dimensions.getMinY()) {
            newY = dimensions.getMinY();
        }
        else if (newY <= dimensions.getScreenHeight() - height) {
            newY = dimensions.getScreenHeight() - height;
            if (height < dimensions.getScreenHeight()) {
                newY = dimensions.getMinY();
            }
        }
        return newY;
    }
}
