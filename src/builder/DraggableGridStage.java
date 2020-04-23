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
                double newX = mouseEvent.getX() + getDeltaX();
                double newY = mouseEvent.getY() + getDeltaY();

                if (newX >= dimensions.getMinX()) {
                    newX = dimensions.getMinX();
                }
                else if (newX <= dimensions.getScreenWidth() - width) {
                    newX = dimensions.getScreenWidth() - width;
                    if (width < dimensions.getScreenWidth()) {
                        newX = dimensions.getMinX();
                    }
                }

                if (newY >= dimensions.getMinY()) {
                    newY = dimensions.getMinY();
                }
                else if (newY <= dimensions.getScreenHeight() - height) {
                    newY = dimensions.getScreenHeight() - height;
                    if (height < dimensions.getScreenHeight()) {
                        newY = dimensions.getMinY();
                    }
                }

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

    protected void snap(double minX, double maxX, double minY, double maxY) {
        if (getTranslateX() >= minX) {
            //setTranslateX(minX);
        }
        else if (getTranslateX() <= maxX - getWidth()) {
            //setTranslateX(maxX - getWidth());
        }

        if (getTranslateY() >= minY) {
            //setTranslateY(minY);
        }
        else if (getTranslateY() <= maxY - getHeight()) {
            //setTranslateY(maxY - getHeight());
        }

    }
}
