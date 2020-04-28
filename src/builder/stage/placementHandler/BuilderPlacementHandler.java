package builder.stage.placementHandler;

import builder.stage.BuilderObjectView;
import builder.stage.TilePaneDimensions;
import engine.view.GameObjectView;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * This class supports the placement of objects on the BuilderPane.
 *
 * This class implements the PlacementHandler interface, which requires it to handle the placement of all movable objects,
 * verifying whether these items are in valid positions.
 *
 * This class is dependent on the BuilderPane's current position and dimensions. It is used to determine whether placements
 * are valid and signalling to the user when they are not (in this specific class, these cases occur when objects overlap or
 * out of the bounds determined by the PaneDimensions dependency).
 *
 * @author Pierce Forte
 */
public class BuilderPlacementHandler implements PlacementHandler {
    public static final Color INVALID_PLACEMENT_COLOR = Color.RED;
    public static final int INVALID_PLACEMENT_SATURATION = -1;

    private TilePaneDimensions dimensions;
    private List<GameObjectView> immovableObjects;
    private List<BuilderObjectView> objectsToCheck;

    /**
     * The constructor to create a BuilderPlacementHandler.
     * @param dimensions the dimensions of the BuilderPane
     * @param immovableObjects the GameObjectViews that are part of the level's initial state and cannot be modified
     */
    public BuilderPlacementHandler(TilePaneDimensions dimensions, List<GameObjectView> immovableObjects) {
        this.dimensions = dimensions;
        this.immovableObjects = immovableObjects;
        objectsToCheck = new ArrayList<>();
    }

    /**
     * Sets the objects that are movable and can cause errors when overlapping; therefore, they are set to be checked.
     * @param builderObjectViews the list of potentially overlapping BuilderObjectViews
     */
    public void setObjectsToCheck(List<BuilderObjectView> builderObjectViews) {
        this.objectsToCheck = builderObjectViews;
    }

    @Override
    public void handlePlacement() {
        for (BuilderObjectView object : objectsToCheck) {
            if (checkForOverlap(object, objectsToCheck, immovableObjects) || isImageViewOutOfBounds(object)) {
                handleInvalidPlacement(object);
            }
            else {
                handleValidPlacement(object);
            }
        }
    }

    @Override
    public boolean isImageViewOutOfBounds(ImageView imageView) {
        return imageView.getX() < dimensions.getMinX()*dimensions.getTileWidth() ||
                imageView.getX() + imageView.getFitWidth() > dimensions.getMaxX()*dimensions.getTileWidth() ||
                imageView.getY() < dimensions.getMinY()*dimensions.getTileHeight() ||
                imageView.getY() + imageView.getFitHeight() > dimensions.getMaxY()*dimensions.getTileHeight();
    }

    private boolean checkForOverlap(BuilderObjectView object, List<BuilderObjectView> movableObjects,
                                    List<GameObjectView> immovableObjects) {
        List<GameObjectView> otherObjects = new ArrayList<>(movableObjects);
        otherObjects.addAll(immovableObjects);
        for (ImageView otherObject : otherObjects) {
            if (object.equals(otherObject)) {
                continue;
            }
            if (object.overlaps(otherObject)) {
                return true;
            }
        }
        object.setEffect(null);
        return false;
    }

    private void handleValidPlacement(BuilderObjectView object) {
        if (object.isOverlapped()) {
            object.setHasNewActionItems(true);
        }
        object.setIsOverlapped(false);
    }

    private void handleInvalidPlacement(BuilderObjectView object) {
        if (object.isDraggable()) {
            if (!object.isOverlapped()) {
                object.setHasNewActionItems(true);
            }
            object.setIsOverlapped(true);
            addInvalidPlacementEffect(object);
        }
    }

    private void addInvalidPlacementEffect(ImageView imageView) {
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(INVALID_PLACEMENT_SATURATION);
        Blend invalidPlacementBlend = new Blend(BlendMode.MULTIPLY, monochrome,
                new ColorInput(imageView.getX(), imageView.getY(),
                        imageView.getFitWidth(), imageView.getFitHeight(),
                        INVALID_PLACEMENT_COLOR));
        imageView.setEffect(invalidPlacementBlend);
    }
}