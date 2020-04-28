package engine.view;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * This class serves the purpose of providing a single method for creating ALL images in the application, as well
 * as storing these images in a single place for reuse. Because create Image objects requires significant memory, this
 * class assures that any given image will only ever be created once. When the image has already been created, it is
 * simply reused to maximize efficiency.
 *
 * @author Pierce Forte
 */
public class ImageCreator {
    private static Map<String, Image> imageMap = new HashMap<>();

    /**
     * Returns an Image object based on its path.
     * @param imgPath the path for the image
     * @return the Image object
     */
    public static Image makeImage(String imgPath) {
        if (!imageMap.containsKey(imgPath)) {
            Image image = new Image(ImageCreator.class.getClassLoader().getResource(imgPath).toExternalForm());
            imageMap.put(imgPath, image);
        }
        return imageMap.get(imgPath);
    }
}