package engine.leveldirectory.hud;

import builder.bank.BankView;
import builder.bank.ViewPane;
import javafx.scene.text.Text;

import java.util.List;
import java.util.ResourceBundle;

public class HUDView extends ViewPane {

    public static final String HEART_IMAGE_PATH = "heart.png";
    public static final double WIDTH = BankView.DEFAULT_WIDTH;
    public static final double HEIGHT = BankView.DEFAULT_HEIGHT;

    private ResourceBundle resources;
    private Text levelDisplay;
    private Text scoreDisplay;
    private Text livesDisplay;


    public HUDView(double width, double height, int level, int score, int lives) {
        super(width, height);
        setId("hudView");
        resources = ResourceBundle.getBundle("text.HUD");
        createDisplays(level, score, lives);
        this.getChildren().addAll(levelDisplay, scoreDisplay, livesDisplay);
    }

    public void setLevel(int level) {
        levelDisplay.setText(resources.getString("Level") + "\n" + level);
    }

    public void setScore(int score) {
        scoreDisplay.setText(resources.getString("Score") + "\n" + score);
    }

    public void setLives(int lives) {
        livesDisplay.setText(resources.getString("Lives") + "\n" + lives);
    }

    private void createDisplays(int level, int score, int lives) {
        levelDisplay = createText("", "levelDisplay");
        setLevel(level);
        scoreDisplay = createText("", "scoreDisplay");
        setScore(score);
        livesDisplay = createText("", "livesDisplay");
        setLives(lives);
    }
}
