package pagination;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
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
    public void start(Stage stage) throws IOException, ReadSaveException, DuplicateUsernameException {
        MainMenu m = new MainMenu(stage);
        stage.show();
    }
}
