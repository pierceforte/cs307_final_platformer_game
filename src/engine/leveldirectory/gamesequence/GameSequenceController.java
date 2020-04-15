package engine.leveldirectory.gamesequence;

import builder.Bank;
import builder.BankItem;
import builder.BuilderObjectView;
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
    private Bank bank;

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
        examplePlatform = new StationaryPlatform(30, 350, StationaryPlatform.EX_IMG_PATH);
        mainCharacter = new SimplePlayer(40, 310, 0, 0, SimplePlayer.EX_IMG_PATH);
        raccoon = new Raccoon(250, 320, 5, Raccoon.EX_IMG_PATH);

        mainCharacterView = new GameObjectView(mainCharacter.getImgPath(), mainCharacter.getX(),
                mainCharacter.getY(), 40, 40, mainCharacter.getXDirection());

        examplePlatformView = new GameObjectView(examplePlatform.getImgPath(), examplePlatform.getX(),
                examplePlatform.getY(), 800, 30, examplePlatform.getXDirection());

        raccoonView = new GameObjectView(raccoon.getImgPath(), raccoon.getX(),
                raccoon.getY(), 50, 30, raccoon.getXDirection());

        BankItem one = new BankItem(mainCharacter.getImgPath(), 30, 30, 10);
        BankItem two = new BankItem("mongoose.png", 30, 30, 20);
        BankItem three = new BankItem(raccoon.getImgPath(), 30, 30, 30);
        BankItem four = new BankItem(mainCharacter.getImgPath(), 30, 30, 40);

        bank = new Bank(List.of(one, two, three, four), 20, 20, 200, 200, 10000, root);

        BuilderObjectView builderObjectView = new BuilderObjectView(mainCharacter.getImgPath(), 350, 350, 50, 50, root);

        myPane.getChildren().addAll(List.of(examplePlatformView, mainCharacterView, raccoonView, builderObjectView));

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
