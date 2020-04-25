package pagination;

import data.ReadSaveException;
import engine.leveldirectory.gamesequence.GameSeqLevelController;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class WinView extends ResultStage {

    private PageController myPC;
    private GameSeqLevelController controller;

    private final static int PrizeCoins = 200;

    public WinView(PageController PC, boolean finalLevel, GameSeqLevelController Controller) throws ReadSaveException {
        super(PC);
        myPC = PC;
        controller = Controller;

        if (finalLevel) {
            displayFinalWinText();
        }
        else {
            standardDisplayText();
            addNextLevelOption();
        }
        UpdateCoinDisplay();

    }

    private void standardDisplayText() {
        Text t = new Text("You win!");
        t.setId("Welcome");
        this.getChildren().add(t);
    }

    private void displayFinalWinText() {
        Text t = new Text("You won!\nThis was the final level");
        t.setId("Welcome");
        this.getChildren().add(t);
    }

    private void UpdateCoinDisplay() throws ReadSaveException {

        Text s = new Text("You have received "+PrizeCoins+" coins for winning");
        s.setId("centeredText");

        this.getChildren().add(s);

        myPC.getUser().updateScore(PrizeCoins);
    }

    private void addNextLevelOption(){

        Button next = new Button("Next Level");
        next.setTranslateX(600);
        next.setTranslateY(600);

        next.setOnMouseClicked(event -> nextLevel());

        this.getChildren().add(next);

    }

    private void nextLevel() {
        controller.incrementLevel();
    }
}
