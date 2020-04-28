package engine.leveldirectory.hud;

/**
 * This class is a controller that handles all the interaction between the frontend and backend for the HUD feature of
 * the game play stage. Accordingly, it is the "Controller" of the HUD's MVC design.
 *
 * This class is dependent on the current level number, score, and number of lives during the current level of game play.
 *
 * @author Pierce Forte
 */
public class HUDController {

    private HUDModel hudModel;
    private HUDView hudView;

    /**
     * The constructor to create a HUDController.
     * @param level the level number of the associated level
     * @param score the score of the associated level
     * @param lives the lives of the user in the associated level
     */
    public HUDController(int level, int score, int lives) {
        hudModel = new HUDModel(lives);
        hudView = new HUDView(HUDView.WIDTH, HUDView.HEIGHT, level, score, lives);
    }

    /**
     * Returns the backend HUDModel associated with this HUDController.
     * @return the backend HUDModel
     */
    public HUDModel getModel() {
        return hudModel;
    }

    /**
     * Returns the frontend HUDView associated with this HUDController.
     * @return the frontend HUDView
     */
    public HUDView getView() {
        return hudView;
    }

    /**
     * Updates the score in the HUD.
     * @param score the new score
     */
    public void updateScore(int score) {
        hudModel.updateScore(score);
    }

    /**
     * Lowers a life in the HUD.
     */
    public void lowerLife() {
        hudModel.lowerLife();
        hudView.setLives(hudModel.getLives());
    }

    /**
     * Gets the number of lives the user has.
     * @return the number of lives the user has
     */
    public int getLives() {
        return hudModel.getLives();
    }
}
