package builder.stage.exitHandler;

import builder.stage.BuilderObjectView;
import builder.stage.TilePaneDimensions;
import engine.gameobject.GameObject;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class supports the exit requests received during the builder stage of the game. The BuilderPane utilizes this class
 * to determine if the exit request is valid and, if so, prepare the BuilderPane for this exit.
 *
 * This class implements the StageExitHandler interface, which requires it to define how to reject and accept exit requests.
 *
 * This class is dependent on the resources bundle used for its text displays. It also requires object types specific to
 * the builder stage, like BuilderObjectViews.
 *
 * @author Pierce Forte
 */
public class BuilderStageExitHandler implements StageExitHandler {

    private Window window;
    private List<GameObject> gameObjects;
    private ResourceBundle resources;

    /**
     * The constructor to create a BuilderStageExitHandler.
     * @param resources the resources bundle the text elements in this class should follow
     */
    public BuilderStageExitHandler(ResourceBundle resources) {
        this.resources = resources;
        gameObjects = new ArrayList<>();
    }

    /**
     * This class determines whether an exit request is valid and compiles the necessary
     * exit materials if successful.
     * @param builderObjectViews the list of BuilderObjectViews currently in the BuilderPane
     * @param dimensions the dimensions of the BuilderPane
     * @return whether the exit request is valid
     */
    public boolean isExitRequestValid(List<BuilderObjectView> builderObjectViews, TilePaneDimensions dimensions) {
        for (BuilderObjectView builderObjectView : builderObjectViews) {
            if (builderObjectView.areActionIconsActive()) {
                gameObjects.clear();
                return false;
            }
            GameObject gameObject = createGameObject(builderObjectView, dimensions);
            gameObjects.add(gameObject);
        }
        return true;
    }

    /**
     * Sets the window for dialog to be presented in.
     * @param window the window to be set
     */
    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * Returns a list of the GameObjects that have been created upon a successful exit request.
     * These objects are typically used to populate the level for game play after exiting the
     * builder stage.
     * @return a list of GameObjects
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    @Override
    public void acceptExitRequest() {
        return;
    }

    @Override
    public void rejectExitRequest() {
        Dialog dialog = new Dialog();
        dialog.initOwner(window);
        dialog.setContentText(resources.getString("InvalidLeave"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Platform.runLater(() -> dialog.showAndWait());
    }

    private GameObject createGameObject(BuilderObjectView builderObjectView, TilePaneDimensions dimensions) {
        GameObject gameObject = builderObjectView.getGameObject();
        gameObject.setX(builderObjectView.getX()/dimensions.getTileWidth());
        gameObject.setY(builderObjectView.getY()/dimensions.getTileHeight());
        gameObject.setWidth(builderObjectView.getFitWidth()/dimensions.getTileWidth());
        gameObject.setHeight(builderObjectView.getFitHeight()/dimensions.getTileHeight());
        return gameObject;
    }
}