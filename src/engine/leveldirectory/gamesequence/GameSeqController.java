package engine.leveldirectory.gamesequence;

import builder.*;
import engine.gameobject.GameObject;
import engine.gameobject.MovingGameObject;
import engine.gameobject.player.Player;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.graphicsengine.NodeFactory;
import engine.leveldirectory.level.Level;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;


public abstract class GameSeqController {
    public static final int FRAME_DURATION = 20;
    private LevelContainer levelContainer;
    private Timeline timeline;
    private GraphicsEngine graphicsEngine;
    private List<SimplePlayer> simplePlayer;
    private GameObjectView simplePlayerView;
    private Game game;

    private double height;
    private double width;

    private Scene myScene;
    private BorderPane myPane;

    private Runnable nextPlayScene;

    public void setNextPlayScene(Runnable nextPlayScene) {
        this.nextPlayScene = nextPlayScene;
    }
    public Runnable getNextPlayScene() {
        return nextPlayScene;
    }

    public GameSeqController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, BorderPane root, double height, double width) {
        this.levelContainer = levelContainer;
        this.graphicsEngine = graphicsEngine;
        this.game = game;
        this.height = height;
        this.width = width;
        this.myScene = scene;
        this.myPane = root;
        setPlayer();

    }

    public void setTimeline(Timeline t) {
        timeline = t;
    }

    public void display() {
        int currentLevel = 0;
        myPane.getChildren().clear();
        for (GameObject g : levelContainer.getCurrentLevel().getAllGameObjects()) {
            GameObjectView gameObjectView = new GameObjectView(g.getImgPath(), g.getX(), g.getY(), g.getWidth(), g.getHeight(), g.getXDirection());
            gameObjectView.convertAttributesToGridBased(width/30, height/20);
            myPane.getChildren().add(gameObjectView);
        }
        simplePlayerView = new GameObjectView(simplePlayer.get(currentLevel).getImgPath(), simplePlayer.get(currentLevel).getX(),
                simplePlayer.get(currentLevel).getY(), simplePlayer.get(currentLevel).getWidth(),
                simplePlayer.get(currentLevel).getHeight(), 20);
        simplePlayerView.convertAttributesToGridBased(width/30, height/20);
        myPane.getChildren().add(simplePlayerView);
        myPane.setVisible(true);
        // TODO: display score board
    }

    private void setPlayer() {
        int currentLevel = 0;
        simplePlayer = new ArrayList<>();
        for (Level l : levelContainer.getLevels())
            for (GameObject g : l.getAllGameObjects())
                if (g.isPlayer()) {
                    simplePlayer.add((SimplePlayer) g);
                    l.removeObject(g);
                    simplePlayerView = new GameObjectView(simplePlayer.get(currentLevel).getImgPath(), simplePlayer.get(currentLevel).getX(),
                            simplePlayer.get(currentLevel).getY(), simplePlayer.get(currentLevel).getWidth(),
                            simplePlayer.get(currentLevel).getHeight(), 20);

                    return;
            }
    }

    public Scene getMyScene() { return myScene; }
    public void setMyScene(Scene scene) { this.myScene = scene; }
    public BorderPane getRoot() { return myPane; }
    public void setRoot(BorderPane root) { this.myPane = root; }
    public double getHeight() { return this.height; }
    public double getWidth() { return width; }
    public SimplePlayer getSimplePlayer() {
        int i = levelContainer.getLevelNum()-1;
        return simplePlayer.get(i);
    }
    public GameObjectView getSimplePlayerView() {  return simplePlayerView; }
    public void setSimplePlayerView(GameObjectView g) { simplePlayerView = g; }

    public void setSimplePlayer(SimplePlayer simplePlayer) {
        this.simplePlayer.add(simplePlayer);
    }

    public GraphicsEngine getGraphicsEngine() { return graphicsEngine; }
    public Game getGame() { return game; }

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

    //TODO: eliminate duplicate code here
    private Image makeImage(String imgPath) {
        return new Image(this.getClass().getClassLoader().getResource(imgPath).toExternalForm());
    }
}
