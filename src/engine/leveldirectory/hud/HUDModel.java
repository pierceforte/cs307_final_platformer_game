package engine.leveldirectory.hud;

/**
 * contains the information to be displayed
 *
 * @author Pierce Forte
 */
public class HUDModel {

    private double score;
    private int lives;

    /**
     * Default Constructor
     */
    public HUDModel(int lives) {
        score = 0;
        this.lives = lives;
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
}
