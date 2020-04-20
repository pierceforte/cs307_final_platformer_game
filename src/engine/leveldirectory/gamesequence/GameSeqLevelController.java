package engine.leveldirectory.gamesequence;

import builder.BankController;
import builder.BankItem;
import builder.BankView;
import builder.BuilderStage;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class GameSeqLevelController extends GameSeqController {

    public GameSeqLevelController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
        setupTimeline();
        initialize(scene, root);
    }

    public void initialize(Scene scene, Pane root) {

    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        Timeline temp = new Timeline();
        temp.setCycleCount(Timeline.INDEFINITE);
        temp.getKeyFrames().add(frame);
        setTimeline(temp);
    }

    public void step() {
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
        // TODO
    }
}
