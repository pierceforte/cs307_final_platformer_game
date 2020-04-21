package engine.leveldirectory.gamesequence;

import builder.*;
import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankView;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.gameobject.platform.StationaryPlatform;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import input.KeyInput;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import menu.PageBuilder;

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
    private BankController bankController;
    private BuilderStage builderStage;

    private Scene myScene;
    private BorderPane myPane;
    private Pane leftPane;

    public GameSequenceController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, BorderPane root, PageBuilder Factory) {
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

        //replace hardcode with methods that 1) read json bank file 2) generate list of BankItems
        Raccoon raccoon = new Raccoon("raccoon.png", 1d, 1d, 10d);
        Mongoose mongoose = new Mongoose("mongoose.png", 1d, 1d, 10d);
        BankItem one = new BankItem(new Raccoon(raccoon),  (int) Factory.getTileWsize(), (int) Factory.getTileHsize(), 10);
        BankItem two = new BankItem(new Mongoose(mongoose), (int) Factory.getTileWsize(), (int) Factory.getTileHsize(), 20);
        BankItem three = new BankItem(new Mongoose(mongoose), (int) Factory.getTileWsize(), (int) Factory.getTileHsize(), 30);
        BankItem four = new BankItem(new Raccoon(raccoon), (int) Factory.getTileWsize(), (int) Factory.getTileHsize(), 40);
        BankView bankView = new BankView();

        //replace with more robust HUD display (see money available hardcode)


        //read in bank item list and money
        bankController = new BankController(List.of(one, two, three, four), 10000, bankView);
        builderStage = new BuilderStage(bankController, Factory.getScreenWidth(), Factory.getScreenHeight());
        leftPane = new Pane();
        leftPane.setId("leftPane");
        leftPane.getChildren().add(bankView);
        myPane.setCenter(builderStage);
        myPane.setLeft(leftPane);
        leftPane.getChildren().add(builderStage.getPlayButton());
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
        if (builderStage.isDone()) {
            myPane.getChildren().removeAll(builderStage, leftPane);
            bankController.getBankView().removeFromRoot();
        }
        else {
            builderStage.update();
        }
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
