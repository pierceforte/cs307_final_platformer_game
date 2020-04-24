package builder.stage.exitHandler;

import builder.stage.BuilderObjectView;
import builder.stage.PaneDimensions;
import engine.gameobject.GameObject;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Pierce Forte
 */
public class BuilderStageExitHandler implements StageExitHandler {

    private Window window;
    private List<GameObject> gameObjects;
    private ResourceBundle resources;

    public BuilderStageExitHandler(ResourceBundle resources) {
        this.resources = resources;
        gameObjects = new ArrayList<>();
    }

    public boolean isExitRequestValid(List<BuilderObjectView> builderObjectViews) {
        for (BuilderObjectView builderObjectView : builderObjectViews) {
            if (builderObjectView.areActionIconsActive()) {
                gameObjects.clear();
                return false;
            }
            GameObject gameObject = builderObjectView.getGameObject();
            //TODO: correct these calculations
            gameObject.setX(builderObjectView.getX()/PaneDimensions.TILE_WIDTH_FACTOR);
            gameObject.setY(builderObjectView.getY()/PaneDimensions.TILE_HEIGHT_FACTOR);
            gameObject.setWidth(builderObjectView.getFitWidth()/PaneDimensions.TILE_WIDTH_FACTOR);
            gameObject.setHeight(builderObjectView.getFitHeight()/PaneDimensions.TILE_HEIGHT_FACTOR);
            gameObjects.add(gameObject);
        }
        return true;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public void acceptExitRequest() {
        return;
    }

    public void rejectExitRequest() {
        Dialog dialog = new Dialog();
        dialog.initOwner(window);
        dialog.setContentText(resources.getString("InvalidLeave"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Platform.runLater(() -> dialog.showAndWait());
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
