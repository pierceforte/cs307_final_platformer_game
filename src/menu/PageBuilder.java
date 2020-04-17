package menu;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.ResourceBundle;

/*
 * Builds items for any Page to add.
 */
public class PageBuilder {
    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources.MenuButtons");
    private static final String STYLESHEET = "menuresources/main.css";
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

    public Button buildNewGameButton() {
        Button save = new Button(myResource.getString("NewGame"));
        save.setId("LaunchButton");
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    Dialog<String[]> dialog = new Dialog<>();
                    dialog.setTitle(myResource.getString("NGTitle"));
                    dialog.setHeaderText(myResource.getString("NGPrompt"));

                    ButtonType save = new ButtonType(myResource.getString("Login"), ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);

                    TextField title = new TextField(myResource.getString("NGUsername"));
                    title.setPromptText(myResource.getString("NGUsername"));

                    TextField saveloc = new TextField(myResource.getString("NGPassword"));
                    saveloc.setPromptText(myResource.getString("NGPassword"));

                    VBox texts = new VBox(title, saveloc);
                    dialog.getDialogPane().setContent(texts);

                    dialog.setResultConverter(dialogButton-> {
                        if (dialogButton == save) {
                            return new String[]{ saveloc.getText(), title.getText()};
                        }
                        return null;
                    });

                    Optional<String[]> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String[] g = result.get();
                        try {
                            //User player = new User(title.getText(), saveloc.getText());
                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle(myResource.getString("InvalidFile"));
                            alert.setHeaderText(myResource.getString("InvalidFile"));
                            alert.setContentText(myResource.getString("Try"));
                            //throw a "file already exists" exception
                        }
                    }}});
        return save;
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

                TextField title = new TextField(myResource.getString("NGUsername"));
                title.setPromptText(myResource.getString("NGUsername"));

                TextField saveloc = new TextField(myResource.getString("NGPassword"));
                saveloc.setPromptText(myResource.getString("NGPassword"));


                VBox texts = new VBox(title, saveloc);
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
                        //check with user

                        LevelDirectory ls = new LevelDirectory(myStage, Pages.LevelDirectory);

                        //myStage.setScene()

                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(myResource.getString("InvalidFile"));
                        alert.setHeaderText(myResource.getString("InvalidFile"));
                        alert.setContentText(myResource.getString("Try"));
                        //throw a "file already exists" exception
                    }
                }

            }});
        Button exit = new Button(myResource.getString("Exit"));
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
        myBox.addButtons(play, exit);
        myBox.setId("MenuBox");
    }
}
