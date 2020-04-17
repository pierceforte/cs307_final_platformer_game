package menu;

import engine.leveldirectory.gamesequence.GameSequenceController;
import engine.leveldirectory.graphicsengine.GraphicsEngine;
import engine.leveldirectory.level.LevelContainer;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class LevelOne extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private boolean light;

    private double screenwidth;
    private double screenheight;
    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private String STYLESHEET;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public LevelOne(Stage primaryStage, Pages page) {
        super(primaryStage, page);
        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        STYLESHEET = "menuresources/light.css";
        light = true;
        screenheight = primaryScreenBounds.getHeight();
        screenwidth = primaryScreenBounds.getWidth();
        myScene = this.buildSpecialScene((int)primaryScreenBounds.getHeight(), (int) primaryScreenBounds.getWidth());
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
    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        GameSequenceController gameSequenceController = new GameSequenceController(
                new LevelContainer(null, null, null),
                new GraphicsEngine(),
                null, myScene, myRoot, screenheight, screenwidth);
        gameSequenceController.play();
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }
}
