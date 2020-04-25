package pagination;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ResultStage extends Pane {

    private Button back;
    private PageController myPC;

    public ResultStage(PageController PC) {
        this.setId("Results");
        myPC = PC;
        buildBackButton();
    }

    private void buildBackButton() {
        back = new Button("Back to Menu");
        back.setId("back");
        back.setOnMouseClicked(this::handle);
        this.getChildren().add(back);
    }

    private void goBack() throws IOException {
        LevelDirectory ld = new LevelDirectory(myPC.getMyStage(), Pages.LevelDirectory, myPC);
    }

    private void handle(MouseEvent event) {
        try {
            goBack();
        } catch (IOException e) {
        }
    }
}

