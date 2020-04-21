package menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.TOP_CENTER;

public class CustomMenu extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private ToggleButton[] myOptions;
    private ToggleButton[] myOpponents;
    private int selected;

    private Map<String, ImageView> avatars;

    private ImageView avatar;
    private Pane myRoot;

    private static final ResourceBundle deets = ResourceBundle.getBundle("menu.menuresources.Details");

    private final String Snake = "Snake";
    private final String Snail = "Snail";
    private final int imageSize = 128;
    private final int spacing = 50;

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
        avatars = new HashMap<>();

        //read from data whether they are snails or snakes
        //find and display avatar

        /* DISCOVER
         * -Snails or Snakes
         * -Score
         *  - purchase item from Pierce
         * -their current avatar
         *
         * PASS BACK
         * -new avatar
         * -new score (if applicable)
         *
         */
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

        myOptions = new ToggleButton[6];
        myOpponents = new ToggleButton[6];

        for (int x = 0; x < 6; x++) {
            if (Snakes) {
                buildOptionButton(Snake, x);
                buildOpponentButton(Snail, x);}
            if (Snails) {
                buildOptionButton(Snail, x);
                buildOpponentButton(Snake, x); }
        }

        Button back = new Button("Back");
        back.setOnMouseClicked(event -> leaveStage());

        myRoot.getChildren().addAll(myOptions);
        myRoot.getChildren().addAll(myOpponents);
        myRoot.getChildren().add(back);
        return myRoot;
    }

    private void buildOptionButton(String type, int x) {
        myOptions[x] = new ToggleButton();

        int y = x + 1;
        String key = type+y;
        avatars.put(key, Avatars.valueOf(key).getImg());

        myOptions[x].setId(key);
        myOptions[x].setTranslateX(myFactory.getScreenWidth()-imageSize-spacing);
        myOptions[x].setTranslateY(y*imageSize+spacing);
        myOptions[x].setOnMouseClicked(event -> action(key));
    }

    private void buildOpponentButton(String type, int x) {
        myOpponents[x] = new ToggleButton();

        int y = x + 1;
        String key = type+y;
        avatars.put(key, Avatars.valueOf(key).getImg());

        myOpponents[x].setId(type+y);
        myOpponents[x].setTranslateY(y*imageSize+spacing);
        myOpponents[x].setTranslateX(spacing);

        myOpponents[x].setOnMouseClicked(event -> action(key));

    }

    private void action(String x) {
        removeCurrentAvatar();
        displayAvatar(avatars.get(x));
        displayDetails(x);
    }
    private void removeNodes() {
        myRoot.getChildren().remove(myRoot.lookup("ViewText"));
        myRoot.getChildren().remove(myRoot.lookup("Name"));
        myRoot.getChildren().remove(myRoot.lookup("Deet"));
    }

    private void displayDetails(String x) {
        removeNodes();

        Pane c = new Pane();
        c.setId("ViewText");

        Text title = new Text(deets.getString(x+"Title"));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setId("Name");
        title.setFill(Color.WHITESMOKE);
        title.setTranslateY(myFactory.getScreenHeight()*4/5 + spacing);
        title.setTranslateX(myFactory.getScreenWidth()/2 - 2*imageSize + 20);

        Text deet = new Text(deets.getString(x+"Deet"));

        deet.setTextAlignment(TextAlignment.CENTER);
        deet.setId("Deet");
        deet.setTranslateY(myFactory.getScreenHeight()*4/5 +spacing+20);
        deet.setFill(Color.WHITESMOKE);
        deet.setTranslateX(myFactory.getScreenWidth()/2 - 2*imageSize+20);

        //c.getChildren().addAll(title, deet);

        c.setTranslateX(myFactory.getScreenWidth()/2 - 3*imageSize);
        c.setTranslateY(myFactory.getScreenHeight()*4/5);

        myRoot.getChildren().addAll(c, title, deet);
    }


    private void attemptPurchase() {

    }

    private void displayAvatar(ImageView img) {
        avatar = img;
        avatar.setTranslateX(myFactory.getScreenWidth()/2 - imageSize);
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
