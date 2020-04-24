package builder.placementHandler;

import builder.stage.BuilderObjectView;
import builder.stage.PaneDimensions;
import engine.view.GameObjectView;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BuilderPlacementHandler implements PlacementHandler {
    public static final Color INVALID_PLACEMENT_COLOR = Color.RED;
    public static final int INVALID_PLACEMENT_SATURATION = -1;

    private PaneDimensions dimensions;
    private List<GameObjectView> immovableObjects;
    private List<BuilderObjectView> objectsToCheck;

    public BuilderPlacementHandler(PaneDimensions dimensions, List<GameObjectView> immovableObjects) {
        this.dimensions = dimensions;
        this.immovableObjects = immovableObjects;
        objectsToCheck = new ArrayList<>();
    }

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

    public boolean isImageViewOutOfBounds(ImageView imageView) {
        return imageView.getX() < dimensions.getMinX()*dimensions.getTileWidth() ||
                imageView.getX() + imageView.getFitWidth() > dimensions.getMaxX()*dimensions.getTileWidth() ||
                imageView.getY() < dimensions.getMinY()*dimensions.getTileHeight() ||
                imageView.getY() + imageView.getFitHeight() > dimensions.getMaxY()*dimensions.getTileHeight();
    }

    public void setObjectsToCheck(List<BuilderObjectView> builderObjectViews) {
        this.objectsToCheck = builderObjectViews;
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