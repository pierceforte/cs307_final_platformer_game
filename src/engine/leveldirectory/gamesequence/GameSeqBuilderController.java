package engine.leveldirectory.gamesequence;

import builder.BuilderStage;
import builder.bank.BankController;
import builder.bank.BankItem;
import builder.bank.BankView;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.Raccoon;
import engine.gameobject.platform.StationaryPlatform;
import engine.gameobject.player.SimplePlayer;
import engine.general.Game;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import input.KeyInput;
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

    public GameSeqBuilderController(LevelContainer levelContainer, GraphicsEngine graphicsEngine, Game game,
                                    Scene scene, BorderPane root, double height, double width) {
        super(levelContainer, graphicsEngine, game, scene, root, height, width);
        myPane = root;
        System.out.println(getLevelContainer().getCurrentLevel().getAllGameObjects().size() + "size 1");
        setNextScene();
        setupTimeline();
        initialize(scene, root);
        getTimeline().play();
    }

    @Override
    public void setNextScene() {
        super.setNextPlayScene(()->{
            pause();
            GameSeqLevelController playTemp = new GameSeqLevelController(getLevelContainer(), getGraphicsEngine(),
                    getGame(), getMyScene(), getRoot(), getHeight(), getWidth());
            playTemp.play();
        });
    }

    public void initialize(Scene scene, BorderPane root) {
        // TODO: initialize from stored info
        setMyScene(scene);
        setRoot(root);

        Raccoon raccoon = new Raccoon("images/avatars/raccoon.png",1d,1d,1., 1., 10.);
        Mongoose mongoose = new Mongoose("images/avatars/mongoose.png",1d,1d, 1., 1., 10.);
        BankItem one = new BankItem(new Raccoon(raccoon),  30, 30, 10);
        BankItem two = new BankItem(new Mongoose(mongoose), 30, 30, 20);
        BankItem three = new BankItem(new Mongoose(mongoose), 30, 30, 30);
        BankItem four = new BankItem(new Raccoon(raccoon), 30, 30, 40);
        BankView bankView = new BankView(BankView.DEFAULT_WIDTH, BankView.DEFAULT_HEIGHT);

        bankController = new BankController(List.of(one, two, three, four), 10000, bankView);
        builderStage = new BuilderStage(bankController, getWidth(), getHeight());
        // TODO: remove hardcoding here
        addGameObjectsToBuilderStage(0);
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
            getRoot().getChildren().remove(builderStage);
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

    private void addGameObjectsToBuilderStage(int level) {
        List<GameObject> gameObjects = getLevelContainer().getLevels().get(level).getAllGameObjects();
        List<GameObjectView> gameObjectViews = createGameObjectViews(gameObjects);
        builderStage.addGameObjectViews(gameObjectViews);
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
