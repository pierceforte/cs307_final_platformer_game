package player;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class in the player package
 *
 * @author Jerry Huang
 */
public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // load resources from resource bundle

        stage.show();
    }
}
