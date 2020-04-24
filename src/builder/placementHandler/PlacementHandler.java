package builder.placementHandler;

import javafx.scene.image.ImageView;

public interface PlacementHandler {

    /**
     * Defines how to verify what needs to be done to fix placement
     */
    void handlePlacement();

    /**
     * Defines how to determine if an object is out of bounds
     * @param object The object to be checked
     * @return Whether object is out of bounds
     */
    boolean isImageViewOutOfBounds(ImageView object);


}
