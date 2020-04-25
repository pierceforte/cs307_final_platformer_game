package menu;

import data.ReadSaveException;
import data.user.User;

import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomMenu extends Page {
    private Stage myStage;
    private Scene myScene;
    private PageBuilder myFactory;
    private ToggleButton[] myOptions;
    private ToggleButton[] myOpponents;
    private int selected;
    private PageController myPC;

    private Map<String, ImageView> avatars;

    private ImageView avatar;
    private Pane myRoot;
    private User myUser;

    private static final ResourceBundle deets = ResourceBundle.getBundle("text.Details");

    private final String Snake = "Snake";
    private final String Snail = "Snail";
    private final int imageSize = 128;
    private final int spacing = 50;

    private boolean Snails;
    private boolean Snakes;

    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private static final String STYLESHEET = "css/cm.css";
    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @param page
     * @return Page
     */
    public CustomMenu(Stage primaryStage, Pages page, PageController myPC) {
        super(primaryStage, page);

        myStage = primaryStage;
        myStage.setFullScreen(true);
        myFactory = new PageBuilder(myStage);
        myStage.setTitle(myResource.getString("MainTitle"));

        this.myPC = myPC;
        myUser = myPC.getUser();
        selected = 0;
        avatars = new HashMap<>();

        setTeam();

        myScene = this.buildSpecialScene((int) myFactory.getScreenHeight(), (int) myFactory.getScreenWidth());
        myStage.setScene(myScene);
    }

    private void setTeam() {
        int mySide = myUser.getType();
        if (mySide == 0) {
            Snails = true;
            Snakes = false;
        }
        else {
            Snails = false;
            Snakes = true;
        }
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
        back.setOnMouseClicked(event -> {
            try {
                leaveStage();
            } catch (IOException e) { }
        });

        myRoot.getChildren().addAll(myOptions);
        myRoot.getChildren().addAll(myOpponents);
        myRoot.getChildren().add(back);

        displayMoney();
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
        myOptions[x].setOnMouseClicked(event -> action(key, type));
    }

    private void buildOpponentButton(String type, int x) {
        myOpponents[x] = new ToggleButton();

        int y = x + 1;
        String key = type+y;
        avatars.put(key, Avatars.valueOf(key).getImg());

        myOpponents[x].setId(type+y);
        myOpponents[x].setTranslateY(y*imageSize+spacing);
        myOpponents[x].setTranslateX(spacing);

        myOpponents[x].setOnMouseClicked(event -> action(key, type));

    }

    private void displayMoney() {
        Text scoreDisplay = new Text(myResource.getString("Score")+myUser.getScore());
        scoreDisplay.setId("scoreDisplay");
        myRoot.getChildren().add(scoreDisplay);
    }
    private void updateMoneyDisplay() {
        myRoot.getChildren().remove(myScene.lookup("#scoreDisplay"));
        displayMoney();
    }

    private void action(String x, String type) {
        removeCurrentAvatar();
        displayAvatar(avatars.get(x));
        displayDetails(x, type);
    }
    private void removeNodes() {
        myRoot.getChildren().removeAll(myRoot.lookup("#ViewText"),myRoot.lookup("#Name"), myRoot.lookup("#Deet"),
                myRoot.lookup("#purchaseButton"), myRoot.lookup("#itemCost"));
    }

    private void displayDetails(String x, String type) {
        removeNodes();

        Pane c = new Pane();
        c.setId("ViewText");

        Text title = new Text(deets.getString(x+"Title"));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setId("Name");
        title.setFill(Color.WHITESMOKE);
        title.setTranslateY(myFactory.getScreenHeight()*4/5 + spacing);
        title.setTranslateX(myFactory.getScreenWidth()/2 - 3*imageSize + 20);

        Text deet = new Text(deets.getString(x+"Deet"));

        deet.setTextAlignment(TextAlignment.CENTER);
        deet.setId("Deet");
        deet.setTranslateY(myFactory.getScreenHeight()*4/5 +spacing+20);
        deet.setFill(Color.WHITESMOKE);
        deet.setTranslateX(myFactory.getScreenWidth()/2 - 3*imageSize + 20);

        c.setTranslateX(myFactory.getScreenWidth()/2 - 3*imageSize);
        c.setTranslateY(myFactory.getScreenHeight()*4/5);

        myRoot.getChildren().addAll(c, title, deet);

        if ((Snakes && type.equals(Snake)) || (Snails && type.equals(Snail))) {
            addPurchaseDetails(x);
        }

    }

    private void addPurchaseDetails(String x) {
        Text purchaseinfo = new Text(deets.getString("Purchase") + Avatars.valueOf(x).getPrice());
        purchaseinfo.setId("itemCost");
        purchaseinfo.setTranslateX(myFactory.getScreenWidth()/2 - spacing*4);
        purchaseinfo.setTranslateY(myFactory.getScreenHeight()*4/5 +spacing*2 + 20);
        purchaseinfo.setFill(Color.WHITESMOKE);

        Button purchaseButton = new Button("Purchase!");
        purchaseButton.setId("purchaseButton");

        purchaseButton.setTranslateX(myFactory.getScreenWidth()/2 + imageSize+20);
        purchaseButton.setTranslateY(myFactory.getScreenHeight()*4/5 +spacing);

        purchaseButton.setOnMouseClicked(event -> {
            try {
                attemptPurchase(x);
            } catch (ReadSaveException e) {
                myRoot.getChildren().add(new Text("Unable to process purchase"));
            }
        });

        myRoot.getChildren().addAll(purchaseButton, purchaseinfo);
    }

    private void attemptPurchase(String x) throws ReadSaveException {
        int userScore = myUser.getScore();

        if (Avatars.valueOf(x).getPrice() <= userScore) {
            myUser.updateScore(-Avatars.valueOf(x).getPrice());
            myUser.changeAvatar(Avatars.valueOf(x).getimgpath());
            updateMoneyDisplay();
        }
        else {
            myRoot.getChildren().add(new Text(deets.getString("NoMoney")));
        }
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

    private void leaveStage() throws IOException {
        for (int x = 0; x < 6; x++) {
            if (myOptions[x].isSelected()) {
                selected = x;
                LevelDirectory ll = new LevelDirectory(myStage, Pages.LevelDirectory, myPC);
            }
        }
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }

    Scene buildSpecialScene(int height, int width) {
        Pane myRoot = init_Root(height, width);
        myScene = new Scene(myRoot);
        myScene.getStylesheets().addAll(this.getClass().getResource(STYLESHEET).toExternalForm());
        return myScene;
    }

}
