package engine.leveldirectory.hud;

public class HUDController {

    private HUDModel hudModel;
    private HUDView hudView;

    public HUDController(int lives, int score, int level) {
        hudModel = new HUDModel(lives);
        hudView = new HUDView(HUDView.WIDTH, HUDView.HEIGHT, lives, score, level);
    }

    public void updateScore(int score) {
        hudModel.updateScore(score);
    }

    public void lowerLife() {
        hudModel.lowerLife();
        hudView.setLives(hudModel.getLives() - 1);
    }

    public int getLives() {
        return hudModel.getLives();
    }
}
