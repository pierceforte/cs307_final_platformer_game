package builder.stage;

import engine.view.ImageCreator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Pierce Forte
 */
public class BuilderActionIcon extends ImageView {

    private ActionableGameObjectView actionableGameObjectView;
    private BuilderAction builderAction;

    public BuilderActionIcon(BuilderAction builderAction, ActionableGameObjectView builderObjectView) {
        this.actionableGameObjectView = builderObjectView;
        this.builderAction = builderAction;
        initialize();
    }

    private void initialize() {
        Image img = ImageCreator.makeImage(builderAction.getImgPath());
        setImage(img);
        setFitWidth(BuilderAction.SIZE);
        setFitHeight(BuilderAction.SIZE);
        setPickOnBounds(true);
        setId(builderAction.getId());
        setOnMouseClicked(mouseEvent -> builderAction.getButtonAction(actionableGameObjectView).run());
    }
}
