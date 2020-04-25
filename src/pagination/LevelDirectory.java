package pagination;

import data.ErrorLogger;
import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;

import engine.general.Game;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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
    private PageController myPC;

    private User user;
    private final String STYLESHEET = "css/light.css";
    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private final int num_Levels = 3;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param levelDirectory
     * @return Page
     */
    public LevelDirectory(Stage primaryStage, Pages levelDirectory, PageController pControl) throws IOException {
        super(primaryStage, levelDirectory);
        myStage=primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myPC = pControl;
        myStage.setTitle(myResource.getString("MainTitle"));
        this.user = pControl.getUser();
        myStage.setScene(this.buildSpecialScene((int) myFactory.getScreenHeight(),(int) myFactory.getScreenWidth()));
    }

    @Override
    Pane init_Root(int height, int width) {
        Pane myRoot = new Pane();
        myRoot.setPrefSize(width, height);

        Text t = myFactory.buildTitleText(myResource.getString("Choose"));

        MenuBox myBox = constructLevelDirectory();
        myBox.setId("MenuBox");

        Button customize = new Button(myResource.getString("Custom"));
        customize.setId("LaunchButton");
        customize.setOnMouseClicked(event -> goCustomize());

        myRoot.getChildren().addAll(t, myBox, customize);
        return myRoot;
    }

    private MenuBox constructLevelDirectory() {
        MenuBox myBox = new MenuBox();
        myBox.setId("MenuBox");

        Button LevelButton = new Button(myResource.getString("StoryMode"));
        LevelButton.setOnMouseClicked(event -> {
                try {
                    playSequentialGame();
                } catch (NoSuchMethodException e) {
                    ErrorLogger.log(e);
                } catch (ReadSaveException e) {
                    ErrorLogger.log(e);
                } catch (InstantiationException e) {
                    ErrorLogger.log(e);
                } catch (IllegalAccessException e) {
                    ErrorLogger.log(e);
                } catch (InvocationTargetException e) {
                    ErrorLogger.log(e);
                } catch (ClassNotFoundException e) {
                    ErrorLogger.log(e);
                }
            });
        myBox.addButtons(LevelButton);

        Button ResumeSave = new Button(myResource.getString("Resume"));
        ResumeSave.setOnMouseClicked(event -> {
            try {
                buildSavedGame();
            } catch (NoSuchMethodException e) {
                ErrorLogger.log(e);
            } catch (ReadSaveException e) {
                ErrorLogger.log(e);
            } catch (InstantiationException e) {
                ErrorLogger.log(e);
            } catch (IllegalAccessException e) {
                ErrorLogger.log(e);
            } catch (InvocationTargetException e) {
                ErrorLogger.log(e);
            } catch (ClassNotFoundException e) {
                ErrorLogger.log(e);
            }
        });
        myBox.addButtons(ResumeSave);

        return myBox;
    }

    private void buildSavedGame() throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        int levelPlay = myPC.getLastLevel();
        if (levelPlay > 0) {
            Game game = new Game(levelPlay, myScene, new BorderPane(), myPC, myPC.getScreenHeight(), myPC.getScreenWidth());
            SavedGame sg = new SavedGame(myStage, Pages.PlayLevel, myPC, game);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(myResource.getString("InvalidFile"));
            alert.setHeaderText(myResource.getString("InvalidFile"));
            alert.setContentText(myResource.getString("Try"));
        }

    }

    private void playSequentialGame() throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        PlayLevel playLevel = new PlayLevel(myStage, Pages.PlayLevel, myPC);
    }


    private void goCustomize() {
        CustomMenu cm = new CustomMenu(myStage, Pages.CustomizePlayerMenu, myPC);
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        SideBar bar = new SideBar(myScene, myPC);
        myRoot.getChildren().add(bar);
        return myScene;
    }

    @Override
    Scene gotoScene(String name) throws IOException, ReadSaveException, DuplicateUsernameException {
            return getScene(name);
    }
}
