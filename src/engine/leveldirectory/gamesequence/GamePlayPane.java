package engine.leveldirectory.gamesequence;

import builder.NodeDragger;
import builder.stage.DraggableTilePane;
import builder.stage.PaneDimensions;

public class GamePlayPane extends DraggableTilePane {

    public GamePlayPane(PaneDimensions dimensions) {
        super(dimensions);
        enableDrag();
    }

    @Override
    public void update() {

    }
}
