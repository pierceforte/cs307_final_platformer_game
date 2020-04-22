package builder;

import engine.gameobject.Coordinates;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public abstract class GridStage extends Pane {

    private Canvas canvas;
    private Affine myGrid;
    private double tileHeight;
    private double tileWidth;

    public GridStage(double width, double height) {
        setWidth(width);
        setHeight(height);
        this.canvas = new Canvas(width, height);
        this.myGrid = new Affine();
        tileWidth = width/30;
        tileHeight = height/25;
        myGrid.appendScale(tileWidth, tileHeight);
        styleGrid();
        this.canvas.setOnMouseClicked(this::handleClick);
        this.getChildren().add(canvas);
    }

    private void styleGrid() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.DARKCYAN);
        g.fillRect(0, 0, tileWidth*30, tileHeight*25);
        g.setFill(Color.WHITE);
        g.setStroke(Color.WHITE);
        for (int y = 0; y < 25; y++) {
            for (int x = 0 ; x < 30; x++) {
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

    static class Delta { double x, y; }

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
            x = getSnappedPos(coords.getX(), getTileWidth());
            y = getSnappedPos(coords.getY(), getTileHeight());
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        object.setX(x);
        object.setY(y);
    }

    private double getSnappedPos(double pos, double tileSize) {
        if (pos < 0) {
            return 0;
        }
        return pos * tileSize;
    }
}