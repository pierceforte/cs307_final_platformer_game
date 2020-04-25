package engine.leveldirectory.gamesequence;

import engine.UserController;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Enemy;
import engine.gameobject.opponent.Opponent;
import engine.gameobject.player.Player;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
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

    public static final double GRAVITY = 0.01;
    public static final double MAX_SCREEN_DEPTH = 23d;
    private double initialX;
    private double initialY;

    public GameSeqLevelController(LevelContainer levelContainer, Game game, Scene scene, BorderPane root, double height, double width) {
        super(levelContainer, game, scene, root, height, width);
        setNextScene();
        setupTimeline();
        setUpListeners();
        // TODO: remove player test later and load from memory
        initializeSimplePlayer();
    }

    private void initializeSimplePlayer() {
        UserController userController = new UserController()
        SimplePlayer s = new SimplePlayer("images/avatars/babysnake.png", 1d,1d, 8., 5., 0.,0.);
        setSimplePlayer(s);
        getSimplePlayer().setXSpeed(0);
        getSimplePlayer().setYSpeed(0);
        GameObjectView g = new GameObjectView(getSimplePlayer().getImgPath(), getSimplePlayer().getX(),
                getSimplePlayer().getY(), getSimplePlayer().getWidth(),
                getSimplePlayer().getHeight(), 0);
        setSimplePlayerView(g);
        initialX = 8.;
        initialY = 5.;
    }

    /**
     * Sets the builder stage that follows this level;
     * if this is the last level, the game exits after it ends
     */
    @Override
    public void setNextScene() {
        super.setNextPlayScene(()->{
            pause();
            getRoot().getChildren().clear();
            getLevelContainer().incrementLevel();
            if (getLevelContainer().getLevelNum() == getLevelContainer().getTotalNumLevels()) {
                System.out.println("Congrats you won!");
                System.exit(0);
            }
            //myPane.getChildren().removeAll(leftPane, gamePlayPane);
            GameSeqBuilderController builderTemp = new GameSeqBuilderController(getLevelContainer(),
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

    private void step() {
        move(getSimplePlayer(), getSimplePlayer().getXSpeed(), getSimplePlayer().getYSpeed());
        if (getSimplePlayer().getY() > MAX_SCREEN_DEPTH) {
            getHUDController().lowerLife();
            respawn();
        }
        boolean flag = playerObjectCollisions();
        if (!flag)
            gravity(getSimplePlayer());
        updateEnemyPositions();
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
            if (intersect(getSimplePlayer(), g)) {
                getSimplePlayer().setYSpeed(0);
                getSimplePlayer().setXSpeed(0);
                isWin(g);
                isEnemy(g);
                isDangerousPlatform(g);
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

    private void gravity(GameObject gameObject) {
        gameObject.setYSpeed(gameObject.getYSpeed() + (GRAVITY));
    }

    private void move(GameObject gameObject, double xDelta, double yDelta) {
        gameObject.setX(gameObject.getX() + xDelta);
        gameObject.setY(gameObject.getY() + yDelta);
    }

    // if hit enemy, lose life + knockback
    private void isEnemy(GameObject gameObject) {
        if (gameObject instanceof Opponent) {
            getHUDController().lowerLife();
            respawn();
        }
    }

    // if you hit a checkpoint you win
    private void isWin(GameObject gameObject) {
        if (gameObject.getImgPath().equals("images/objects/checkpoint.png")) {
            endPhase();
        }
    }

    // lose life + knock back if hit on salt
    private void isDangerousPlatform(GameObject gameObject) {
        if (gameObject.getImgPath().equals("images/objects/salt.png")) {
            getHUDController().lowerLife();
            respawn();
            return;
        }
    }

    private void updateEnemyPositions() {
        for (GameObject g : getLevelContainer().getCurrentLevel().getAllGameObjects())
            if (g instanceof Enemy) {
                if (g.getY() > MAX_SCREEN_DEPTH) {
                    ((Enemy) g).respawn();
                } else if (!checkAttached((Enemy) g)) {
                    g.setY(g.getY()+0.1d);
                } else
                    ((Enemy) g).updateLogic(getSimplePlayer());
            }
    }

    // checks if the enemy is attached to something that's not a player or enemy
    private boolean checkAttached(Enemy enemy) {
        for (GameObject g : getLevelContainer().getCurrentLevel().getAllGameObjects()) {
            if (intersect(g, enemy) && g != enemy && !(g instanceof Enemy) && !(g instanceof Player))
                return true;
        }
        return false;
    }

    // after the player loses a life, he is sent back to the starting location
    private void respawn() {
        getSimplePlayer().setX(initialX);
        getSimplePlayer().setY(initialY);
    }

    // ends the play stage
    private void endPhase() {
        this.getTimeline().stop();
        if (getHUDController().getLives() <= 0)
            exit();
        getNextPlayScene().run();
    }
}
