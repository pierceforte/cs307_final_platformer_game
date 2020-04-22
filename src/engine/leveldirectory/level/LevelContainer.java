package engine.leveldirectory.level;

import data.ReadSaveException;
import data.levels.LevelData;
import engine.general.Game;
import engine.leveldirectory.gamesequence.GameSeqController;
import engine.leveldirectory.gamesequence.ScoreDisplay;
import engine.leveldirectory.gamesequence.StepInterface;

import java.lang.reflect.InvocationTargetException;
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
    private final Game game;
    private ScoreDisplay scoreDisplay;

    /**
     * constructor for the LevelContainer
     * @param game: Game to be loaded
     */
    public LevelContainer(Game game) {
        this.game = game;
        currentLevel = 0;
    }

    public void loadLevels() throws ReadSaveException, ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        LevelData levelData = new LevelData();
        // TODO: int numLevels = levelData.getNumLevels()
        int numLevels = levelData.getNumLevels();
        List<Level> levels = new ArrayList<>();
        for (int i = 1; i <= numLevels; i++) {
            Level levelTemp = new Level(levelData.getSavedLevel(i));
            levels.add(levelTemp);
        }
        this.levels = levels;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public int getLevelNum() { return currentLevel; }

    public Level getCurrentLevel() {
        return levels.get(currentLevel);
    }

    public List<Level> getLevels() { return levels; }

    public void incrementLevel() {
        currentLevel++;
    }

    public Game getGame() {
        return this.game;
    }

}


