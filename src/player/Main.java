package player;

import javafx.application.Application;
import javafx.stage.Stage;
import menu.*;

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
        stage.setScene(myMenu.buildScene(1000,1000));
        stage.show();
    }
}
