package engine.leveldirectory.gamesequence;

import builder.stage.GridDimensions;
import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.Level;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.Timeline;
import javafx.scene.Scene;
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
    private Pane gamePlayPane;
    private Pane leftPane;

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
            GameObjectView gameObjectView = createGameObjectView(g);

            if (g.getImgPath().equals("images/avatars/raccoon.png")) {
                System.out.println("X:" + g.getX());
                System.out.println("Y: " + g.getY());
                System.out.println("Height" + g.getHeight());
                g.setX(10.);
                g.setY(5.);
            }
            myPane.getChildren().add(gameObjectView);
        }
        simplePlayerView = createGameObjectView(simplePlayer.get(currentLevel));
        myPane.getChildren().add(simplePlayerView);
        System.out.println(myPane.getChildren().size());
        //myPane.setVisible(true);
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

    public GameObjectView createGameObjectView(GameObject gameObject) {
        GameObjectView gameObjectView = new GameObjectView(gameObject.getImgPath(), gameObject.getX(),
                gameObject.getY(), gameObject.getWidth(), gameObject.getHeight(), gameObject.getXDirection());

        gameObjectView.convertAttributesToGridBased(width/ GridDimensions.TILE_WIDTH_FACTOR,
                height/GridDimensions.TILE_HEIGHT_FACTOR);
        return gameObjectView;
    }

    public List<GameObjectView> createGameObjectViews(List<GameObject> gameObjects) {
        List<GameObjectView> gameObjectViews = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            gameObjectViews.add(createGameObjectView(gameObject));
        }
        return gameObjectViews;
    }

    public Scene getMyScene() { return myScene; }
    public void setMyScene(Scene scene) { this.myScene = scene; }
    public BorderPane getRoot() { return myPane; }
    public void setRoot(BorderPane root) { this.myPane = root; }
    public double getHeight() { return this.height; }
    public double getWidth() { return width; }
    public SimplePlayer getSimplePlayer() {
        int i = levelContainer.getLevelNum();
        return simplePlayer.get(i);
    }
    public GameObjectView getSimplePlayerView() {  return simplePlayerView; }
    public void setSimplePlayerView(GameObjectView g) { simplePlayerView = g; }
    public void setSimplePlayer(SimplePlayer simplePlayer) {
        this.simplePlayer.add(simplePlayer);
    }
    public GraphicsEngine getGraphicsEngine() { return graphicsEngine; }
    public Game getGame() { return game; }
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
}
