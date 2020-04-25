package menu;

import data.ReadSaveException;
import data.user.DuplicateUsernameException;
import data.user.User;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        for (int k = 1; k <= num_Levels; k++ )  {
            Button LevelButton = new Button(myResource.getString("L"+k));
            int finalK = k;
            LevelButton.setOnMouseClicked(event -> {
                try {
                    goLX(finalK);
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
            });
            myBox.addButtons(LevelButton);
        }

        return myBox;
    }

    private void goLX(int k) throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        if (k == 1) goL1();
        if (k == 2) goL2();
        if (k == 3) goL3();
    }

    private void goL1() throws NoSuchMethodException, ReadSaveException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        LevelOne levelOne = new LevelOne(myStage, Pages.PlayLevel, myPC);
    }

    private void goL2() {

    }

    private void goL3() {

    }
    private void goDebug() throws IOException {
        DebugEnvironment de = new DebugEnvironment(myStage, Pages.Debug);
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
