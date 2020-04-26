package engine.leveldirectory.gamesequence;

import builder.stage.BuilderPane;
import builder.stage.PaneDimensions;
import builder.bank.BankController;
import builder.bank.view.BankView;
import engine.gameobject.GameObject;
import engine.general.Game;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pagination.SideBar;

import java.util.List;

/**
 * This class controls the flow of the builder stage.
 *
 * @author Jerry Huang, Pierce Forte
 */
public class GameSeqBuilderController extends GameSeqController implements SceneChanger {

    public static final String LEFT_PANE_ID = "builderLeftPane";

    private Pane leftPane;
    private BuilderPane builderPane;
    private BankController bankController;
    private Game game;

    /**
     * Standard constructor
     * @param levelContainer: contains all levels in the game
     * @param game: contains the game the controller is running in
     * @param scene: scene to be modified
     * @param root: root to add/remove objects
     * @param height: height of the screen
     * @param width: width of the screen
     */
    public GameSeqBuilderController(LevelContainer levelContainer, Game game, Scene scene,
                                    BorderPane root, double height, double width) {
        super(levelContainer, game, scene, root, height, width - BankView.DEFAULT_WIDTH);
        this.game = game;
        setNextScene();
        setupTimeline();
        initialize();
        getTimeline().play();
    }

    /**
     * Sets the next stage to be run
     * Assumes that a builder stage is always followed by its corresponding play stage
     */
    @Override
    public void setNextScene() {
        super.setNextPlayScene(()->{
            pause();
            GameSeqLevelController playTemp = new GameSeqLevelController(getLevelContainer(),
                    getGame(), getMyScene(), getRoot(), getHeight(), getWidth());
            playTemp.play();
        });
    }

    private void initialize() {
        List<GameObject> gameObjects = getLevelGameObjects(getLevelContainer().getLevelNum());
        List<GameObjectView> gameObjectViews = createGameObjectViews(gameObjects);
        PaneDimensions dimensions = getLevelContainer().getCurrentLevel().getDimensions();
        bankController = new BankController(getLevelContainer().getCurrentLevel().getBankController());
        builderPane = new BuilderPane(dimensions, bankController, gameObjectViews);
        setUpView();
    }

    private void setupTimeline() {
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DURATION), e -> step());
        Timeline temp = new Timeline();
        temp.setCycleCount(Timeline.INDEFINITE);
        temp.getKeyFrames().add(frame);
        setTimeline(temp);
    }

    private void step() {
        if (builderPane.isDone()) {
            endPhase();
            return;
        }
        builderPane.update();
    }

    private void endPhase() {
        List<GameObject> newGameObjects = builderPane.getGameObjects();
        getLevelContainer().getCurrentLevel().addGameObject(newGameObjects);
        getRoot().getChildren().removeAll(builderPane, leftPane);
        this.getTimeline().stop();
        getNextPlayScene().run();
    }

    private void setUpView() {
        leftPane = new Pane();
        leftPane.setId(LEFT_PANE_ID);
        leftPane.getChildren().add(new SideBar(getMyScene(), game.getPC(), bankController.getBankView(), game));
        leftPane.getChildren().add(builderPane.getPlayButton());
        getRoot().setCenter(builderPane);
        getRoot().setLeft(leftPane);
    }
}
