package menu;

import data.ReadSaveException;
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
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class LevelDirectory extends Page {
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
     * @param levelDirectory
     * @return Page
     */
    public LevelDirectory(Stage primaryStage, Pages levelDirectory) throws IOException {
        super(primaryStage, levelDirectory);
        myStage=primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        STYLESHEET = "menuresources/light.css";
        light = true;
        myStage.setScene(this.buildSpecialScene((int) primaryScreenBounds.getHeight(),(int) primaryScreenBounds.getWidth()));
    }

    @Override
    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Text t = myFactory.buildTitleText(myResource.getString("Choose"));

        MenuBox myBox = new MenuBox("Level 1", "Level 2", "Level 3", "Debug");
        myBox.setId("MenuBox");

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

        myRoot.getChildren().addAll(t, myBox, lightbutton);
        return myRoot;
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        try {
            return getScene(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ReadSaveException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
