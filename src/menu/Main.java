package menu;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;
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

        //User test = new User("CustomMenu", "tester", "basicsnake.png", new String[]{"05", "21", "1999"});
        //test.replaceScore(500);
        //CustomMenu cm = new CustomMenu(stage, Pages.CustomizePlayerMenu, test);
        MainMenu m = new MainMenu(stage);
        stage.show();
    }
}
