package builder.stage;

import data.ErrorLogger;
import engine.gameobject.Coordinates;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

/**
 * This abstract class is part of a hierarchy of classes that allows for a Pane to be made up of tiles. This class, as a
 * child of the Pane class, adds onto the preexisting attributes of a Pane by maintaining a canvas and affine through composition,
 * each of which allow for a stylistic tile grid and tile-snapping node placement. Note that this tile grid and snapping can
 * be enabled and disabled through public methods.
 *
 * This class is most suitably used by nodes that have dimensions in units of the tiles used for this TilePane. If this is the case,
 * nodes will fit into the tiles properly and can be addressed based on the TilePane's coordinate system.
 *
 * This class is dependent on a dimensions object which determines its size and its tiles' size.
 *
 * @author Pierce Forte
 * @author Nicole Lindbergh
 */
public abstract class TilePane extends Pane {

    public static final Color CANVAS_COLOR = Color.DARKCYAN;
    public static final Color CELL_COLOR = Color.WHITE;
    public static final double CANVAS_OPACITY = 0.6;

    private Canvas canvas;
    private Affine myGrid;
    private double tilesWide;
    private double tilesHigh;
    private TilePaneDimensions dimensions;

    /**
     * The constructor to create a TilePane.
     * @param dimensions the dimensions associated with this TilePane
     */
    public TilePane(TilePaneDimensions dimensions) {
        this.dimensions = dimensions;
        this.tilesWide = dimensions.getMaxX() - dimensions.getMinX();
        this.tilesHigh = dimensions.getMaxY() - dimensions.getMinY();
        setWidth(tilesWide * dimensions.getTileWidth());
        setHeight(tilesHigh * dimensions.getTileHeight());
        this.canvas = new Canvas(getWidth(), getHeight());
        this.getChildren().add(canvas);
    }

    /**
     * This method requires subclasses of the TilePane class to define how the Pane should be updated. This method
     * can simply be left empty if the TilePane should not update internally, or it can be used to update the TilePane
     * as desired as part of a stepping function.
     */
    public abstract void update();

    /**
     * This method can be used to enable the tile-based grid system for the TilePane.
     */
    public void addGrid() {
        this.myGrid = new Affine();
        myGrid.appendScale(dimensions.getTileWidth(), dimensions.getTileHeight());
        styleGrid();
    }

    /**
     * This method can be used to disable the tile-based grid system for the TilePane. When called, this method, in essence,
     * removes the added features of the TilePane, and the TilePane object will act as a simple Pane.
     */
    public void removeGrid() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    protected Coordinates convertToGridCoords(double clickX, double clickY) throws NonInvertibleTransformException{
        Point2D myClick = myGrid.inverseTransform(clickX,clickY);
        int x = (int) myClick.getX();
        int y = (int) myClick.getY();
        return new Coordinates(x, y);
    }

    protected void snapItem(ImageView object) {
        double x = object.getX();
        double y = object.getY();
        try {
            Coordinates coords = convertToGridCoords(object.getX(), object.getY());
            x = getSnappedPos(coords.getX(), dimensions.getTileWidth(), getTranslateX(), dimensions.getScreenWidth());
            y = getSnappedPos(coords.getY(), dimensions.getTileHeight(), getTranslateY(), dimensions.getScreenHeight());
        } catch (NonInvertibleTransformException e) {
            ErrorLogger.log(e);
        }
        object.setX(x);
        object.setY(y);
    }

    private double getSnappedPos(double pos, double tileSize, double gridPos, double screenSize) {
        double adjustedPos = pos * tileSize;
        double adjustedGridPos = -1*gridPos;
        if (adjustedPos < adjustedGridPos) {
            return adjustedGridPos;
        }
        else if (adjustedPos > adjustedGridPos + screenSize) {
            return adjustedGridPos + screenSize;
        }
        return adjustedPos;
    }

    private void styleGrid() {
        GraphicsContext graphicContext = canvas.getGraphicsContext2D();
        graphicContext.setFill(CANVAS_COLOR);
        graphicContext.setStroke(CELL_COLOR);
        graphicContext.fillRect(0, 0, getWidth(), getHeight());
        for (int x = 0 ; x < tilesWide; x++) {
            for (int y = 0; y < tilesHigh; y++) {
                graphicContext.strokeRect(x * dimensions.getTileWidth(), y* dimensions.getTileHeight(),
                        dimensions.getTileWidth(), dimensions.getTileHeight());
            }
        }
        canvas.setOpacity(CANVAS_OPACITY);
    }
}