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
        // load resources from resource bundle
        stage.setTitle("Snizards vs. Snizards");
        MainMenu myMenu = new MainMenu(stage);
        stage.setScene(myMenu.buildScene(900,900));
        stage.show();
    }
}
