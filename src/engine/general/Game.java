package engine.general;

import data.ReadSaveException;
import engine.leveldirectory.gamesequence.*;
import engine.leveldirectory.hud.HUDController;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * The Game class runs the entire game
 *
 * @author Jerry Huang
 */
public class Game {
    // fields for the UI
    private Stage stage;
    private BorderPane root;
    private Scene scene;

    private LevelContainer levelContainer;
    private List<Runnable> buildRunnables;
    private List<Runnable> playRunnables;
    private HUDController hudController;
    private GraphicsEngine graphicsEngine;

    private double height;
    private double width;

    // default constructor
    public Game(Scene scene, BorderPane root, double height, double width) throws NoSuchMethodException,
            ReadSaveException, InstantiationException,
            IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        this.scene = scene;
        this.root = root;

        levelContainer = new LevelContainer(this);
        levelContainer.loadLevels();
        // TODO: read in num of player lives
        hudController = new HUDController(5, 0, getLevelContainer().getLevelNum());
        graphicsEngine = null;
        this.height = height;
        this.width = width;

        startLevelPhase(scene, root, height, width);
    }


    public void startLevelPhase(Scene scene, BorderPane root, double height, double width) {
        /*
        GameSeqLevelController gameSeqLevelController = new GameSeqLevelController(levelContainer, graphicsEngine,
                this, scene, root, height, width);
        gameSeqLevelController.play();
         */
        GameSeqBuilderController gameSeqBuilderController = new GameSeqBuilderController(levelContainer, graphicsEngine,
                this, scene, root, height, width);
    }

    public LevelContainer getLevelContainer() { return levelContainer; }

    public HUDController getHUDController() {
        return hudController;
    }
}