package builder.stage;

import engine.gameobject.GameObject;

import java.util.List;

/**
 * This class is a child of the PaneDimensions class. It is specifcally suited for the needs of a TilePane, which requires information
 * about tile size.
 *
 * This class is dependent on the screen on which it is portrayed, for it must access the sizing information of this screen.
 *
 * @author Pierce Forte
 */
public class TilePaneDimensions extends PaneDimensions{

    public static final double TILE_WIDTH_FACTOR = 35;
    public static final double TILE_HEIGHT_FACTOR = 25;

    private double tileWidth;
    private double tileHeight;

    /**
     * The constructor used to create a TilePaneDimensions object.
     * @param minX the minimum x position of the TilePane
     * @param maxX the maximum x position of the TilePane
     * @param minY the minimum y position of the TilePane
     * @param maxY the maximum y position of the TilePane
     */
    public TilePaneDimensions(Integer minX, Integer maxX, Integer minY, Integer maxY) {
        super(minX, maxX, minY, maxY);
        setTileSize();
    }

    /**
     * The constructor used to create a PaneDimensions object based on a list of GameObjects.
     * This list of GameObjects, which is typically used to populate a level for a game, can be used
     * to determine the boundaries for a TilePane based on the positioning of these GameObjects.
     * @param gameObjects a list of GameObjects
     */
    public TilePaneDimensions(List<GameObject> gameObjects) {
        super(gameObjects);
    }

    /**
     * Get the width of the tiles
     * @return the tile width
     */
    public double getTileWidth() {
        return tileWidth;
    }

    /**
     * Get the height of the tiles
     * @return the tile height
     */
    public double getTileHeight() {
        return tileHeight;
    }

    private void setTileSize() {
        tileWidth = getScreenWidth()/TILE_WIDTH_FACTOR;
        tileHeight = getScreenHeight()/TILE_HEIGHT_FACTOR;
    }
}
