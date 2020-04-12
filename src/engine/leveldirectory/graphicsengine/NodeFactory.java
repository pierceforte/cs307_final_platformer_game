package engine.leveldirectory.graphicsengine;

import engine.gameobject.GameObject;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Input: GameObjects
 * Output: corresponding JavaFX ImageView displays
 *
 * Assumes GameObjects have fields that store image path, x, y, width, height
 *
 * @author Jerry Huang
 */
public class NodeFactory {
    public ImageView generateImage(GameObject go) {
        ImageView i = new ImageView(new Image(go.getImagePath()));
        i.setX(go.getX());
        i.setY(go.getY());
        i.setFitWidth(go.getWidth());
        i.setFitHeight(go.getHeight());
        return i;
    }
}
