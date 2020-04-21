package menu;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class in the player package
 *
 * @author Jerry Huang
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        CustomMenu cm = new CustomMenu(stage, Pages.CustomizePlayerMenu);
        stage.show();
    }
}
