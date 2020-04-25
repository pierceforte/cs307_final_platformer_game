package engine.leveldirectory.hud;

import builder.bank.view.BankView;
import builder.bank.view.ViewPane;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class HUDView extends ViewPane {

    public static final String ID = "miniScoreDisplay";
    public static final String RESOURCES_PATH = "text.HUD";
    public static final double WIDTH = BankView.DEFAULT_WIDTH;
    public static final double HEIGHT = BankView.DEFAULT_HEIGHT;

    private ResourceBundle resources;
    private Text levelDisplay;
    private Text scoreDisplay;
    private Text livesDisplay;

    public HUDView(double width, double height, int level, int score, int lives) {
        super(width, height);
        setId(ID);
        resources = ResourceBundle.getBundle(RESOURCES_PATH);
        setLevel(level);
        setScore(score);
        setLives(lives);
        this.getChildren().addAll(levelDisplay, scoreDisplay, livesDisplay);
    }

    public void setLevel(int level) {
        String text = resources.getString("Level") + "\n" + level;
        if (levelDisplay == null){
            levelDisplay = createText(text, "levelDisplay");
            return;
        }
        levelDisplay.setText(text);
    }

    public void setScore(int score) {
        String text = resources.getString("Score") + "\n" + score;
        if (scoreDisplay == null) {
            scoreDisplay = createText(text, "scoreDisplay");
            return;
        }
        scoreDisplay.setText(text);
    }

    public void setLives(int lives) {
        String text = resources.getString("Lives") + "\n" + lives;
        if (livesDisplay == null) {
            livesDisplay = createText(text, "livesDisplay");
            return;
        }
        livesDisplay.setText(text);
    }
}