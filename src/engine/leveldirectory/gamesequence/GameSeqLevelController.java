package engine.leveldirectory.gamesequence;

import builder.BuilderStage;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class GameSeqLevelController extends GameSeqController {

    public GameSeqLevelController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
        setUpRunnable();
        setupTimeline();
        setUpListeners();

        // TODO: remove player test later
        playerTest();
    }
    private void playerTest() {
        SimplePlayer s = new SimplePlayer("babysnake.png", 300., 400., 200.,200.);
        setSimplePlayer(s);
    }

    private void setUpRunnable() {
        // TODO: check if this isn't the last level
        super.setNextPlayScene(()->{
            pause();
            super.getLevelContainer().incrementLevel();
            GameSeqBuilderController builderTemp = new GameSeqBuilderController(getLevelContainer(), getGraphicsEngine(),
                    getGame(), getMyScene(), getRoot(), getHeight(), getWidth());
            builderTemp.play();
        });
    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        Timeline temp = new Timeline();
        temp.setCycleCount(Timeline.INDEFINITE);
        temp.getKeyFrames().add(frame);
        setTimeline(temp);
    }

    public void step() {
        //System.out.println("StepStep");
        //getMyScene().setOnKeyPressed(e -> userInput(e.getCode()));
        List<GameObject> tempGameObjects = getLevelContainer().getCurrentLevel().getAllGameObjects();
        for (GameObject tempObj : getLevelContainer().getCurrentLevel().getAllGameObjects()) {
            interactionsAndGravity(tempObj);
        }
        getLevelContainer().getCurrentLevel().setGameObjects(tempGameObjects);
        super.display();
    }

    private void setUpListeners() {
        getMyScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            System.out.println("Key Pressed");
            if (key.getCode() == KeyCode.LEFT)
                getSimplePlayer().move(SimplePlayer.LEFT);
            else if (key.getCode() == KeyCode.RIGHT)
                getSimplePlayer().move(SimplePlayer.RIGHT);
            else if (key.getCode() == KeyCode.SPACE)
                getSimplePlayer().jump();
        });
    }


    private void interactionsAndGravity(GameObject gameObject) {
        for (GameObject tempObj : getLevelContainer().getCurrentLevel().getAllGameObjects()) {
            // TODO check if the panes intersect etc.

        }
    }

    public void endPhase() {
        getNextPlayScene().run();
    }
}
