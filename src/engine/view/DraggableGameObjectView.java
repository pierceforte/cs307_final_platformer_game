package engine.view;

import builder.NodeDragger;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DraggableGameObjectView extends GameObjectView {

    private NodeDragger nodeDragger;
    private boolean isSnapped;
    private boolean isReadyForSnap;
    private boolean isDraggable;
    private boolean isOverlapped;

    public DraggableGameObjectView(String imgPath, double xPos, double yPos, double width, double height, int xDirection) {
        super(imgPath, xPos, yPos, width, height, xDirection);
        isDraggable = true;
        isReadyForSnap = true;
        initializeNodeDragger();
        enableDrag();
    }

    public boolean isOverlapped() {
        return isOverlapped;
    }

    public void setIsOverlapped(boolean isOverlapped) {
        this.isOverlapped = isOverlapped;
    }

    public boolean isDraggable() {
        return isDraggable;
    }

    public boolean isReadyForSnap() {
        return isReadyForSnap;
    }

    public boolean isSnapped() {
        return isSnapped;
    }

    public void setIsSnapped(boolean isSnapped) {
        this.isSnapped = isSnapped;
    }

    public void enableDrag() {
        isDraggable = true;
        nodeDragger.enableDrag(this);
    }

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