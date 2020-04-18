package engine.general;

import engine.leveldirectory.gamesequence.GameSequence;
import engine.leveldirectory.gamesequence.GameSequenceController;
import engine.leveldirectory.gamesequence.ScoreDisplay;
import engine.leveldirectory.gamesequence.SequenceChanger;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;

/**
 * The Game class creates an object that represents the current state of the game
 *
 * @author Jerry Huang
 */
public abstract class Game {
    private LevelContainer levelContainer;
    private ScoreDisplay scoreDisplay;
    private SequenceChanger sequenceChanger;
    private GraphicsEngine graphicsEngine;

    public Game(GameSequence gameSequence) {
        this.levelContainer = gameSequence.getLevelContainer();
        //this.scoreDisplay = gameSequence.getScorebar();
        this.sequenceChanger = gameSequence.getGameSequenceChanger();
        this.graphicsEngine = gameSequence.getGraphicsEngine();
    }

    public LevelContainer getLevelContainer() { return levelContainer; }

    public ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }

    public SequenceChanger getGameSequenceController() { return sequenceChanger; }

    public GraphicsEngine getGraphicsEngine() { return graphicsEngine; }
}