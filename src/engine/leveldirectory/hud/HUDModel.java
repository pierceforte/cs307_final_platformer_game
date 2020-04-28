package engine.leveldirectory.hud;

/**
 * This class is the backend for the bank feature of the game play stage. Accordingly, it is the "Model" of the
 * HUD's MVC design.
 *
 * This class is dependent on the current number of lives during the current level of game play.
 *
 * @author Pierce Forte
 */
public class HUDModel {

    private double score;
    private int lives;

    /**
     * The constructor to create a HUDModel.
     * @param lives the lives of the user in the associated level
     */
    public HUDModel(int lives) {
        score = 0;
        this.lives = lives;
    }

    /**
     * Updates the score of the user.
     * @param points the points to be added to the score
     */
    public void updateScore(int points) {
        score += points;
    }

    /**
     * Returns the number of lives of the user.
     * @return the number of lives of the user
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the number of lives of the user.
     * @param lives the new number of lives of the user
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Returns the score of the user.
     * @return the score of the user
     */
    public double getScore() {
        return score;
    }

    /**
     * Removes a life from the user.
     */
    public void lowerLife() {
        lives--;
    }

    /**
     * Sets the score of the user.
     * @param score the new score of the user
     */
    public void setScore(double score) {
        this.score = score;
    }
}
