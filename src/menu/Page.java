package menu;
import engine.leveldirectory.gamesequence.SceneChanger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Nicole Lindbergh
 */

public abstract class Page {
    private static final int GAME_SIZE = 900;

    private Stage myStage;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return    Page
     */

    public Page(Stage primaryStage) {
        myStage = primaryStage;
    }

    /**
     * Hypothetically would build the scene
     *
     * @param height the height of the program window
     * @param width the width of the program window.
     *
     * @return    double
     */

    abstract Scene buildScene(int height, int width) throws IOException;

    /**
     * Returns the Stage in use.
     *
     * @return    Stage
     */

    public Stage getMyStage(){return myStage;}

    /**
     * Returns the type of the page.
     *
     * @return    String
     */

    abstract String getType();

    /**
     * Returns the command function of the page.
     *
     * @return    String
     */

    abstract String getCommand();

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
                //button.setPrefSize(300, 40);
                getChildren().add(createSeparator());
                button.setId("button");

                button.setOnMouseClicked(event -> {
                    if (item.equals("EXIT")) {
                        System.exit(0);
                    }
                    try {
                        myStage.setScene(gotoScene(item));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                getChildren().addAll(button, createSeparator());

            }
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

    public Scene getScene(String name) throws IOException {
        Scene myScene = null;
        if (name.equals("")) {
            myScene = new Scene(myStage.getScene().getRoot());
        }
        return myScene;
    }

    /**
     * goes to Scene.
     *
     * @return    Scene
     */

    abstract Scene gotoScene(String name) throws IOException;

    void setFullScreen() {
        myStage.setFullScreen(true);
    }
}
