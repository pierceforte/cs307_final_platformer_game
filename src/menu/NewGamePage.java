package menu;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private final String STYLESHEET = "menuresources/dark.css";

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

        Button save = new Button(myResource.getString("Cont"));
        save.setId("LaunchButton");
        save.setOnMouseClicked(event -> switchLevelDirectory());

        Pane backstory = buildTextDisplay();
        backstory.setTranslateX(myFactory.getScreenWidth()/2 - 450);
        backstory.setTranslateY(myFactory.getScreenHeight()/2);

        myRoot.getChildren().addAll(dialogue, prompt, title, saveloc, save, backstory);
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


    private void switchLevelDirectory() {
        saveInformation();
    }
    private void saveInformation() {
        User u = new User(title.getText(), saveloc.getText(), )
    }

    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException {
        return getScene(name);
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }
}
