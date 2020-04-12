package menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class DebugEnvironment extends Page {

    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private static final String STYLESHEET = "menuresources/main.css";

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public DebugEnvironment(Stage primaryStage, Pages page) throws IOException {
        super(primaryStage, page);
        myStage = primaryStage;

        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myStage.setScene(this.buildScene(900,900));

    }

    @Override
    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);



        myRoot.getChildren().addAll();
        return myRoot;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }
}
