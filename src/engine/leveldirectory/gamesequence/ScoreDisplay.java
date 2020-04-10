package engine.leveldirectory.gamesequence;

import engine.general.Game;
import engine.leveldirectory.level.LevelContainer;

/**
 * contains the information to be displayed
 */
public class ScoreDisplay {
    private Game game;
    private LevelContainer levelContainer;
    private int score;
    private int lives;

    /**
     * Default Constructor
     */
    public ScoreDisplay(Game game) {
        this.game = game;
        score = 0;
        levelContainer = new LevelContainer(game);
    }

    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }

    public LevelContainer getLevelContainer() {
        return levelContainer;
    }
    public void setLevelContainer(LevelContainer lc) {
        this.levelContainer = lc;
    }
}
