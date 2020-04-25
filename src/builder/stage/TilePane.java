package builder.stage;

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
 *
 * @author Pierce Forte, Nicole Lindbergh
 */
public abstract class TilePane extends Pane {

    public static final Color CANVAS_COLOR = Color.DARKCYAN;
    public static final Color CELL_COLOR = Color.WHITE;
    public static final double CANVAS_OPACITY = 0.6;

    private Canvas canvas;
    private Affine myGrid;
    private double tilesWide;
    private double tilesHigh;
    private PaneDimensions dimensions;

    public TilePane(PaneDimensions dimensions) {
        this.dimensions = dimensions;
        this.tilesWide = dimensions.getMaxX() - dimensions.getMinX();
        this.tilesHigh = dimensions.getMaxY() - dimensions.getMinY();
        setWidth(tilesWide * dimensions.getTileWidth());
        setHeight(tilesHigh * dimensions.getTileHeight());
        this.canvas = new Canvas(getWidth(), getHeight());
        this.getChildren().add(canvas);
    }

    public abstract void update();

    public void addGrid() {
        this.myGrid = new Affine();
        myGrid.appendScale(dimensions.getTileWidth(), dimensions.getTileHeight());
        styleGrid();
    }

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
            e.printStackTrace();
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