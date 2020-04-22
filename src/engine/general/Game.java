package engine.general;

import data.ReadSaveException;
import data.levels.LevelData;
import engine.gameobject.GameObject;
import engine.leveldirectory.gamesequence.*;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.Level;
import engine.leveldirectory.level.LevelContainer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
    private ScoreDisplay scoreDisplay;
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
        scoreDisplay = new ScoreDisplay(this);
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

    public ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }
}