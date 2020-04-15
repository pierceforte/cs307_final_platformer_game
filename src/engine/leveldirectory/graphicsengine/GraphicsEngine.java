package engine.leveldirectory.graphicsengine;

import engine.gameobject.GameObject;
import engine.general.Game;
import engine.leveldirectory.gamesequence.ScoreDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import player.loadandplay.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class defines how the graphics are created and displayed
 */
public class GraphicsEngine {
    private ResourceBundle resourceBundle;
    private List<GameObject> gameObjects; // all the game objects to be displayed
    private List<ImageView> nodes;

    private ScoreDisplay scoreDisplay;
    private BorderPane borderPane;

    public GraphicsEngine(Game game, ResourceBundle resourceBundle, AbstractPlayer abstractPlayer) {
        gameObjects = new ArrayList<>();
        nodes = new ArrayList<>();
        scoreDisplay = new ScoreDisplay(game);
        this.resourceBundle = resourceBundle;

    }

    public Pane getBorderPane() { return borderPane; }

    public ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }

    private void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        this.updateUI();
    }
    /**
     * updates the view
     */
    public void updateUI() {
        // clear all existing UI
        nodes.clear();
        borderPane.getChildren().clear();

        // draw all objects
        NodeFactory nodeFactory = new NodeFactory();
        for (GameObject g : gameObjects) {
            ImageView temp = nodeFactory.generateImage(g);

            // TODO: add ImageView to display

            nodes.add(temp);
            borderPane.getChildren().add(temp);

            // TODO: sort which object is on top?
        }
    }
}
