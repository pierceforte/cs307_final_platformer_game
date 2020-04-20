package engine.leveldirectory.gamesequence;

import builder.BuilderStage;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class GameSeqBuilderController extends GameSeqController {
    private data.input.KeyInput keyInput;
    private SimplePlayer mainCharacter;
    private StationaryPlatform examplePlatform;
    private Raccoon raccoon;

    private GameObjectView mainCharacterView;
    private GameObjectView examplePlatformView;
    private GameObjectView raccoonView;
    private BankController bankController;
    private BuilderStage builderStage;

    private Runnable nextPlayScene;

    public void setNextPlayScene(Runnable nextPlayScene) {
        this.nextPlayScene = nextPlayScene;
    }
    public Runnable getNextPlayScene() {
        return nextPlayScene;
    }

    public GameSeqBuilderController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game,
                                    Scene scene, Pane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
        setUpRunnable();
        setupTimeline();
        initialize(scene, root);
    }

    private void setUpRunnable() {
        super.setNextPlayScene(()->{
            pause();
            GameSeqLevelController playTemp = new GameSeqLevelController(getLevelContainer(), getGraphicsEngine(),
                    getGame(), getMyScene(), getRoot(), getHeight(), getWidth());
            playTemp.play();
        });
    }

    public void initialize(Scene scene, Pane root) {
        // TODO: initialize from levelContainer.getCurrentLevel()
        setMyScene(scene);
        setRoot(root);

        Raccoon raccoon = new Raccoon("raccoon.png", 1., 1., 10.);
        Mongoose mongoose = new Mongoose("mongoose.png", 1., 1., 10.);

        BankItem one = new BankItem(new Raccoon(raccoon),  30, 30, 10);
        BankItem two = new BankItem(new Mongoose(mongoose), 30, 30, 20);
        BankItem three = new BankItem(new Mongoose(mongoose), 30, 30, 30);
        BankItem four = new BankItem(new Raccoon(raccoon), 30, 30, 40);
        BankView bankView = new BankView(20, 20, 200, 260, root);

        //replace with more robust HUD display (see money available hardcode)
        //read in bank item list and money
        bankController = new BankController(List.of(one, two, three, four), 10000, bankView);
        builderStage = new BuilderStage(bankController, getWidth(), getHeight());
        getRoot().getChildren().add(builderStage);
    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        Timeline temp = new Timeline();
        temp.setCycleCount(Timeline.INDEFINITE);
        temp.getKeyFrames().add(frame);
        setTimeline(temp);
    }

    public void step() {
        if (builderStage.isDone()) {
            getRoot().getChildren().remove(builderStage);
            bankController.getBankView().removeFromRoot();
            endPhase();
            // TODO: add objects to the current level's list of GameObjects
        }
        else
            builderStage.update();
    }

    public void endPhase() {
        this.getTimeline().stop();
        //nextPlayScene.run();
    }
}
