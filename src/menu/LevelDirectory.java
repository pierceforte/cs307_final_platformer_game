package menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class LevelDirectory extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private static final String STYLESHEET = "menuresources/main.css";

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param levelDirectory
     * @return Page
     */
    public LevelDirectory(Stage primaryStage, Pages levelDirectory) throws IOException {
        super(primaryStage, Pages.LevelDirectory);
        myStage=primaryStage;
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myStage.setScene(this.buildScene(900,900));
    }

    @Override
    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Text t = myFactory.buildTitleText(myResource.getString("Choose"));

        MenuBox myBox = new MenuBox("Level 1", "Level 2", "Level 3", "Debug");
        myBox.setId("MenuBox");

        myRoot.getChildren().addAll(t, myBox);
        return myRoot;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }
}
