package engine.leveldirectory.gamesequence;

<<<<<<< HEAD
import builder.bank.BankModel;
import builder.stage.BuilderStage;
=======
import builder.stage.BuilderPane;
import builder.stage.PaneDimensions;
>>>>>>> temp
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
<<<<<<< HEAD
    private BuilderStage builderStage;
    private List<BankItem> levelBankItems;
    private List<GameObject> levelGameObjects;
    private List<GameObjectView> levelGameObjectViews;
    private Game game;
=======
>>>>>>> temp

    /**
     * Standard constructor
     * @param levelContainer: contains all levels in the game
     * @param game: contains the game the controller is running in
     * @param scene: scene to be modified
     * @param root: root to add/remove objects
     * @param height: height of the screen
     * @param width: width of the screen
     */
<<<<<<< HEAD
    public GameSeqBuilderController(LevelContainer levelContainer, Game game, Scene scene, BorderPane root, double height,
                                    double width) {
        super(levelContainer, game, scene, root, height, width);
        this.game = game;
        myPane = root;
        setNextScene();
        setupTimeline();
        initialize();
=======
    public GameSeqBuilderController(LevelContainer levelContainer, Game game, Scene scene,
                                    BorderPane root, double height, double width) {
        super(levelContainer, game, scene, root, height, width - BankView.DEFAULT_WIDTH);
        initialize();
        setNextScene();
        setupTimeline();
>>>>>>> temp
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
<<<<<<< HEAD
        levelGameObjects = getLevelGameObjects(getLevelContainer().getLevelNum());
        levelGameObjectViews = createGameObjectViews(levelGameObjects);
        levelBankItems = getLevelContainer().getCurrentLevel().getBankItems();
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(levelBankItems, BankModel.DEFAULT_MONEY_AVAILABLE, bankView);
        builderStage = new BuilderStage(getDimensions(), bankController, levelGameObjectViews);
=======
        List<GameObject> gameObjects = getLevelGameObjects(getLevelContainer().getLevelNum());
        List<GameObjectView> gameObjectViews = createGameObjectViews(gameObjects);
        PaneDimensions dimensions = getLevelContainer().getCurrentLevel().getDimensions();
        bankController = getLevelContainer().getCurrentLevel().getBankController();
        builderPane = new BuilderPane(dimensions, bankController, gameObjectViews);
>>>>>>> temp
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
<<<<<<< HEAD
        if (builderStage.isDone()) {
=======
        if (builderPane.isDone()) {
>>>>>>> temp
            endPhase();
            return;
        }
        builderPane.update();
    }

    private void endPhase() {
<<<<<<< HEAD
        List<GameObject> newGameObjects = builderStage.getGameObjects();
        getLevelContainer().getCurrentLevel().addGameObject(newGameObjects);
        myPane.getChildren().removeAll(leftPane, builderStage);
=======
        List<GameObject> newGameObjects = builderPane.getGameObjects();
        getLevelContainer().getCurrentLevel().addGameObject(newGameObjects);
        getRoot().getChildren().removeAll(builderPane, leftPane);
>>>>>>> temp
        this.getTimeline().stop();
        getNextPlayScene().run();
    }

    private void setUpView() {
        leftPane = new Pane();
<<<<<<< HEAD
        leftPane.setId("builderLeftPane");
        leftPane.getChildren().add(new SideBar(getMyScene(), game.getPC(), bankController.getBankView(), game));
        myPane.setCenter(builderStage);
        myPane.setLeft(leftPane);
        leftPane.getChildren().add(builderStage.getPlayButton());
=======
        leftPane.setId(LEFT_PANE_ID);
        leftPane.getChildren().addAll(bankController.getBankView(), builderPane.getPlayButton());
        getRoot().setCenter(builderPane);
        getRoot().setLeft(leftPane);
>>>>>>> temp
    }
}