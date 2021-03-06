package pagination;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ResourceBundle;

public class MainMenu extends Page {

    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private Stage myStage;
    private PageBuilder myFactory;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return Page
     */
    public MainMenu(Stage primaryStage) throws IOException {
        super(primaryStage, Pages.MainMenu);
        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myStage.setScene(this.buildScene((int) myFactory.getScreenHeight(), (int) myFactory.getScreenWidth()));
    }

    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Button newgame = new Button(myResource.getString("NewGame"));
        newgame.setOnMouseClicked(event -> {
            try {
                gotoScene(myResource.getString("NewGame"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ReadSaveException e) {
                e.printStackTrace();
            } catch (DuplicateUsernameException e) {
                e.printStackTrace();
            }
        });
        newgame.setId("LaunchButton");

        MenuBox myMenuBox = new MenuBox();
        myFactory.addMainMenuButtons(myMenuBox);

        myRoot.getChildren().addAll(newgame, myMenuBox);

        return myRoot;
    }

    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException {
            return getScene(name);
    }
}
