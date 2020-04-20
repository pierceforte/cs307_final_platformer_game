package menu;

import data.ReadSaveException;
import engine.general.Game;
import engine.leveldirectory.gamesequence.GameSeqBuilderController;
import engine.leveldirectory.gamesequence.GameSeqController;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class LevelOne extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private boolean light;

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private String STYLESHEET;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public LevelOne(Stage primaryStage, Pages page) throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        super(primaryStage, page);
        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        STYLESHEET = "menuresources/light.css";
        light = true;
        myScene = this.buildSpecialScene((int) myFactory.getScreenHeight(), (int) myFactory.getScreenWidth());
        myStage.setScene(myScene);

    }

    @Override
    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);


        Button lightbutton = new Button();
        lightbutton.setId("LightButton");
        lightbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (light) {
                    STYLESHEET = "menuresources/dark.css";
                    myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
                    light = false;
                }
                else {
                    STYLESHEET = "menuresources/light.css";
                    myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
                    light = true;
                }
            }
        });

        myRoot.getChildren().addAll(lightbutton);
        return myRoot;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return null;
    }
    Scene buildSpecialScene(int height, int width) throws NoSuchMethodException, ReadSaveException,
            InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        /*
        GameSeqBuilderController gameSeqBuilderController = new GameSeqBuilderController(
                new LevelContainer(null, null, null),
                new GraphicsEngine(null, null, null),
                null, myScene, myRoot, screenheight, screenwidth);
        gameSeqBuilderController.play();
        */
        Game game = new Game(myScene, myRoot, height, width);
        game.startLevelPhase(myScene, myRoot, height, width);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }
}
