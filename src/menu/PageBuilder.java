package menu;

import builder.stage.PaneDimensions;
import data.user.User;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

/*
 * Builds items for any Page to add.
 */
public class PageBuilder {
    private ResourceBundle myResource = ResourceBundle.getBundle("text.MenuButtons");
    private Stage myStage;

    public PageBuilder(Stage primaryStage) {
        myStage = primaryStage;
    }

    public Text buildTitleText(String s) {
        Text t = new Text(s);
        t.setId("MainTitle");
        t.setFill(Color.GREENYELLOW);

        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);

        t.setEffect(is);

        return t;
    }

    public double getScreenWidth() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getWidth();
    }

    public double getScreenHeight() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getHeight();
    }

    public double getTileWsize() {
        return getScreenWidth()/ PaneDimensions.TILE_WIDTH_FACTOR;
    }
    public double getTileHsize() {
        return getScreenHeight()/PaneDimensions.TILE_HEIGHT_FACTOR;
    }

    public void addMainMenuButtons(Page.MenuBox myBox) {
        Button play = new Button (myResource.getString("Cont"));
        play.setId("button");
        play.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog<String[]> dialog = new Dialog<>();
                dialog.setTitle(myResource.getString("Welcome"));
                dialog.setHeaderText(myResource.getString("Welcome"));

                ButtonType play = new ButtonType(myResource.getString("Login"), ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(play, ButtonType.CANCEL);

                TextField title = new TextField();
                title.setPromptText(myResource.getString("NGUsername"));

                TextField saveloc = new TextField();
                saveloc.setPromptText(myResource.getString("NGPassword"));


                VBox texts = new VBox(title, saveloc);
                dialog.initOwner(myStage.getScene().getWindow());
                dialog.getDialogPane().setContent(texts);

                dialog.setResultConverter(dialogButton-> {
                    if (dialogButton == play) {
                        return new String[]{ saveloc.getText(), title.getText()};
                    }
                    return null;
                });

                Optional<String[]> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String[] g = result.get();
                    try {
                        User user = new User(title.getText(), saveloc.getText());
                        PageController pageControl = new PageController(user, myStage);
                        LevelDirectory ls = new LevelDirectory(myStage, Pages.LevelDirectory, pageControl);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(myResource.getString("InvalidFile"));
                        alert.setHeaderText(myResource.getString("InvalidFile"));
                        alert.setContentText(myResource.getString("Try"));
                    }
                }

            }});
        Button exit = new Button(myResource.getString("Exit"));
        exit.setOnMouseClicked(event -> System.exit(0));
        myBox.addButtons(play, exit);

    }
}
