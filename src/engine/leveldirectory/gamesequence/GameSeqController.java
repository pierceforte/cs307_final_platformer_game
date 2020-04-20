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

public abstract class GameSeqController {
    public static final int FRAME_DURATION = 20;
    private LevelContainer levelContainer;
    private Timeline timeline;
    private GraphicsEngine graphicsEngine;

    private double height;
    private double width;

    private Scene myScene;
    private Pane myPane;

    public GameSeqController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, Pane root, double height, double width) {
        this.levelContainer = levelContainer;
        levelContainer.setGameSeqController(this);
        this.graphicsEngine = graphicsEngine;
        this.height = height;
        this.width = width;
    }

    public void setTimeline(Timeline t) {
        timeline = t;
    }

    public void display() {
        for (GameObject g : levelContainer.getCurrentLevel().getAllGameObjects()) {
            myPane.getChildren().removeAll();
            GameObjectView gameObjectView = new GameObjectView(g.getImagePath(), g.getX(), g.getY(), g.getWidth(), g.getHeight(), g.getXDirection());
            myPane.getChildren().add(gameObjectView);
        }
    }

    public Scene getMyScene() { return myScene; }
    public void setMyScene(Scene scene) { this.myScene = scene; }
    public Pane getRoot() { return myPane; }
    public void setRoot(Pane root) { this.myPane = root; }
    public double getHeight() { return this.height; }
    public double getWidth() { return width; }

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
