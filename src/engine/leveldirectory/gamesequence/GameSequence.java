package engine.leveldirectory.gamesequence;

import engine.general.Game;
import engine.leveldirectory.hud.HUDController;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import javafx.scene.Scene;

/**
 * This class describes the timeline logic in the game
 *
 * Other classes in this package may modify fields to reflect
 * changes in the Game's flow
 *
 * @author Jerry Huang
 */
public class GameSequence {
    private InfoBundle infoBundle;
    private LevelContainer levelContainer;
    private GraphicsEngine graphicsEngine;
    private SequenceChanger sequenceChanger;

    private HUDController hudController;

    /**
     * Instantiates all relevant backend objects when the Game is first started or loaded
     */
    public GameSequence(Scene scene, Game game, GraphicsEngine graphicsEngine) {
        this.graphicsEngine = graphicsEngine;

        hudController = null; // get scorebar from the graphics engine

        //TODO: properly implement code below
        /*
        levelContainer = new LevelContainer(game, scoreBoardController, new StepInterface()); // need to finish this line
         */
    }

    /**
     * To be called by the Game Player
     */
    /*
    public void startTimeline() {
        levelContainer.getGameSeqController().play();
    }

    public void pauseTimeline() {
        levelContainer.getGameSeqController().pause();
    }
     */


    //public Pane getGameView() {
    //    return graphicsEngine.getView();
    //}

    public HUDController getHudController() {
        return hudController;
    }

    public SequenceChanger getGameSequenceChanger() {
        return sequenceChanger;
    }

    public LevelContainer getLevelContainer() {
        return levelContainer;
    }

    public GraphicsEngine getGraphicsEngine() {
        return graphicsEngine;
    }
}
