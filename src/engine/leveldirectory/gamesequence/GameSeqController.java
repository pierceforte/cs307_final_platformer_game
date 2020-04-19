package engine.leveldirectory.gamesequence;

import builder.*;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.gameobject.platform.StationaryPlatform;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.graphicsengine.NodeFactory;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class GameSeqController {
    public static final int FRAME_DURATION = 20;
    private LevelContainer levelContainer;
    private Timeline timeline;
    private GraphicsEngine graphicsEngine;

    private double height;
    private double width;

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

    public GameSeqController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root, double height, double width) {
        this.levelContainer = levelContainer;
        levelContainer.setGameSeqController(this);
        this.graphicsEngine = graphicsEngine;
        this.height = height;
        this.width = width;
        setupTimeline();
        initialize(scene, root);
    }

    public void initialize(Scene scene, Pane root) {
        //Pierce stuff
        myScene = scene;
        myPane = root;

        //replace hardcode with methods that 1) read json bank file 2) generate list of BankItems
        Raccoon raccoon = new Raccoon("raccoon.png", 1, 1, 10);
        Mongoose mongoose = new Mongoose("mongoose.png", 1, 1, 10);
        BankItem one = new BankItem(new Raccoon(raccoon),  30, 30, 10);
        BankItem two = new BankItem(new Mongoose(mongoose), 30, 30, 20);
        BankItem three = new BankItem(new Mongoose(mongoose), 30, 30, 30);
        BankItem four = new BankItem(new Raccoon(raccoon), 30, 30, 40);
        BankView bankView = new BankView(20, 20, 200, 260, root);

        //replace with more robust HUD display (see money available hardcode)
        //read in bank item list and money
        bankController = new BankController(List.of(one, two, three, four), 10000, bankView);
        builderStage = new BuilderStage(bankController, width, height);
        myPane.getChildren().add(builderStage);
    }

    public void display() {
        for (GameObject g : levelContainer.getCurrentLevel().getAllGameObjects()) {
            myPane.getChildren().removeAll();
            NodeFactory nodeFactory = new NodeFactory();
            myPane.getChildren().add(nodeFactory.generateImage(g)); //  fix this line
        }
    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
    }

    private void step() {
        //Pierce stuff
        if (builderStage.isDone()) {
            myPane.getChildren().remove(builderStage);
            bankController.getBankView().removeFromRoot();
        }
        else {
            builderStage.update();
        }
    }

    public Scene getMyScene() { return myScene; }

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
