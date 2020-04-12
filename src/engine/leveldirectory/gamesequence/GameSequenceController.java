package engine.leveldirectory.gamesequence;

import data.KeyInput;
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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private Mongoose mongoose;

    private GameObjectView mainCharacterView;
    private GameObjectView examplePlatformView;
    private GameObjectView raccoonView;
    private GameObjectView mongooseView;
    private Pane myPane;

    public GameSequenceController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game) {
        this.levelContainer = levelContainer;
        levelContainer.setGameSequenceController(this);
        setupTimeline();
        //
        /*
        levelContainer.getStepFunction().setup(levelContainer, graphicsEngine, game);
         */



        //Pierce stuff

        Stage myStage = new Stage();
        myPane = new Pane();
        myPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene myScene = new Scene(myPane, 400, 400);
        myStage.setScene(myScene);
        myStage.show();

        keyInput = new KeyInput(myScene);
        examplePlatform = new StationaryPlatform(0, 350);
        mainCharacter = new SimplePlayer(10, 310, 0, 0);
        raccoon = new Raccoon(250, 320, 5);
        mongoose = new Mongoose(200, 310, 5);

        mainCharacterView = new GameObjectView(mainCharacter.getImgPath(), mainCharacter.getX(),
                mainCharacter.getY(), 40, 40);

        examplePlatformView = new GameObjectView(examplePlatform.getImgPath(), examplePlatform.getX(),
                examplePlatform.getY(), 400, 50);

        raccoonView = new GameObjectView(raccoon.getImgPath(), raccoon.getX(),
                raccoon.getY(), 50, 30);

        mongooseView = new GameObjectView(mongoose.getImgPath(), mongoose.getX(),
                mongoose.getY(), 40, 40);

        myPane.getChildren().addAll(List.of(examplePlatformView, mainCharacterView, raccoonView, mongooseView));
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

        raccoonView.setX(raccoon.getX());
        raccoonView.setY(raccoon.getY());

        mongooseView.setX(mongoose.getX());
        mongooseView.setY(mongoose.getY());

        mainCharacter.handleInputs(keyInput.getPressedKeys());
        raccoon.updateLogic(mainCharacter);
        mongoose.updateLogic(mainCharacter);

        mainCharacter.updatePositionOnStep(0.167);
        raccoon.updatePositionOnStep(0.167);
        mongoose.updatePositionOnStep(0.167);
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
