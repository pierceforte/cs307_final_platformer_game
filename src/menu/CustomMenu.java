package menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomMenu extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private ToggleButton[] myOptions;
    private ToggleButton[] myOpponents;
    private int selected;

    private HashMap<String, ImageView> avatars;

    private ImageView avatar;
    private Pane myRoot;

    private boolean Snails;
    private boolean Snakes;

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private static final String STYLESHEET = "menuresources/cm.css";
    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public CustomMenu(Stage primaryStage, Pages page) {
        super(primaryStage, page);
        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));

        selected = 0;

        //read from data whether they are snails or snakes
        //find and display avatar

        //plus scorebar and haspurchaseitem

        //Hardcode:

        Snails = false;
        Snakes = true;

        myScene = this.buildSpecialScene((int) myFactory.getScreenHeight(), (int) myFactory.getScreenWidth());
        myStage.setScene(myScene);
    }

    @Override
    Pane init_Root(int height, int width) {
        myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Image img = new Image("basicsnake.png");
        displayAvatar(new ImageView(img););

        myOptions = new ToggleButton[6];
        myOpponents = new ToggleButton[6];

        int y = 1;
        for (int x = 0; x < 6; x++) {
            myOptions[x] = new ToggleButton();
            myOpponents[x] = new ToggleButton();

            if (Snakes) {
                myOptions[x].setId("Snake"+y);
                myOptions[x].setTranslateX(myFactory.getScreenWidth()-128-50);
                myOptions[x].setTranslateY(y*128+50);

                myOpponents[x].setId("Snail"+y);
                myOpponents[x].setTranslateY(y*128+50);
                myOpponents[x].setTranslateX(50);
            }
            if (Snails) {
                myOptions[x].setId("Snail"+y);
                myOptions[x].setTranslateY(y*128+50);
                myOptions[x].setTranslateX(50);

                myOpponents[x].setId("Snake"+y);
                myOpponents[x].setTranslateX(myFactory.getScreenWidth()-128-50);
                myOpponents[x].setTranslateY(y*128+50);
            }
            y++;
        }

        Button back = new Button("Back");
        back.setOnMouseClicked(event -> leaveStage());

        myRoot.getChildren().addAll(myOptions);
        myRoot.getChildren().addAll(myOpponents);
        myRoot.getChildren().add(back);
        return myRoot;
    }

    private HashMap<String, ImageView> init_avatars(){
        HashMap<String, ImageView> hm = new HashMap<>();


        return hm;
    }
    private void setSelected(int x) {

    }

    private void action(String x) {
        removeCurrentAvatar();
        displayAvatar();
    }

    private void displayAvatar(ImageView img) {
        avatar = img;
        avatar.setTranslateX(myFactory.getScreenWidth()/2 - 128);
        avatar.setTranslateY(myFactory.getScreenHeight()/2);
        myRoot.getChildren().add(avatar);

    }
    private void removeCurrentAvatar() {
        myRoot.getChildren().remove(avatar);
    }

    private void leaveStage() {
        for (int x = 0; x < 6; x++) {
            if (myOptions[x].isSelected()) {
                selected = x;
            }
        }

        if (selected != 0) {
            //tell user info to update in data
        }
        else {
            rejectAttempttoLeave();
        }
    }
    private void rejectAttempttoLeave() {

    }



    @Override
    Scene gotoScene(String name) throws IOException {
        return null;
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }

}
