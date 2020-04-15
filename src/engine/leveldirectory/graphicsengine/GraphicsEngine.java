package engine.leveldirectory.graphicsengine;

import engine.gameobject.GameObject;
import engine.leveldirectory.gamesequence.ScoreDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.ResourceBundle;

/**
 *
 */
public class GraphicsEngine {
    private ResourceBundle resourceBundle;

    private List<GameObject> gameObjects;
    private List<ImageView> nodes;

    private ScoreDisplay scoreDisplay;

    private BorderPane borderPane;

    public Pane getBorderPane() { return borderPane; }

    /**
     * updates the view
     */
    public void updateUI() {

    }
}
