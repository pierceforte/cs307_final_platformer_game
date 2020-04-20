package menu;

import data.ReadSaveException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class MainMenu extends Page {

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private static final String STYLESHEET = "menuresources/main.css";

    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private int bheight;
    private int bwidth;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return Page
     */
    public MainMenu(Stage primaryStage) throws IOException {
        super(primaryStage, Pages.MainMenu);
        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myStage.setScene(this.buildScene(bheight, bwidth));
    }

    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Button newgame = myFactory.buildNewGameButton();

        MenuBox myMenuBox = new MenuBox();
        myFactory.addMainMenuButtons(myMenuBox);


        myRoot.getChildren().addAll(newgame, myMenuBox);

        return myRoot;
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
