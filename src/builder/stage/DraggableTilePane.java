package builder.stage;

import builder.NodeDragger;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class DraggableTilePane extends TilePane {

    private NodeDragger nodeDragger;
    private boolean isDraggable;

    public DraggableTilePane(PaneDimensions dimensions) {
        super(dimensions);
        double width = getWidth();
        double height = getHeight();
        nodeDragger = new NodeDragger() {
            @Override
            public void handleDraggableMouseDrag(MouseEvent mouseEvent, Node node) {
                double dragPosX = mouseEvent.getX() + getDeltaX();
                double dragPosY = mouseEvent.getY() + getDeltaY();
                node.setTranslateX(getNewPosOnDrag(dragPosX, dimensions.getMinX(), width, dimensions.getScreenWidth()));
                node.setTranslateY(getNewPosOnDrag(dragPosY, dimensions.getMinY(), height, dimensions.getScreenHeight()));
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

    protected double getNewPosOnDrag(double dragPos, double minPos, double size, double screenSize) {
        double newPos = dragPos;
        if (newPos >= minPos) {
            newPos = minPos;
        }
        else if (newPos <= screenSize - size) {
            newPos = screenSize - size;
            if (size < screenSize) {
                newPos = minPos;
            }
        }
        return newPos;
    }
}
