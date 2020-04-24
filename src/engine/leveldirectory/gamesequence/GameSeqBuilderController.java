package engine.leveldirectory.gamesequence;

import builder.stage.BuilderStage;
import builder.stage.GridDimensions;
import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankView;
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

import java.util.List;

public class GameSeqBuilderController extends GameSeqController implements SceneChanger{
    private BorderPane myPane;
    private Pane leftPane;
    private BankController bankController;
    private BuilderStage builderStage;
    private List<GameObjectView> levelGameObjectViews;

    public GameSeqBuilderController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game,
                                    Scene scene, BorderPane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width - 200);
        myPane = root;
        setNextScene();
        setupTimeline();
        initialize(scene, root);
        getTimeline().play();
    }

    @Override
    public void setNextScene() {
        System.out.println(getLevelContainer().getCurrentLevel().getAllGameObjects().size());
        super.setNextPlayScene(()->{
            //getLevelContainer().getCurrentLevel().addGameObject(builderStage.getGameObjects());
            pause();
            System.out.println(getLevelContainer().getCurrentLevel().getAllGameObjects().size());
            GameSeqLevelController playTemp = new GameSeqLevelController(getLevelContainer(), getGraphicsEngine(),
                    getGame(), getMyScene(), getRoot(), getHeight(), getWidth());
            System.out.println(getLevelContainer().getCurrentLevel().getAllGameObjects().size());
            playTemp.play();
        });
    }

    public void initialize(Scene scene, BorderPane root) {
        // TODO: initialize from stored info
        setMyScene(scene);
        setRoot(root);
        // TODO: remove hardcoding here
        levelGameObjectViews = getLevelGameObjects(0);

        Raccoon raccoon = new Raccoon("images/avatars/raccoon.png",1d,1d,1., 1., 10.);
        Mongoose mongoose = new Mongoose("images/avatars/mongoose.png",1d,1d, 1., 1., 10.);
        BankItem one = new BankItem(new Raccoon(raccoon),  2, 2, 10);
        BankItem two = new BankItem(new Mongoose(mongoose), 1, 1, 20);
        BankItem three = new BankItem(new Mongoose(mongoose), 1, 1, 30);
        BankItem four = new BankItem(new Raccoon(raccoon), 1, 1, 40);
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);

        bankController = new BankController(List.of(one, two, three, four), 10000, bankView);
        GridDimensions builderStageDimensions = new GridDimensions(getWidth(), getHeight(),
                0, 34, 0, 20);
        builderStage = new BuilderStage(builderStageDimensions, bankController, levelGameObjectViews);

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

    public void step() {
        if (builderStage.isDone()) {
            List<GameObject> temp = builderStage.getGameObjects();
            getLevelContainer().getCurrentLevel().addGameObject(temp);
            myPane.getChildren().remove(builderStage);
            bankController.getBankView().removeFromRoot();
            endPhase();
        }
        else {
            builderStage.update();
        }
    }

    public void endPhase() {
        this.getTimeline().stop();
        myPane.getChildren().remove(leftPane);
        getNextPlayScene().run();
    }

    private List<GameObjectView> getLevelGameObjects(int level) {
        List<GameObject> gameObjects = getLevelContainer().getLevels().get(level).getAllGameObjects();
        return createGameObjectViews(gameObjects);
    }

    private void setUpView() {
        leftPane = new Pane();
        leftPane.setId("leftPane");
        leftPane.getChildren().add(bankController.getBankView());
        myPane.setCenter(builderStage);
        myPane.setLeft(leftPane);
        leftPane.getChildren().add(builderStage.getPlayButton());
    }
}
