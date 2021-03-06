package pagination;

import builder.stage.TilePaneDimensions;
import data.user.User;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PageController {

    private User user;
    private Stage myStage;
    private TilePaneDimensions myPane;
    private int lastLevel;

    public PageController(User user, Stage stage) {
        this.myStage = stage;
        this.user = user;
        lastLevel = -1;
    }

    public User getUser() {
        return user;
    }

    public int getLastLevel() {
        return lastLevel;
    }
    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
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
    public double getTileWsize() {
        return myPane.getTileWidth();
    }
    public double getTileHsize() {
        return myPane.getTileHeight();
    }



}
