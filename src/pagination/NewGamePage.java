package pagination;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class NewGamePage extends Page {

    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;

    private Pane myRoot;

    private TextField title;
    private TextField saveloc;
    private TextField birthday;
    private ToggleButton snail;
    private ToggleButton snake;
    private int side;

    private String sideURL;
    private static final int FREE_COINS = 500;
    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private final String STYLESHEET = "css/dark.css";

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public NewGamePage(Stage primaryStage, Pages page) {
        super(primaryStage, page);

        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));
        myScene = this.buildSpecialScene((int) myFactory.getScreenHeight(), (int) myFactory.getScreenWidth());
        myStage.setScene(myScene);
    }

    @Override
    Pane init_Root(int height, int width) {
        myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Text dialogue = myFactory.buildTitleText(myResource.getString("NGTitle"));

        Text prompt = new Text(myResource.getString("NGPrompt"));
        prompt.setFill(Color.WHITESMOKE);
        prompt.setTranslateX(myFactory.getScreenWidth()/2 -450);
        prompt.setTranslateY(myFactory.getScreenHeight()*3/4);

        title = new TextField();
        title.setPromptText(myResource.getString("NGUsername"));
        title.setTranslateY(myFactory.getScreenHeight()*3/4 -50);
        title.setTranslateX(myFactory.getScreenWidth()/2 - 50);

        saveloc = new TextField();
        saveloc.setPromptText(myResource.getString("NGPassword"));
        saveloc.setTranslateX(myFactory.getScreenWidth()/2 - 50);
        saveloc.setTranslateY(myFactory.getScreenHeight()*3/4);

        birthday = new TextField();
        birthday.setPromptText(myResource.getString("Bday"));
        birthday.setTranslateX(myFactory.getScreenWidth()/2 - 50);
        birthday.setTranslateY(myFactory.getScreenHeight()*3/4-100);

        snail = new ToggleButton();
        snail.setId("ChooseSnail");
        snail.setOnMouseClicked(event -> pickSide());
        snake = new ToggleButton();
        snake.setId("ChooseSnake");
        snake.setOnMouseClicked(event -> pickSide());

        Button save = new Button(myResource.getString("Cont"));
        save.setId("LaunchButton");
        save.setOnMouseClicked(event -> {
            try { switchLevelDirectory();
            } catch (ReadSaveException e) { } catch (DuplicateUsernameException e) { } catch (IOException e) {
            }
        });

        Pane backstory = buildTextDisplay();
        backstory.setTranslateX(myFactory.getScreenWidth()/2 - 450);
        backstory.setTranslateY(myFactory.getScreenHeight()/2);

        myRoot.getChildren().addAll(dialogue, prompt, title, saveloc, save, backstory, birthday, snake, snail);
        return myRoot;
    }

    private Pane buildTextDisplay() {
        Pane ret = new Pane();
        ret.setId("ViewText");

        TextFlow flow = new TextFlow();

        Text hist = new Text(myResource.getString("NGHistory"));
        hist.setFill(Color.WHITESMOKE);
        hist.setFont(Font.font("Helvetica", FontPosture.ITALIC, 15));

        Text hist2 = new Text(myResource.getString("NGHistory2"));
        hist2.setFill(Color.WHITESMOKE);
        hist2.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));

        Text BigChoice = new Text(myResource.getString("NGChoose"));
        BigChoice.setFill(Color.WHITESMOKE);
        BigChoice.setFont(Font.font("Helvetica", FontPosture.ITALIC, 15));

        Text BiggerChoice = new Text(myResource.getString("Choice"));
        BiggerChoice.setFill(Color.WHITESMOKE);
        BiggerChoice.setFont(Font.font("Helvetica", FontPosture.ITALIC, 15));

        flow.getChildren().addAll(hist, hist2, BigChoice, BiggerChoice);
        ret.getChildren().add(flow);
        return ret;
    }

    private void pickSide() {
        if (snail.isSelected()) {
            side = 0;
            sideURL = "images/avatars/wilbur.png";
        }
        if (snake.isSelected()) {
            side = 1;
            sideURL = "images/avatars/basicsnake.png";
        }
    }

    private void switchLevelDirectory() throws ReadSaveException, DuplicateUsernameException, IOException {
        User u = saveInformation();
        PageController myPC = new PageController(u, myStage);
        LevelDirectory ll = new LevelDirectory(myStage, Pages.LevelDirectory, myPC);
    }

    private User saveInformation() {
        String imagePath = processSelections();
        User u = null;
        try {
            u = new User(title.getText(), saveloc.getText(), imagePath, birthday.getText().split(" "));
            u.setType(side);
            u.changeAvatar(sideURL);
            u.replaceScore(FREE_COINS);
        } catch (DuplicateUsernameException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(myResource.getString("Whoops"));
            alert.setHeaderText(myResource.getString("Whoops"));
            alert.setContentText(myResource.getString("BadName"));
        }
        return u;
    }

    private String processSelections() {
        String imagePath = null;
        if (side == 0) {
            imagePath = Avatars.valueOf("Snail1").getimgpath();
        }
        else if (side == 1) {
            imagePath = Avatars.valueOf("Snake1").getimgpath();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(myResource.getString("Whoops"));
            alert.setHeaderText(myResource.getString("Whoops"));
            alert.setContentText(myResource.getString("Pick"));
        }
        return imagePath;
    }
    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException {
        return getScene(name);
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }
}
