package engine.leveldirectory.gamesequence;

import builder.stage.BuilderStage;
import builder.stage.PaneDimensions;
import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.view.BankView;
import data.ReadSaveException;
import data.levels.LevelData;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import engine.view.GameObjectView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.lang.reflect.InvocationTargetException;
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
    private PaneDimensions levelDimensions;

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
        super(levelContainer, game, scene, root, height, width - 200);
        myPane = root;
        setNextScene();
        setupTimeline();
        initialize(scene, root);
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

    private void initialize(Scene scene, BorderPane root) {
        // TODO: initialize from stored info
        setMyScene(scene);
        setRoot(root);

        // TODO: handle exceptions @Ben
        /*
        LevelData levelData = new LevelData();
        try {
            levelBankItems = levelData.getBank(getLevelContainer().getLevelNum());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        levelGameObjects = getLevelGameObjects(getLevelContainer().getLevelNum());
        levelGameObjectViews = createGameObjectViews(levelGameObjects);
        levelBankItems = getLevelContainer().getCurrentLevel().getBankItems();
        levelDimensions = getLevelContainer().getCurrentLevel().getDimensions();
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);
        bankController = new BankController(levelBankItems, 100, bankView);
        builderStage = new BuilderStage(levelDimensions, bankController, levelGameObjectViews);

        // TODO: handle this stuff within BuilderStage
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
            List<GameObject> temp = builderStage.getGameObjects();
            getLevelContainer().getCurrentLevel().addGameObject(temp);
            getRoot().getChildren().remove(builderStage);
            bankController.getBankView().removeFromRoot();
            endPhase();
        }
        else {
            builderStage.update();
        }
    }

    private void endPhase() {
        this.getTimeline().stop();
        myPane.getChildren().remove(leftPane);
        getNextPlayScene().run();
    }

    private void setUpView() {
        leftPane = new Pane();
        leftPane.setId("builderLeftPane");
        leftPane.getChildren().add(bankController.getBankView());
        myPane.setCenter(builderStage);
        myPane.setLeft(leftPane);
        leftPane.getChildren().add(builderStage.getPlayButton());
    }
}
