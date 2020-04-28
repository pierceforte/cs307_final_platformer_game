package engine.leveldirectory.gamesequence;

import builder.stage.DraggableTilePane;
import builder.stage.TilePaneDimensions;

/**
 * This class is a DraggableTilePane that provides additional functionality for the game play stage of the game.
 */
public class GamePlayPane extends DraggableTilePane {

    /**
     * The constructor to create a GamePlayPane.
     * @param dimensions the dimensions associated with this GamePlayPane
     */
    public GamePlayPane(TilePaneDimensions dimensions) {
        super(dimensions);
        enableDrag();
    }

    @Override
    public void update() {

    }
}
