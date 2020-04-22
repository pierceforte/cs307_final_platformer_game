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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import static javafx.application.Platform.exit;

public class GameSeqLevelController extends GameSeqController {


    private KeyInput keyInput;

    public GameSeqLevelController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game, Scene scene, BorderPane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
        setUpRunnable();
        setupTimeline();
        setUpListeners();

        // TODO: remove player test later
        playerTest();
    }
    private void playerTest() {
        SimplePlayer s = new SimplePlayer("images/avatars/babysnake.png", 1d,1d, 10., 5., 200.,200.);
        setSimplePlayer(s);

        GameObjectView g = new GameObjectView(getSimplePlayer().getImgPath(), getSimplePlayer().getX(),
                getSimplePlayer().getY(), getSimplePlayer().getWidth(),
                getSimplePlayer().getHeight(), 20);
        setSimplePlayerView(g);
    }

    private void setUpRunnable() {
        // TODO: check if this isn't the last level
        super.setNextPlayScene(()->{
            pause();
            super.getLevelContainer().incrementLevel();
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
        boolean flag = playerObjectCollisions();
        if (!flag)
            playerGravity();
        super.display();
    }

    private void setUpListeners() {
        getMyScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            getSimplePlayer().handleInput(key.getCode());
        });

    }

    // if the player isn't intersecting with anything, move it down
    private void playerGravity() {
        getSimplePlayer().setY(getSimplePlayer().getY() - getSimplePlayer().getHeight() / 4);
    }

    // implements player-gameobject collisions
    // if the player is intersecting with an enemy, move it backwards in the x direction in the direction it came from
    private boolean playerObjectCollisions() {
        for (GameObject g : getLevelContainer().getCurrentLevel().getAllGameObjects()) {
            if (intersect(getSimplePlayer(), g)) ; // checks if the player is intersecting with the current object
            { // TODO: check if g is an enemy
                if (getSimplePlayer().getXDirection() > 0)
                    getSimplePlayerView().setX(getSimplePlayer().getX() - getSimplePlayer().getWidth());
                else
                    getSimplePlayerView().setX(getSimplePlayer().getX() + getSimplePlayer().getWidth());
                if (true) // TODO: check if g is an enemy
                    getGame().getScoreDisplay().loseLife();
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

    // creates a GameObjectView when given a GameObject
    private GameObjectView createGOView(GameObject g) {
        GameObjectView gameObjectView = new GameObjectView(g.getImgPath(), g.getX(), g.getY(), g.getWidth(), g.getHeight(), g.getXDirection());
        gameObjectView.setX(gameObjectView.getX() * getWidth()/30);
        gameObjectView.setY(gameObjectView.getY() * getHeight()/20);
        gameObjectView.setFitWidth(getWidth()/30);
        gameObjectView.setFitHeight(getHeight()/20);
        return gameObjectView;
    }



    public void endPhase() {
        if (getGame().getScoreDisplay().getLives() <= 0)
            exit();
        // TODO: if (win)
            getNextPlayScene().run();
    }
}
