package engine.general;

import data.ReadSaveException;
import engine.leveldirectory.gamesequence.*;
import engine.leveldirectory.hud.HUDController;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pagination.PageController;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * The Game class runs the actual game itself; it includes things such as the individual
 * levels and builder/play stages.
 *
 * @author Jerry Huang
 */
public class Game {
    private LevelContainer levelContainer;
    private PageController pageController;
    private Scene scene;
    private BorderPane root;
    private double height;
    private double width;
    private PageController myPC;

    /**
     * Constructor to create a game
     * @param scene: scene to be modified
     * @param root: contains which objects are added/removed; determines what is displayed on the screen
     * @param height: height of the screen
     * @param width: width of the screen
     */
    public Game(int num, Scene scene, BorderPane root, PageController pageController, double height, double width) throws NoSuchMethodException, ReadSaveException,
            InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        levelContainer = new LevelContainer(this);
        levelContainer.loadLevels();
        levelContainer.setCurrentLevel(num);
        this.pageController = pageController;
        this.scene = scene;
        this.root = root;
        this.height = height;
        this.width = width;
        myPC = pageController;
        //startLevelPhase(scene, this.root, height, width);

        startLevelPhase();

    }

    public PageController getPC() {
        return myPC;
    }
    /**
     * Starts the builder stage of the first level
     */
    public void startLevelPhase() {
        GameSeqBuilderController gameSeqBuilderController = new GameSeqBuilderController(levelContainer,
                this, scene, root, height, width);
    }

    /*
    standard get method
     */
    public LevelContainer getLevelContainer() { return levelContainer; }

    public PageController getPageController() {
        return pageController;
    }
}