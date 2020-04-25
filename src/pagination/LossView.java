package pagination;

import engine.leveldirectory.gamesequence.GameSeqLevelController;
import javafx.scene.text.Text;

public class LossView extends ResultStage {


    public LossView(PageController PC,  GameSeqLevelController Controller) {
        super(PC);
        standardDisplayText();
    }

    private void standardDisplayText() {
        Text t = new Text("You've lost :(");
        t.setId("Welcome");
        this.getChildren().add(t);
    }

}
