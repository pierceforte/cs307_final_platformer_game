package pagination;


import data.ReadSaveException;
import engine.general.Game;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class SavedGame extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private PageController myPC;

    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private String STYLESHEET;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public SavedGame(Stage primaryStage, Pages page, PageController PC) throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        super(primaryStage, page);
        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myPC = PC;
        STYLESHEET = "css/light.css";
        myScene = this.buildSpecialScene((int) myFactory.getScreenHeight(), (int) myFactory.getScreenWidth());
        myStage.setScene(myScene);

    }

    @Override
    BorderPane init_Root(int height, int width) {
        BorderPane myRoot = new BorderPane();
        myRoot.setPrefSize(width, height);
        return myRoot;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }

    Scene buildSpecialScene(int height, int width) throws NoSuchMethodException, ReadSaveException,
            InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        BorderPane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);

        Game game = new Game(myScene, myRoot, myPC, height, width);
        game.getLevelContainer().getCurrentLevel();
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());

        game.startLevelPhase();

        return myScene;
    }
}
