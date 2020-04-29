package engine.leveldirectory.hud;

/**
 * contains the information to be displayed
 *
 * @author Pierce Forte
 */
public class HUDModel {

    private int score;
    private int lives;
    private int level;

    /**
     * Default Constructor
     */
    public HUDModel(int level, int score, int lives) {
        this.level = level;
        this.score = score;
        this.lives = lives;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
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

    public int getScore() {
        return score;
    }

    public void lowerLife() {
        lives--;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
