package builder.stage;

import builder.NodeDragger;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This is an abstract class that extends the TilePane class, proving a tile-based pane that can be dragged. This drag
 * feature is maintained through composition with a NodeDragger object.
 *
 * This class is dependent on a dimensions object which determines its size and its tiles' size.
 *
 * @author Pierce Forte
 */
public abstract class DraggableTilePane extends TilePane {

    private NodeDragger nodeDragger;
    private boolean isDraggable;

    /**
     * The constructor to create a DraggableTilePane.
     * @param dimensions the dimensions associated with this DraggableTilePane
     */
    public DraggableTilePane(TilePaneDimensions dimensions) {
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

    @Override
    public abstract void update();

    /**
     * Returns whether the DraggableTilePane is currently draggable.
     * @return whether the DraggableTilePane is currently draggable
     */
    public boolean isDraggable() {
        return isDraggable;
    }

    /**
     * Enables the draggable feature.
     */
    protected void enableDrag() {
        if (!isDraggable) {
            nodeDragger.enableDrag(this);
            isDraggable = true;
        }
    }

    /**
     * Disables the draggable feature, effectively causing the DraggableTilePane to function entirely like
     * its superclass, a TilePane.
     */
    protected void disableDrag() {
        if (isDraggable) {
            nodeDragger.disableDrag(this);
            isDraggable = false;
        }
    }

    /**
     * Gets the new position (either x or y) for the DraggableTilePane when it is dragged.
     * @param dragPos the actual position (either x or y) of the DraggableTilePane
     * @param minPos the minimum allowed position (either x or y) for this DraggableTilePane
     * @param size the size of the tile pane (in either the x or y direction)
     * @param screenSize the size of the screen (in either the x or y direction)
     * @return the new position (either x or y) of the DraggableTilePane
     */
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
