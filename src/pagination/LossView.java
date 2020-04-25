package pagination;

import engine.leveldirectory.gamesequence.GameSeqLevelController;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class LossView extends ResultStage {


    private final ResourceBundle myResource = ResourceBundle.getBundle("text.WinLoss");

    public LossView(PageController PC,  GameSeqLevelController Controller) {
        super(PC);
        standardDisplayText();
    }

    private void standardDisplayText() {
        Text t = new Text(myResource.getString("StandardLoss"));
        t.setId("Welcome");
        this.getChildren().add(t);
    }

}
