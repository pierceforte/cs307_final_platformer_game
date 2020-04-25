package pagination;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class DebugEnvironment extends Page {

    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;


    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private String STYLESHEET;
    private boolean light;

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
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        STYLESHEET = "css/light.css";
        light = true;
        myStage.setScene(this.buildSpecialScene((int) primaryScreenBounds.getHeight(),(int) primaryScreenBounds.getWidth()));

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

        myRoot.getChildren().addAll(lightbutton);
        return myRoot;
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }

    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException {
        return getScene(name);
    }
}
