package builder.stage.placementHandler;

import javafx.scene.image.ImageView;

/**
 * This is an interface that requires the classes that implement it to define how to handle the placement of movable
 * objects and determine if they are out of bounds.
 *
 * This interface can, and should, be used by any stage that must handle the movement and placement of its objects.
 *
 * @author Pierce Forte
 */
public interface PlacementHandler {

    /**
     * Defines how to verify what needs to be done to fix placement.
     */
    void handlePlacement();

    /**
     * Defines how to determine if an ImageView is out of bounds.
     * @param object the ImageView to be checked
     * @return whether object is out of bounds
     */
    boolean isImageViewOutOfBounds(ImageView object);
}
