package engine.view;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pierce Forte
 */
public class ImageCreator {
    private static Map<String, Image> imageMap = new HashMap<>();

    public static Image makeImage(String imgPath) {
        if (!imageMap.containsKey(imgPath)) {
            Image image = new Image(ImageCreator.class.getClassLoader().getResource(imgPath).toExternalForm());
            imageMap.put(imgPath, image);
        }
        return imageMap.get(imgPath);
    }
}