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
        hudModel = new HUDModel(level, score, lives);
        hudView = new HUDView(HUDView.WIDTH, HUDView.HEIGHT, hudModel);
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
     * Updates the level in the HUD.
     * @param level the new score
     */
    public void updateLevel(int level) {
        hudModel.setLevel(level);
        hudView.update(hudModel);
    }

    /**
     * Updates the score in the HUD.
     * @param score the new score
     */
    public void updateScore(int score) {
        hudModel.updateScore(score);
        hudView.update(hudModel);
    }

    /**
     * Lowers a life in the HUD.
     */
    public void lowerLife() {
        hudModel.lowerLife();
        hudView.update(hudModel);
    }

    /**
     * Gets the number of lives the user has.
     * @return the number of lives the user has
     */
    public int getLives() {
        return hudModel.getLives();
    }
}
