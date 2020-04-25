package menu;

import data.user.User;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PageController {

    private User user;
    private Stage myStage;

    public PageController(User user, Stage stage) {
        this.myStage = stage;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    public Stage getMyStage() {return myStage;}

    public double getScreenWidth() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getWidth();
    }

    public double getScreenHeight() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getHeight();
    }

}
