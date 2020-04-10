package engine.leveldirectory.level;

import engine.general.Game;
import engine.leveldirectory.gamesequence.GameSequence;
import engine.leveldirectory.gamesequence.GameSequenceController;
import engine.leveldirectory.gamesequence.ScoreDisplay;
import engine.leveldirectory.gamesequence.StepInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The LevelContainer class holds all the levels and transition scenes for the current game
 *
 * @author Jerry Huang
 */
public class LevelContainer {
    private List<Level> levels;
    private int currentLevel;
    private final Game game; // current game -- should not be modified by by this class
    private StepInterface stepFunction;
    private GameSequenceController gameSequenceController;
    private ScoreDisplay scoreDisplay;

    /**
     * constructor for the LevelContainer
     * @param game: Game to be loaded
     */
    public LevelContainer(Game game, ScoreDisplay scoreDisplay, StepInterface stepFunction) {
        levels = new ArrayList<>();
        currentLevel = 0;
        this.game = game;
        this.stepFunction = stepFunction;
        this.scoreDisplay = scoreDisplay;
    }

    public void loadAllLevelsFromData() {


    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public Level getLevel() {
        return levels.get(currentLevel);
    }

    public List<Level> getLevels() { return levels; }

    public void incrementLevel() {
        currentLevel++;
    }

    public Game getGame() {
        return this.game;
    }

    public StepInterface getStepFunction() { return stepFunction; }

    public GameSequenceController getGameSequenceController() { return gameSequenceController; }

    public void setGameSequenceController(GameSequenceController g) { gameSequenceController = g; }
}


