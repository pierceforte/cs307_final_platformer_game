package engine.leveldirectory.graphicsengine;

import engine.gameobject.GameObject;
import engine.general.Game;
import engine.leveldirectory.hud.HUDController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pagination.loadandplay.AbstractPlayer;

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

    private HUDController scoreBoardController;
    private BorderPane borderPane;

    public GraphicsEngine(Game game, ResourceBundle resourceBundle, AbstractPlayer abstractPlayer) {
        gameObjects = new ArrayList<>();
        nodes = new ArrayList<>();
        scoreBoardController = new HUDController(game.getLevelContainer().getLevelNum(), 0, 5);
        this.resourceBundle = resourceBundle;

    }

    public Pane getBorderPane() { return borderPane; }

    public HUDController getScoreBoardController() {
        return scoreBoardController;
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
            nodes.add(temp);
            borderPane.getChildren().add(temp);
        }
    }
}
