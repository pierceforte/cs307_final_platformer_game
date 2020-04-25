package engine.leveldirectory.gamesequence;

import builder.stage.PaneDimensions;
import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.hud.HUDController;
import engine.leveldirectory.level.Level;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class that defines a controller used in the Game. It is
 * extended by the classes GameSeqBuilderController and GameSeqLevelController.
 *
 * @author Jerry Huang, Pierce Forte
 */
public abstract class GameSeqController {
    public static final int FRAME_DURATION = 20;
    private LevelContainer levelContainer;
    private Timeline timeline;
    private SimplePlayer simplePlayer;
    private GameObjectView simplePlayerView;
    private Game game;

    private Pane gamePlayPane;
    private Pane leftPane;
    private BorderPane myPane;
    private GamePlayPane gamePlayPane2;
    private HUDController hudController;

    private double height;
    private double width;
    private Scene myScene;
    private Runnable nextPlayScene;

    public GameSeqController(LevelContainer levelContainer, Game game, Scene scene, BorderPane root, double height, double width) {
        this.levelContainer = levelContainer;
        this.game = game;
        this.height = height;
        this.width = width;
        this.myScene = scene;
        this.myPane = root;

        setPlayer();
        setUpView();
    }

    public void display() {
        gamePlayPane.getChildren().clear();
        for (GameObject g : levelContainer.getCurrentLevel().getAllGameObjects()) {
            GameObjectView gameObjectView = createGameObjectView(g);
            gamePlayPane.getChildren().add(gameObjectView);
        }
        simplePlayerView = createGameObjectView(simplePlayer);
        gamePlayPane.getChildren().add(simplePlayerView);
    }

    private void setPlayer() {
        for (Level l : levelContainer.getLevels())
            for (GameObject g : l.getAllGameObjects())
                if (g.isPlayer()) {
                    simplePlayer = (SimplePlayer) g;
                    l.removeObject(g);
                    simplePlayerView = new GameObjectView(simplePlayer.getImgPath(), simplePlayer.getX(), simplePlayer.getY(),
                            simplePlayer.getWidth(), simplePlayer.getHeight(), 20);
                    return;
                }
    }

    private GameObjectView createGameObjectView(GameObject gameObject) {
        GameObjectView gameObjectView = new GameObjectView(gameObject.getImgPath(), gameObject.getX(), gameObject.getY(),
                gameObject.getWidth(), gameObject.getHeight(), gameObject.getXDirection());
        gameObjectView.convertAttributesToGridBased(width/ PaneDimensions.TILE_WIDTH_FACTOR,
                height/ PaneDimensions.TILE_HEIGHT_FACTOR);
        return gameObjectView;
    }

    public List<GameObjectView> createGameObjectViews(List<GameObject> gameObjects) {
        List<GameObjectView> gameObjectViews = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            gameObjectViews.add(createGameObjectView(gameObject));
        }
        return gameObjectViews;
    }

    private void setUpView() {
        //TODO: read in minX, maxX, minY, and maxY
        PaneDimensions gamePlayDimensions = new PaneDimensions(
                PaneDimensions.DEFAULT_MIN_X, 34, PaneDimensions.DEFAULT_MIN_Y, 20);
        gamePlayPane = new GamePlayPane(gamePlayDimensions);
        hudController = new HUDController(getLevelContainer().getLevelNum(), 0, 5);
        //leftPane.getChildren().add(HUDView);
        myPane.setCenter(gamePlayPane);
        myPane.setLeft(hudController.getView());
    }

    public void setTimeline(Timeline t) {
        timeline = t;
    }

    public Scene getMyScene() {
        return myScene;
    }

    public void setMyScene(Scene scene) {
        this.myScene = scene;
    }

    public BorderPane getRoot() {
        return myPane;
    }

    public void setRoot(BorderPane root) {
        this.myPane = root;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return width;
    }

    public SimplePlayer getSimplePlayer() {
        return simplePlayer;
    }

    public GameObjectView getSimplePlayerView() {
        return simplePlayerView;
    }

    public void setSimplePlayerView(GameObjectView g) {
        simplePlayerView = g;
    }

    public void setSimplePlayer(SimplePlayer simplePlayer) {
        this.simplePlayer = simplePlayer;
    }
    public Game getGame() {
        return game;
    }

    public LevelContainer getLevelContainer() {
        return levelContainer;
    }
    public Timeline getTimeline() {
        return timeline;
    }

    public void pause() {
        timeline.pause();
    }
    public void play() {
        timeline.play();
    }

    public HUDController getHUDController() {
        return hudController;
    }

    public void setNextPlayScene(Runnable nextPlayScene) {
        this.nextPlayScene = nextPlayScene;
    }
    public Runnable getNextPlayScene() {
        return nextPlayScene;
    }
}
