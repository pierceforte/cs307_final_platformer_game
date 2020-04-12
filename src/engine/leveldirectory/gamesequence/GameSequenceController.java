package engine.leveldirectory.gamesequence;

import data.KeyInput;
import engine.gameobject.opponent.Raccoon;
import engine.gameobject.platform.StationaryPlatform;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class GameSequenceController {
    public static final int FRAME_DURATION = 20;
    private LevelContainer levelContainer;
    private Timeline timeline;


    //Pierce stuff
    private KeyInput keyInput;
    private SimplePlayer mainCharacter;
    private StationaryPlatform examplePlatform;
    private Raccoon raccoon;

    private GameObjectView mainCharacterView;
    private GameObjectView examplePlatformView;
    private GameObjectView raccoonView;
    private Scene myScene;
    private Pane myPane;

    public GameSequenceController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root) {
        this.levelContainer = levelContainer;
        levelContainer.setGameSequenceController(this);
        setupTimeline();
        //
        /*
        levelContainer.getStepFunction().setup(levelContainer, graphicsEngine, game);
         */



        //Pierce stuff
        myScene = scene;
        myPane = root;
        myPane.getChildren().clear();

        keyInput = new KeyInput(myScene);
        examplePlatform = new StationaryPlatform(30, 350);
        mainCharacter = new SimplePlayer(40, 310, 0, 0);
        raccoon = new Raccoon(250, 320, 5);

        mainCharacterView = new GameObjectView(mainCharacter.getImgPath(), mainCharacter.getX(),
                mainCharacter.getY(), 40, 40, mainCharacter.getXDirection());

        examplePlatformView = new GameObjectView(examplePlatform.getImgPath(), examplePlatform.getX(),
                examplePlatform.getY(), 800, 30, examplePlatform.getXDirection());

        raccoonView = new GameObjectView(raccoon.getImgPath(), raccoon.getX(),
                raccoon.getY(), 50, 30, raccoon.getXDirection());

        myPane.getChildren().addAll(List.of(examplePlatformView, mainCharacterView, raccoonView));
    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
    }

    private void step() {
        /*
        levelContainer.getGameSequenceController().step();
         */


        //Pierce stuff
        mainCharacterView.setX(mainCharacter.getX());
        mainCharacterView.setY(mainCharacter.getY());
        mainCharacterView.setScaleX(mainCharacter.getXDirection());

        raccoonView.setX(raccoon.getX());
        raccoonView.setY(raccoon.getY());
        raccoonView.setScaleX(raccoon.getXDirection());

        mainCharacter.handleInputs(keyInput.getPressedKeys());
        raccoon.updateLogic(mainCharacter);

        mainCharacter.updatePositionOnStep(0.167);
        raccoon.updatePositionOnStep(0.167);
    }

    public void pause() {
        timeline.pause();
    }

    public void play() {
        timeline.play();
    }

    public LevelContainer getLevelContainer() {
        return levelContainer;
    }

    public Timeline getTimeline() {
        return timeline;
    }
}
