package engine.leveldirectory.gamesequence;

import input.KeyInput;
import engine.gameobject.GameObject;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import static javafx.application.Platform.exit;

public class GameSeqLevelController extends GameSeqController implements SceneChanger {

    public static double GRAVITY = 0.01;

    public GameSeqLevelController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, BorderPane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
        setNextScene();
        setupTimeline();
        setUpListeners();
        // TODO: remove player test later and load from memory
        playerTest();
    }

    private void playerTest() {
        SimplePlayer s = new SimplePlayer("images/avatars/babysnake.png", 1d,1d, 10., 5., 0.,0.);
        setSimplePlayer(s);
        getSimplePlayer().setXSpeed(0);
        getSimplePlayer().setYSpeed(0);
        GameObjectView g = new GameObjectView(getSimplePlayer().getImgPath(), getSimplePlayer().getX(),
                getSimplePlayer().getY(), getSimplePlayer().getWidth(),
                getSimplePlayer().getHeight(), 0);
        setSimplePlayerView(g);
    }

    /**
     * Sets the builder stage that follows this level;
     * if this is the last level, the game exits after it ends
     */
    @Override
    public void setNextScene() {
        if (getLevelContainer().getLevelNum() == getLevelContainer().getTotalNumLevels())
            exit();
        super.setNextPlayScene(()->{
            pause();
            getLevelContainer().incrementLevel();
            GameSeqBuilderController builderTemp = new GameSeqBuilderController(getLevelContainer(), getGraphicsEngine(),
                    getGame(), getMyScene(), getRoot(), getHeight(), getWidth());
            builderTemp.play();
        });
    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        Timeline temp = new Timeline();
        temp.setCycleCount(Timeline.INDEFINITE);
        temp.getKeyFrames().add(frame);
        setTimeline(temp);
    }

    public void step() {
        move(getSimplePlayer(), getSimplePlayer().getXSpeed(), getSimplePlayer().getYSpeed());
        boolean flag = playerObjectCollisions();
        if (!flag) {
            gravity(getSimplePlayer());
        }
        super.display();
    }

    private void setUpListeners() {
        getMyScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.N)
                endPhase();
            getSimplePlayer().handleInput(key.getCode());
        });
    }

    // implements player-gameobject collisions
    // if the player is intersecting with an enemy, move it backwards in the x direction in the direction it came from
    private boolean playerObjectCollisions() {
        for (GameObject g : getLevelContainer().getCurrentLevel().getAllGameObjects()) {
            if (intersect(getSimplePlayer(), g)) // checks if the player is intersecting with the current object
            {
                getSimplePlayer().setYSpeed(0);
                getSimplePlayer().setXSpeed(0);
                // TODO: check if g is an enemy
                if (getSimplePlayer().getXDirection() > 0)
                    getSimplePlayerView().setX(getSimplePlayer().getX() - getSimplePlayer().getWidth());
                else
                    getSimplePlayerView().setX(getSimplePlayer().getX() + getSimplePlayer().getWidth());
                if (true) // TODO: check if g is an enemy
                    getGame().getHUDController().lowerLife();
                return true;
            }
        }
        return false;
    }

    // returns true if two gameObjectViews are intersecting
    private boolean intersect(GameObject g1, GameObject g2){
        GameObjectView view1 = createGOView(g1);
        GameObjectView view2 = createGOView(g2);

        Bounds bounds1 = view1.getBoundsInLocal();
        Bounds bounds2 = view2.getBoundsInLocal();

        if (bounds1.intersects(bounds2))
            return true;
        else
            return false;
    }

    private GameObjectView createGOView(GameObject g) {
        GameObjectView gameObjectView = new GameObjectView(g.getImgPath(), g.getX(), g.getY(), g.getWidth(), g.getHeight(), g.getXDirection());
        gameObjectView.setX(gameObjectView.getX() * getWidth()/30);
        gameObjectView.setY(gameObjectView.getY() * getHeight()/20);
        gameObjectView.setFitWidth(getWidth()/30);
        gameObjectView.setFitHeight(getHeight()/20);
        return gameObjectView;
    }

    public void gravity(GameObject gameObject) {
        gameObject.setYSpeed(gameObject.getYSpeed() + (GRAVITY));
    }

    private void move(GameObject gameObject, double xDelta, double yDelta) {
        gameObject.setX(gameObject.getX() + xDelta);
        gameObject.setY(gameObject.getY() + yDelta);
    }

    public void endPhase() {
        if (getGame().getHUDController().getLives() <= 0)
            exit();
        // TODO: if (win)
        getNextPlayScene().run();
    }
}
