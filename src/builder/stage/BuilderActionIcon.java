package builder.stage;

import engine.view.ImageCreator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a child of the ImageView class that provides it with duplicate code used to create action icons for the
 * BuilderObjectView objects.
 *
 * This class is dependent on the associated BuilderAction enum element associated with it as well as the ActionableGameObjectView
 * affected by it when fired.
 *
 * @author Pierce Forte
 */
public class BuilderActionIcon extends ImageView {

    private ActionableGameObjectView actionableGameObjectView;
    private BuilderAction builderAction;

    /**
     * The constructor to create a BuilderActionIcon.
     * @param builderAction the associated enum element
     * @param actionableGameObjectView the associated ActionableGameObjectView
     */
    public BuilderActionIcon(BuilderAction builderAction, ActionableGameObjectView actionableGameObjectView) {
        this.actionableGameObjectView = actionableGameObjectView;
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
