package engine.leveldirectory.hud;

import builder.bank.view.BankView;
import builder.bank.view.ViewPane;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

/**
 * This class is a Pane that handles all of the frontend elements for the HUD feature of the game play stage, along with the
 * necessary methods to update these frontend elements. Accordingly, it is the "View" of the HUD's MVC design.
 *
 * This class is a child of the ViewPane class, which provides a method to all of its subclasses that makes it very easy to
 * create text displays along with an ID for testing.
 *
 * This class is dependent on the current level number, score, and number of lives during the current level of game play.
 *
 * @author Pierce Forte
 */
public class HUDView extends ViewPane {

    public static final String ID = "miniScoreDisplay";
    public static final String RESOURCES_PATH = "text.HUD";
    public static final double WIDTH = BankView.DEFAULT_WIDTH;
    public static final double HEIGHT = BankView.DEFAULT_HEIGHT;

    private ResourceBundle resources;
    private Text levelDisplay;
    private Text scoreDisplay;
    private Text livesDisplay;

    /**
     * The constructor to create a HUDView.
     * @param width the width of the HUDView
     * @param height the height of the HUDView
     * @param level the level number of the associated level
     * @param score the score of the associated level
     * @param lives the lives of the user in the associated level
     */
    public HUDView(double width, double height, int level, int score, int lives) {
        super(width, height);
        setId(ID);
        resources = ResourceBundle.getBundle(RESOURCES_PATH);
        setLevel(level);
        setScore(score);
        setLives(lives);
        this.getChildren().addAll(levelDisplay, scoreDisplay, livesDisplay);
    }

    /**
     * Updates the frontend display of the level number.
     * @param level the level number
     */
    public void setLevel(int level) {
        String text = resources.getString("Level") + "\n" + level;
        if (levelDisplay == null){
            levelDisplay = createText(text, "levelDisplay");
            return;
        }
        levelDisplay.setText(text);
    }

    /**
     * Updates the frontend display of the user's score.
     * @param score the user's score
     */
    public void setScore(int score) {
        String text = resources.getString("Score") + "\n" + score;
        if (scoreDisplay == null) {
            scoreDisplay = createText(text, "scoreDisplay");
            return;
        }
        scoreDisplay.setText(text);
    }

    /**
     * Updates the frontend display of the user's lives.
     * @param lives the user's lives
     */
    public void setLives(int lives) {
        String text = resources.getString("Lives") + "\n" + lives;
        if (livesDisplay == null) {
            livesDisplay = createText(text, "livesDisplay");
            return;
        }
        livesDisplay.setText(text);
    }
}