package builder;

import engine.gameobject.Coordinates;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public abstract class GridStage extends Pane {

    public static final double TILE_WIDTH_FACTOR = 30;
    public static final double TILE_HEIGHT_FACTOR = 25;
    private Canvas canvas;
    private Affine myGrid;
    private double tilesWide;
    private double tilesHigh;
    private double tileHeight;
    private double tileWidth;
    private GridDimensions dimensions;

    public GridStage(GridDimensions dimensions) {
        this.dimensions = dimensions;
        this.tilesWide = dimensions.getMaxX() - dimensions.getMinX();
        this.tilesHigh = dimensions.getMaxY() - dimensions.getMinY();
        tileWidth = dimensions.getScreenWidth()/TILE_WIDTH_FACTOR;
        tileHeight = dimensions.getScreenHeight()/TILE_HEIGHT_FACTOR;
        setWidth(tilesWide * tileWidth);
        setHeight(tilesHigh * tileHeight);
        this.canvas = new Canvas(getWidth(), getHeight());
        this.myGrid = new Affine();
        myGrid.appendScale(tileWidth, tileHeight);
        styleGrid();
        this.canvas.setOnMouseClicked(this::handleClick);
        this.getChildren().add(canvas);
    }

    private void styleGrid() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.DARKCYAN);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFill(Color.WHITE);
        g.setStroke(Color.WHITE);
        for (int y = 0; y < tilesHigh; y++) {
            for (int x = 0 ; x < tilesWide; x++) {
                g.strokeRect(x * tileWidth, y* tileHeight, tileWidth, tileHeight);
            }
        }
        canvas.setOpacity(.6);
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public double getTileHeight() {
        return tileHeight;
    }

    public abstract void update();

    protected void handleClick(MouseEvent mouseEvent) {
        double clickX = mouseEvent.getX();
        double clickY = mouseEvent.getY();
        try {
            Coordinates coords = convertToGridCoords(clickX, clickY);
            System.out.println("click:" + coords.getX() + ", " + coords.getY());
        } catch (NonInvertibleTransformException e){
            e.printStackTrace();
            return;
        }
    }

    protected Coordinates convertToGridCoords(double clickX, double clickY) throws NonInvertibleTransformException{
        Point2D myClick = myGrid.inverseTransform(clickX,clickY);
        int x = (int) myClick.getX();
        int y = (int) myClick.getY();
        Coordinates coords = new Coordinates(x, y);
        return coords;
    }

    protected void snapItem(ImageView object) {
        double x = object.getX();
        double y = object.getY();
        try {
            Coordinates coords = convertToGridCoords(object.getX(), object.getY());
            x = getSnappedPos(coords.getX(), getTileWidth(), getTranslateX(), dimensions.getScreenWidth());
            y = getSnappedPos(coords.getY(), getTileHeight(), getTranslateY(), dimensions.getScreenHeight());
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        object.setX(x);
        object.setY(y);
    }

    private double getSnappedPos(double pos, double tileSize, double gridPos, double screenSize) {
        double adjustedPos = pos * tileSize;
        if (adjustedPos < -1*gridPos) {
            return -1*gridPos;
        }
        else if (adjustedPos > -1*gridPos + screenSize) {
            return -1*gridPos + screenSize;
        }
        return adjustedPos;
    }
}