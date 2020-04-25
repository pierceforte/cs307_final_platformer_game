package engine.leveldirectory.hud;

public class HUDController {

    private HUDModel hudModel;
    private HUDView hudView;

    public HUDController(int level, int score, int lives) {
        hudModel = new HUDModel(lives);
        hudView = new HUDView(HUDView.WIDTH, HUDView.HEIGHT, level, score, lives);
    }

    public HUDModel getModel() {
        return hudModel;
    }

    public HUDView getView() {
        return hudView;
    }

    public void updateScore(int score) {
        hudModel.updateScore(score);
    }

    public void lowerLife() {
        hudModel.lowerLife();
        hudView.setLives(hudModel.getLives());
    }

    public int getLives() {
        return hudModel.getLives();
    }
}
