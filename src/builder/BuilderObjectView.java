package builder;

import engine.view.GameObjectView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

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

    public static final String PATH_TO_CHECK_IMG = "";
    public static final String PATH_TO_X_IMG = "";
    public static final String PATH_TO_MOVE_IMG = "";
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private boolean isDraggable;
    private boolean isActive;
    private List<Node> actionIcons;

    public BuilderObjectView(String imgPath, double xPos, double yPos, double width, double height) {
        super(imgPath, xPos, yPos, width, height);
        enableDrag();
        isActive = true;
        actionIcons = new ArrayList<>();
        askUserToPlaceMe();
    }

    public boolean isActive() {
        return isActive;
    }

    //TODO: maybe replace this
    static class Delta { double x, y; }
    /**
     * Enable the drag functionality (this method is intended to be used when the build phase begins)
     */
    public void enableDrag() {
        isDraggable = true;
        final Delta dragDelta = new Delta();
        setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = this.getX() - mouseEvent.getX();
            dragDelta.y = this.getY() - mouseEvent.getY();
            this.getScene().setCursor(Cursor.MOVE);
            hideActionIcons();
        });
        setOnMouseReleased(mouseEvent -> {
            this.getScene().setCursor(Cursor.HAND);
            askUserToPlaceMe();
        });
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
        isDraggable = false;
        hideActionIcons();
        setOnMousePressed(mouseEvent -> askUserToMoveMe());
        setOnMouseReleased(mouseEvent -> {return;});
        setOnMouseDragged(mouseEvent -> {return;});
        setOnMouseEntered(mouseEvent -> {return;});
        setOnMouseExited(mouseEvent -> {return;});
    }

    private void askUserToPlaceMe() {
        ImageView check = createActionIcon(PATH_TO_CHECK_IMG, RIGHT);
        ImageView x = createActionIcon(PATH_TO_X_IMG, LEFT);

        check.setOnMouseClicked(mouseEvent -> disableDrag());
        x.setOnMouseClicked(mouseEvent -> {
            isActive = false;
            hideActionIcons();
            // give back money
        });
    }

    private void askUserToMoveMe() {
        ImageView move = createActionIcon(PATH_TO_MOVE_IMG, RIGHT);
        ImageView x = createActionIcon(PATH_TO_X_IMG, LEFT);

        move.setOnMouseClicked(mouseEvent -> enableDrag());
        x.setOnMouseClicked(mouseEvent -> isActive = false);
    }

    private ImageView createActionIcon(String imgPath, int side) {
        Image img = makeImage(imgPath);
        ImageView imgView = new ImageView(img);
        imgView.setX(this.getX() + 10 * side);
        imgView.setY(this.getY() - 10);
        actionIcons.add(imgView);
        //TODO: add imgView to scene
        return imgView;
    }

    private void hideActionIcons() {
        for (Node actionIcon : actionIcons) {
            //TODO: remove actionIcon from scene
        }
        actionIcons.clear();
    }
}
