package engine.leveldirectory.gamesequence;

import builder.*;
import engine.gameobject.opponent.Mongoose;
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
    private data.input.KeyInput keyInput;
    private SimplePlayer mainCharacter;
    private StationaryPlatform examplePlatform;
    private Raccoon raccoon;

    private GameObjectView mainCharacterView;
    private GameObjectView examplePlatformView;
    private GameObjectView raccoonView;
    private BankController bankController;
    private BuilderStage builderStage;

    private Scene myScene;
    private Pane myPane;

    public GameSequenceController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root, double height, double width) {
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
        BankItem one = new BankItem("raccoon.png", Raccoon.class, 30, 30, 10);
        BankItem two = new BankItem("mongoose.png", Mongoose.class, 30, 30, 20);
        BankItem three = new BankItem("raccoon.png", Raccoon.class, 30, 30, 30);
        BankItem four = new BankItem("raccoon.png", Raccoon.class, 30, 30, 40);
        BankView bankView = new BankView(20, 20, 200, 260, root);

        //replace with more robust HUD display (see money available hardcode)
        bankController = new BankController(List.of(one, two, three, four), 10000, bankView);
        builderStage = new BuilderStage(bankController, width, height);
        myPane.getChildren().add(builderStage);
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
            myPane.getChildren().remove(builderStage);
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
