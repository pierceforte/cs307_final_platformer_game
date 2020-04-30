package engine.leveldirectory.hud;

public class HUDController {

    private HUDModel hudModel;
    private HUDView hudView;

    public HUDController(int level, int score, int lives) {
        hudModel = new HUDModel(level, score, lives);
        hudView = new HUDView(HUDView.WIDTH, HUDView.HEIGHT, hudModel);
    }

    public HUDModel getModel() {
        return hudModel;
    }

    public HUDView getView() {
        return hudView;
    }

    public void updateLevel(int level) {
        hudModel.setLevel(level);
        hudView.update(hudModel);
    }
    public void updateScore(int score) {
        hudModel.updateScore(score);
        hudView.update(hudModel);
    }

    public void lowerLife() {
        hudModel.lowerLife();
        hudView.update(hudModel);
    }

    public int getLives() {
        return hudModel.getLives();
    }
}
