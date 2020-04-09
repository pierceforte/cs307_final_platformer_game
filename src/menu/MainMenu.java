package menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.*;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainMenu extends Page {

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources");
    private static final String STYLESHEET = "menuresources/main.css";


    private Stage myStage;
    private Scene myScene;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return Page
     */
    public MainMenu(Stage primaryStage) {
        super(primaryStage);
        myStage = primaryStage;
    }

    @Override
    Scene buildScene(int height, int width) throws IOException {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);


        myRoot.getChildren().addAll();

        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource("resources/main.css")
                .toExternalForm());

        return myScene;
    }

    @Override
    String getType() {
        return null;
    }

    @Override
    String getCommand() {
        return null;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return null;
    }
}
