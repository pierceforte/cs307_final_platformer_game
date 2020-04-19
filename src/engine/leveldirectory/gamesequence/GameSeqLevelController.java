package engine.leveldirectory.gamesequence;

import engine.gameobject.GameObject;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.List;

public class GameSeqLevelController extends GameSeqController {

    public GameSeqLevelController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
    }

    private void step() {
        getMyScene().setOnKeyPressed(e -> userInput(e.getCode()));
        List<GameObject> tempGameObjects = getLevelContainer().getCurrentLevel().getAllGameObjects();
        for (GameObject tempObj : getLevelContainer().getCurrentLevel().getAllGameObjects()) {
            gravity(tempObj);
            // TODO
        }
        getLevelContainer().getCurrentLevel().setGameObjects(tempGameObjects);
        super.display();
    }

    private void userInput(KeyCode keyCode) {
        if (keyCode == KeyCode.LEFT) {
            // TODO
        }
    }
    private void gravity(GameObject gameObject) {

    }
}
