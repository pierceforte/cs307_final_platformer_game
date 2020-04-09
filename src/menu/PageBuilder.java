package menu;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 * Builds items for any Page to add.
 */
public class PageBuilder {
    private ResourceBundle myResource = ResourceBundle.getBundle("menu.menuresources");
    private static final String STYLESHEET = "menuresources/main.css";

    public PageBuilder() {
    }

    public Button buildNewGameButton() {
            Button save = new Button(myResource.getString("NewGame"));
            save.setId("SaveButton");
            save.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Dialog<String[]> dialog = new Dialog<>();
                    dialog.setTitle(myResource.getString("SaveTitle"));
                    dialog.setHeaderText(myResource.getString("Enter"));

                    ButtonType save = new ButtonType(myResource.getString("Save"), ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);

                    TextField title = new TextField(myResource.getString("Simtitle"));
                    title.setPromptText("Title");

                    TextField saveloc = new TextField(myResource.getString("SaveLoc"));
                    saveloc.setPromptText(myResource.getString("SaveLoc"));

                    TextField author = new TextField(myResource.getString("Simauthor"));
                    author.setPromptText("Author");

                    TextField description = new TextField(myResource.getString("Simdetail"));
                    description.setPromptText(myResource.getString("Describe"));

                    VBox texts = new VBox(title, author, description, saveloc);
                    dialog.getDialogPane().setContent(texts);

                    dialog.setResultConverter(dialogButton-> {
                        if (dialogButton == save) {
                            return new String[]{ saveloc.getText(), title.getText(), author.getText(), description.getText()};
                        }
                        return null;
                    });

                    Optional<String[]> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String[] g = result.get();
                        try {

                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle(myResource.getString("InvalidFile"));
                            alert.setHeaderText(myResource.getString("InvalidFile"));
                            alert.setContentText(myResource.getString("Try"));
                            //throw a "file already exists" exception
                        }
                    }}}
            return save;
    }
}
