package builder.stage;

import engine.view.ImageCreator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuilderActionIcon extends ImageView {

    private BuilderObjectView builderObjectView;
    private BuilderAction builderAction;

    public BuilderActionIcon(BuilderAction builderAction, BuilderObjectView builderObjectView) {
        this.builderObjectView = builderObjectView;
        this.builderAction = builderAction;
        createActionIcon();
    }

    private void createActionIcon() {
        Image img = ImageCreator.makeImage(builderAction.getImgPath());
        setImage(img);
        setFitWidth(BuilderAction.SIZE);
        setFitHeight(BuilderAction.SIZE);
        setPickOnBounds(true);
        setId(builderAction.getId());
        setOnMouseClicked(mouseEvent -> builderAction.getButtonAction(builderObjectView).run());
    }
}
