package menu;

import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ResourceBundle;

public class MainMenu extends Page {

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private static final String STYLESHEET = "menuresources/main.css";

    private Stage myStage;
    private Scene myScene;
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
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myStage.setScene(this.buildScene(900,900));
    }

    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Button newgame = myFactory.buildNewGameButton();

        MenuBox myMenuBox = new MenuBox();
        myFactory.addMainMenuButtons(myMenuBox);

        myRoot.getChildren().addAll(newgame, myMenuBox, myFactory.buildTitleText(myResource.getString("MainTitle")));

        return myRoot;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }
}
