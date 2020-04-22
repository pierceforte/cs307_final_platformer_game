package engine.leveldirectory.gamesequence;

import engine.general.Game;
import engine.leveldirectory.level.LevelContainer;

/**
 * contains the information to be displayed
 */
public class ScoreDisplay {
    String filePath;
    private Game game;
    private double score;
    private double lives;

    private double xPos;
    private double yPos;

    /**
     * Default Constructor
     */
    public ScoreDisplay(Game game) {
        this.game = game;
        score = 0;
        lives = 5;
        xPos = 0;
        yPos = 0;

        // TODO: add file path for scoreboard
        filePath = "";
    }

    public void updateScore(int points) { score += points; }

    public Game getGame() { return game; }
    public double getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public double getScore() { return score; }
    public void loseLife() { lives--; }
    public void setScore(double score) { this.score = score; }
    public String getFilePath() {
        return filePath;
    }
}
