package engine.leveldirectory.gamesequence;

import builder.bank.BankModel;
import builder.stage.BuilderStage;
import builder.bank.BankController;
import builder.bank.BankItem;
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
    private BorderPane myPane;
    private Pane leftPane;
    private BankController bankController;
    private BuilderStage builderStage;
    private List<BankItem> levelBankItems;
    private List<GameObject> levelGameObjects;
    private List<GameObjectView> levelGameObjectViews;
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
    public GameSeqBuilderController(LevelContainer levelContainer, Game game, Scene scene, BorderPane root, double height,
                                    double width) {
        super(levelContainer, game, scene, root, height, width);
        this.game = game;
        myPane = root;
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
        levelGameObjects = getLevelGameObjects(getLevelContainer().getLevelNum());
        levelGameObjectViews = createGameObjectViews(levelGameObjects);
        levelBankItems = getLevelContainer().getCurrentLevel().getBankItems();
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(levelBankItems, BankModel.DEFAULT_MONEY_AVAILABLE, bankView);
        builderStage = new BuilderStage(getDimensions(), bankController, levelGameObjectViews);
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
        if (builderStage.isDone()) {
            endPhase();
        }
        else {
            builderStage.update();
        }
    }

    private void endPhase() {
        List<GameObject> newGameObjects = builderStage.getGameObjects();
        getLevelContainer().getCurrentLevel().addGameObject(newGameObjects);
        myPane.getChildren().removeAll(leftPane, builderStage);
        this.getTimeline().stop();
        getNextPlayScene().run();
    }

    private void setUpView() {
        leftPane = new Pane();
        leftPane.setId("builderLeftPane");
        leftPane.getChildren().add(new SideBar(getMyScene(), game.getPC(), bankController.getBankView(), game));
        myPane.setCenter(builderStage);
        myPane.setLeft(leftPane);
        leftPane.getChildren().add(builderStage.getPlayButton());
    }
}