package builder;

import engine.view.GameObjectView;
import javafx.scene.Cursor;

/**
 * This class is used to create the frontend for build phase objects. These objects are GameObjectViews
 * that can be dragged around during the build phase. Beyond the build phase, they can simply act as
 * normal GameObjectViews by disabling the drag feature.
 *
 * The code used for the enableDrag method was modified from StackOverflow at
 * https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687
 *
 * @author Pierce Forte
 */
public class BuilderObjectView extends GameObjectView {

    public BuilderObjectView(String imgName, double xPos, double yPos, double width, double height) {
        super(imgName, xPos, yPos, width, height);
        enableDrag();
    }

    //TODO: maybe replace this
    static class Delta { double x, y; }
    /**
     * Enable the drag functionality (this method is intended to be used when the build phase begins)
     */
    public void enableDrag() {
        final Delta dragDelta = new Delta();
        setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = this.getX() - mouseEvent.getX();
            dragDelta.y = this.getY() - mouseEvent.getY();
            this.getScene().setCursor(Cursor.MOVE);
        });
        setOnMouseReleased(mouseEvent -> this.getScene().setCursor(Cursor.HAND));
        setOnMouseDragged(mouseEvent -> {
            setX(mouseEvent.getX() + dragDelta.x);
            setY(mouseEvent.getY() + dragDelta.y);
        });
        setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    /**
     * Disable the drag functionality (this method is intended to be used after the build phase when
     * build objects are no longer movable)
     */
    public void disableDrag() {
        setOnMousePressed(mouseEvent -> {return;});
        setOnMouseReleased(mouseEvent -> {return;});
        setOnMouseDragged(mouseEvent -> {return;});
        setOnMouseEntered(mouseEvent -> {return;});
        setOnMouseExited(mouseEvent -> {return;});
    }
}
