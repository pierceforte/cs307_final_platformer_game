package engine.leveldirectory.hud;

/**
 * contains the information to be displayed
 */
public class HUDModel {

    public static final int INIT_SCORE = 0;
    public static final int INIT_LIVES = 0;

    String filePath;
    private double score;
    private int lives;

    /**
     * Default Constructor
     */
    public HUDModel(int lives) {
        score = 0;
        this.lives = lives;

        // TODO: add file path for scoreboard
        filePath = "";
    }

    public void updateScore(int points) {
        score += points;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public double getScore() {
        return score;
    }

    public void lowerLife() {
        lives--;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFilePath() {
        return filePath;
    }
}
