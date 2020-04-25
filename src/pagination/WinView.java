package pagination;

import data.ReadSaveException;
import engine.leveldirectory.gamesequence.GameSeqLevelController;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class WinView extends ResultStage {

    private PageController myPC;
    private GameSeqLevelController controller;

    private final static int PrizeCoins = 200;
    private final ResourceBundle myResource = ResourceBundle.getBundle("text.WinLoss");

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
        Text t = new Text(myResource.getString("StandardWin"));
        t.setId("Welcome");
        this.getChildren().add(t);
    }

    private void displayFinalWinText() {
        Text t = new Text(myResource.getString("FinalWin"));
        t.setId("Welcome");
        this.getChildren().add(t);
    }

    private void UpdateCoinDisplay() throws ReadSaveException {
        Text s = new Text(myResource.getString("Received")+PrizeCoins+myResource.getString("Received2"));
        s.setId("centeredText");
        this.getChildren().add(s);
        myPC.getUser().updateScore(PrizeCoins);
    }

    private void addNextLevelOption(){
        Button next = new Button(myResource.getString("next"));
        next.setId("next");
        next.setOnMouseClicked(event -> nextLevel());
        this.getChildren().add(next);

    }

    private void nextLevel() {
        controller.incrementLevel();
    }
}
