package menu;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class NewGamePage extends Page {

    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private boolean light;
    private Pane myRoot;

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private final String STYLESHEET = "menuresources/dark.css";

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public NewGamePage(Stage primaryStage, Pages page) {
        super(primaryStage, page);

    }

    @Override
    Pane init_Root(int height, int width) {

        return null;
    }

    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException {
        return null;
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }
}
