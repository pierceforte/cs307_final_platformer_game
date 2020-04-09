package engine.leveldirectory.level;

import engine.general.Game;

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

    private int score; // can be updated for more complicated scores -- how should we keep track of scores/points?

    private final Game game; // current game -- should not be modified by by this class

    /**
     * constructor for the LevelContainer
     * @param game: Game to be loaded
     */
    public LevelContainer(Game game) {
        levels = new game.getLevels();
        currentLevel = 0;
        this.game = game;
    }


    /**
     * Adds the level passed in the formal parameter
     * @param level
     */
    public void addLevel(Level level) {
        levels.add(level);
    }

    /**
     * @return the current level
     */
    public Level getLevel() {
        return levels.get(currentLevel);
    }

    /**
     * goes to the next level
     */
    public void incrementLevel() {
        currentLevel++;
    }

    /**
     * standard get method
     */
    public Game getGame() {
        return this.game;
    }
}


