package menu;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;

/**
 * @author Nicole Lindbergh
 */

public abstract class Page {

    private Stage myStage;
    private Pages myPage;
    private Scene myScene;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return    Page
     */

    public Page(Stage primaryStage, Pages page) {
        myStage = primaryStage;
        myPage = page;
    }

    /**
     * Hypothetically would build the scene
     *
     * @param height the height of the program window
     * @param width the width of the program window.
     *
     * @return    double
     */

    Scene buildScene(int height, int width) throws IOException {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource("css/main.css").toExternalForm());
        return myScene;
    }


    abstract Pane init_Root(int height, int width);
    /**
     * Returns the Stage in use.
     *
     * @return    Stage
     */

    public Stage getMyStage(){return myStage;}

    /**
     * Returns the type of the page.
     *
     * @return    Pages
     */

    public Pages getType() {
        return myPage;
    }

    public Scene getMyScene() {return myScene;}

    /**
     * This nested class extends VBox.
     *
     * @nicol wrote this to format the VBox to her aesthetic.
     *
     * @return    MenuBox
     */

    public class MenuBox extends VBox {
        /**
         * Returns a fully functioning, visible MenuBox with all clickable items
         * in a neat VBox separated by a white Line.
         *
         * @return    MenuBox (with MenuItems inside it)
         */


        public MenuBox(String... items) {
            for (String item : items) {
                Button button = new Button(item);

                getChildren().add(createSeparator());
                button.setId("button");

                button.setOnMouseClicked(event -> {
                    if (item.equals("EXIT")) {
                        System.exit(0);
                    }
                    try {
                        gotoScene(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DuplicateUsernameException e) {
                        e.printStackTrace();
                    } catch (ReadSaveException e) {
                        e.printStackTrace();
                    }
                });
                getChildren().addAll(button, createSeparator());

            }
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

            this.setTranslateX(primaryScreenBounds.getWidth()/2 - 100);
            this.setTranslateY(primaryScreenBounds.getHeight()/2);
        }

        public void addButtons(Button ...buttons) {
            for (Button b : buttons) {
                this.getChildren().add(b);
                this.getChildren().add(createSeparator());
            }
        }

        /**
         * Creates a separator between MenuItems.
         * @return    Line
         */

        private Line createSeparator() {
            Line separator = new Line();
            separator.setEndX(200); //200 is the width of the MenuItem Box, make final static int
            separator.setStroke(Color.WHITE);
            return separator;
        }
    }

    /**
     * A basic equals method for pages.
     *
     * @return    boolean
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(myStage, page.myStage);
    }

    /**
     * Returns the hashcode of a page for equals method.
     *
     * @return    int
     */

    @Override
    public int hashCode() {
        return Objects.hash(myStage);
    }

    /**
     * When a MenuItem is clicked, getScene is called. If the MenuItem's Type matches the
     * name, it creates a Page of that type and builds a Scene out of it.
     *
     * @param name the name of the page the user has clicked on for the program to create.
     * @return    Scene
     */

    public Scene getScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scene myScene = null;

        if (name.equals("Continue")) {
            LevelDirectory ls = new LevelDirectory(myStage, Pages.LevelDirectory, null);
        }
        if (name.equals("Debug")) {
            DebugEnvironment de = new DebugEnvironment(myStage, Pages.Debug);
        }
        if (name.equals("Level 1")) {
            LevelOne ll = new LevelOne(myStage, Pages.BluePrintStage);
        }
        if (name.equals("Customize Player")) {
            CustomMenu cm = new CustomMenu(myStage, Pages.CustomizePlayerMenu, new User("test", "tester", "basicsnake.png", new String[]{"05", "21", "1999"}));
        }
        if (name.equals("New Game")) {
            NewGamePage ng = new NewGamePage(myStage, Pages.FirstTimeCutScene);
        }

        return myScene;
    }

    /**
     * goes to Scene.
     *
     * @return    Scene
     */

    abstract Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException;
}
