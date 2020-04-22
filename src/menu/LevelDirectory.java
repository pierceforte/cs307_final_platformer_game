package menu;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class LevelDirectory extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private boolean light;

    private User user;
    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private String STYLESHEET;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param levelDirectory
     * @return Page
     */
    public LevelDirectory(Stage primaryStage, Pages levelDirectory, User user) throws IOException {
        super(primaryStage, levelDirectory);
        myStage=primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        STYLESHEET = "css/light.css";
        light = true;
        this.user = user;
        myStage.setScene(this.buildSpecialScene((int) myFactory.getScreenHeight(),(int) myFactory.getScreenWidth()));
    }

    @Override
    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Text t = myFactory.buildTitleText(myResource.getString("Choose"));

        MenuBox myBox = new MenuBox("Level 1", "Level 2", "Level 3", "Debug");
        myBox.setId("MenuBox");

        Button customize = new Button(myResource.getString("Custom"));
        customize.setId("LaunchButton");
        customize.setOnMouseClicked(event -> goCustomize());

        Button lightbutton = new Button();
        lightbutton.setId("LightButton");
        lightbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (light) {
                    STYLESHEET = "css/dark.css";
                    myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
                    light = false;
                }
                else {
                    STYLESHEET = "css/light.css";
                    myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
                    light = true;
                }
            }
        });

        myRoot.getChildren().addAll(t, myBox, lightbutton, customize);
        return myRoot;
    }

    private void goCustomize() {
        CustomMenu cm = new CustomMenu(myStage, Pages.CustomizePlayerMenu, user);
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }

    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException {
        try {
            return getScene(name);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
